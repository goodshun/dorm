package com.lds.orm.dorm.connection;

import com.lds.orm.dorm.connection.config.DbConfig;
import com.lds.orm.dorm.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
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
            LOGGER.error("Get connection error",e);
            throw new ConnectionException("Get connection error",e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 释放链接
     *
     * @param con
     */
    public void recycleConnection(Connection con) {

    }

    /**
     * 检测连接池状况
     */
    public void check() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.debug("空线池连接数："+unUsedConn.size());
                LOGGER.debug("活动连接数：："+usedConn.size());
                LOGGER.debug("总的连接数："+connSize.get());
            }
        },1000);
    }
}
