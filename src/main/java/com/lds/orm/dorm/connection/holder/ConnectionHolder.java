package com.lds.orm.dorm.connection.holder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: ConnectionHolder
 * <p>
 * Description: 数据源  连接持有对象
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/25
 */
public class ConnectionHolder {
    private Map<DataSource,Connection> connectionMap = new HashMap<>();

    public Connection getConnection(DataSource dataSource){
        return connectionMap.get(dataSource);
    }

    public void putConnection(DataSource dataSource,Connection connection){
        connectionMap.put(dataSource,connection);
    }

    public void removeConnection(){

    }
}
