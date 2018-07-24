package com.lds.orm.dorm.connection;

import com.lds.orm.dorm.exception.MoreMaxConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Title: ConnectionManager
 * <p>
 * Description:  过时的连接管理
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/11
 */
@Deprecated
public class ConnectionManager {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private int coreNum = 3;
    private int maxNum = 10;
    // 连接池活动状态

    private boolean isActive = false;

    // 连接池活动状态

    private int contActive = 0;

    private int timeOut = 0;

    private List<Connection> freeConnection = new ArrayList<>(coreNum);
    private List<Connection> activeConnection = new ArrayList<>();


    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            for (int i = 0; i < coreNum; i++) {
                try {
                    Connection connection = newConnection();
                    if (connection != null) {
                        freeConnection.add(connection);
                        contActive++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            isActive = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Connection getCurrentConnection() {
        Connection connection = threadLocal.get();
        if (isValid(connection)) {
            return connection;
        }
        return null;
    }


    public Connection getConnection() {
        Connection conn = threadLocal.get();
        if (isValid(conn)) {
            return conn;
        }
        try {
            synchronized (this) {
                if (contActive >= maxNum){
                    this.wait(timeOut);
                }
                if (contActive < maxNum) {
                    if (freeConnection.size() > 0) {
                        conn = freeConnection.get(0);
                        if (conn != null) {
                            threadLocal.set(conn);
                        }
                        freeConnection.remove(0);
                    } else {
                        conn = newConnection();
                    }
                }
                if (isValid(conn)) {
                    activeConnection.add(conn);
                    contActive ++;
                    return conn;
                } else {
                    //超出最大数量
                    throw new MoreMaxConnectionException();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return conn;
    }


    //释放链接

    private synchronized void releaseConn(Connection connection){
        if (isValid(connection) ){
            freeConnection.add(connection);
            contActive --;
            threadLocal.remove();
            notifyAll();
        }
    }



    // 获得新连接

    private  Connection newConnection()
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
        return conn;
    }


    private boolean isValid(Connection conn) {
        try {
            if (conn == null || conn.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void checkPool(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("空线池连接数："+freeConnection.size());
                System.out.println("活动连接数：："+activeConnection.size());
                System.out.println("总的连接数："+contActive);
            }
        },1000);
    }
}
