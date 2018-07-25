package com.lds.orm.dorm.connection.holder;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Title: ThreadConnectionHolder
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/25
 */
public class ThreadConnectionHolder {
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();


    public static Connection getConnection(DataSource dataSource){
        return connectionThreadLocal.get();
    }

    public static void putConnection(DataSource dataSource,Connection connection){
        connectionThreadLocal.set(connection);
    }

    public static void removeConnection(){
        connectionThreadLocal.remove();
    }
}
