package com.lds.orm.dorm.connection;

import com.lds.orm.dorm.connection.config.DbConfig;
import com.lds.orm.dorm.connection.wrapper.ConnectionWrapper;
import com.lds.orm.dorm.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title: ConnectionPoolImpl
 * <p>
 * Description:连接池实现
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/24
 */
public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private DbConfig jdbcConfig;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Condition get = lock.newCondition();
    private final List<Connection> unUsedConn = new LinkedList<Connection>();
    private final List<Connection> usedConn = new LinkedList<Connection>();
    private volatile AtomicInteger connSize = new AtomicInteger(0);
    private final ScheduledExecutorService clearService = Executors.newSingleThreadScheduledExecutor();


    public ConnectionPool(DbConfig config) {
        this.jdbcConfig = config;
        initPool(config.getInitialPoolSize());
        //TODO  是否开启监控
    }


    /**
     * 初始化连接池
     *
     * @param size 初始化大小
     */
    public void initPool(int size) {
        lock.lock();
        try {
            for (int i = 0; i < size; i++) {
                unUsedConn.add(createConnection());
            }
            timerCheckConnection();
            LOGGER.info("===============Initialize DB Connection Pool SUCCESS===============");
        } catch (Exception e) {
            LOGGER.error("Initialize db connection error", e);
            throw new ConnectionException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 创建连接
     *
     * @return
     */
    private Connection createConnection() {
        lock.lock();
        try {
            Connection con = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
            connSize.incrementAndGet();
            Connection cw = new ConnectionWrapper(con, this);
            LOGGER.info("Create db connection：{} success", cw);
            return cw;
        } catch (SQLException e) {
            LOGGER.info("Create db connection error", e);
            throw new ConnectionException("Create db connection error", e);
        } finally {
            lock.unlock();
        }
    }


    /**
     * 获取连接
     *
     * @return
     */
    public Connection getConnection() {
        lock.lock();
        try {
            if (unUsedConn.size() > 0) {
                Connection con = unUsedConn.get(0);
                ConnectionWrapper cw = (ConnectionWrapper) con;
                if (cw.isSurvive() || ConnectionExceptionCount.isClose(cw)) {
                    cw.destroy();
                    unUsedConn.remove(con);
                    connSize.decrementAndGet();
                    return getConnection();
                }
                cw.setLastUsedTime(System.currentTimeMillis());
                unUsedConn.remove(con);
                usedConn.add(con);
                return con;
            }
            if (connSize.get() < jdbcConfig.getMaxPoolSize()) {
                Connection conn = createConnection();
                usedConn.add(conn);
                return conn;
            }
            boolean isSignal = get.await(jdbcConfig.getMaxWaitTime(), TimeUnit.MILLISECONDS);
            if (isSignal) {
                return getConnection();
            }
            throw new ConnectionException("当前数据库连接已达上限，无法再创建连接");
        } catch (Exception e) {
            LOGGER.error("Get connection error", e);
            throw new ConnectionException("Get connection error", e);
        } finally {
            lock.unlock();
        }
    }


    public Connection getConnection(String username, String password) throws SQLException {
        if (jdbcConfig.getUsername().equals(username) && jdbcConfig.getPassword().equals(password)) {
            return getConnection();
        }
        throw new ConnectionException("Username or password error");
    }

    /**
     * 释放链接
     *
     * @param con
     */
    public void recycleConnection(Connection con) {
        lock.lock();
        try {
            if (usedConn.remove(con)) {
                unUsedConn.add(con);
                get.signal();
                LOGGER.debug("回收连接:{} 成功", con);
                LOGGER.debug("当前连接情况【已创建：{}，已使用：{}，空闲：{}】", connSize.get(), usedConn.size(), unUsedConn.size());
            }
        } catch (Exception e) {
            LOGGER.error("回收连接池异常", e);
            throw new ConnectionException("回收连接出现异常", e);
        } finally {
            lock.unlock();
        }

    }

    /**
     * 定时检测连接 并清空连接
     */
    private void timerCheckConnection(){
        timerClearUnUsedConnection();
        timerClearUsedConnection();
    }

    /**
     * 定时清理空闲连接
     */
    private void timerClearUnUsedConnection() {
        //判断是否需要检查  默认不需要
        if (jdbcConfig.getIdleConnectionTestPeriod() <= 0) {
            return;
        }
        clearService.scheduleAtFixedRate(() -> {
            lock.lock();
            try {
                LOGGER.debug("开始清理空闲连接。【已创建连接：{}，已使用：{}，空闲：{}】", connSize.get(), usedConn.size(), unUsedConn.size());
                if (unUsedConn.size() <= jdbcConfig.getMinPoolSize()) {
                    return;
                }
                unUsedConn.removeIf(c -> {
                    if (unUsedConn.size() <= jdbcConfig.getMinPoolSize()) {
                        return false;
                    }
                    ConnectionWrapper cw = (ConnectionWrapper) c;
                    if (getIdleTime(cw.getLastUsedTime()) < jdbcConfig.getMaxIdleTime()) {
                        return false;
                    }
                    try {
                        cw.destroy();
                    } catch (Exception e) {
                        LOGGER.error("关闭连接出现异常", e);
                    }
                    connSize.decrementAndGet();
                    get.signal();
                    LOGGER.debug("销毁连接：{}成功", cw);
                    return true;
                });
            } catch (Exception e) {
                LOGGER.error("清理空闲连接出现异常", e);
            } finally {
                lock.unlock();
            }
        }, jdbcConfig.getIdleConnectionTestPeriod(), jdbcConfig.getIdleConnectionTestPeriod(), TimeUnit.MILLISECONDS);
    }


    /**
     * 清理已使用但未归还连接，每五分钟执行一次，若获取连接后，过了30分钟还未归还，则关闭此连接。
     */
    private void timerClearUsedConnection() {
        int interval = 5 * 60 * 1000;
        int idle = 30 * 60 * 1000;
        clearService.scheduleAtFixedRate(() -> {
            try {
                lock.lock();
                LOGGER.info("开始清理已使用未归还连接。【已创建连接：{}，已使用：{}，空闲：{}】", connSize.get(), usedConn.size(), unUsedConn.size());
                usedConn.removeIf(c -> {
                    ConnectionWrapper qcw = (ConnectionWrapper) c;
                    if (getIdleTime(qcw.getLastUsedTime()) < idle) {
                        return false;
                    }
                    try {
                        qcw.destroy();
                    } catch (Exception e) {
                        LOGGER.error("关闭连接出现异常", e);
                    }
                    connSize.decrementAndGet();
                    get.signal();
                    LOGGER.debug("销毁连接：{}成功", qcw);
                    return true;
                });
            } catch (Exception e) {
                LOGGER.error( "清理已使用连接出现异常",e);
            } finally {
                lock.unlock();
            }
        }, interval, interval, TimeUnit.MILLISECONDS);
    }


    private long getIdleTime(long lastUsedTime) {
        return System.currentTimeMillis() - lastUsedTime;
    }

}
