package com.lds.orm.dorm.dao;

import com.lds.orm.dorm.sql.SqlInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Title: PreparedStatementWrapper
 * <p>
 * Description: 执行语句封装处理 主要用来后期监控
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/25
 */
public class PreparedStatementWrapper {

    private PreparedStatement stmt;
    private SqlInfo sqlInfo;
    private boolean executeTimeMonitor;
    private long maxExecuteTime;

    public PreparedStatementWrapper(PreparedStatement stmt, SqlInfo sqlInfo, boolean executeTimeMonitor,
                                    long maxExecuteTime) {
        super();
        this.stmt = stmt;
        this.sqlInfo = sqlInfo;
        this.executeTimeMonitor = executeTimeMonitor;
        this.maxExecuteTime = maxExecuteTime;
    }

    public int executeUpdate() throws SQLException {
        if (!executeTimeMonitor){
            return stmt.executeUpdate();
        }


        //TODO  慢SQL、监控
        return 0;
    }

    public ResultSet executeQuery() throws SQLException {
        if (!executeTimeMonitor) {
            return stmt.executeQuery();
        }
        //TODO  慢SQL、监控
        return null;
    }

    public void close() throws SQLException {
        stmt.close();
    }
}
