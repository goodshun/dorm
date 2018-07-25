package com.lds.orm.dorm.connection;

import com.lds.orm.dorm.connection.config.DbConfig;
import com.lds.orm.dorm.connection.holder.ThreadConnectionHolder;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Title: MyDataSource
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/25
 */
public class MyDataSource implements DataSource {


    private ConnectionPool pool;


    public MyDataSource(DbConfig jdbcConfig) {
        super();
        pool = new ConnectionPool(jdbcConfig);
    }


    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = ThreadConnectionHolder.getConnection(this);
        if (conn == null || conn.isClosed()) {
            conn = pool.getConnection();
            ThreadConnectionHolder.putConnection(this, conn);
        }
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return pool.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("DataSource can't support getParentLogger method!");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("Can't support unwrap method!");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("Can't support isWrapperFor method!");
    }

}
