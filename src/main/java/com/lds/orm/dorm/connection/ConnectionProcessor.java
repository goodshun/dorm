package com.lds.orm.dorm.connection;

import com.lds.orm.dorm.annotation.Join;
import com.lds.orm.dorm.cache.ClassCache;
import com.lds.orm.dorm.connection.config.DbConfig;
import com.lds.orm.dorm.dao.PreparedStatementWrapper;
import com.lds.orm.dorm.exception.ConnectionException;
import com.lds.orm.dorm.exception.ExecuteSqlException;
import com.lds.orm.dorm.exception.TransactionException;
import com.lds.orm.dorm.model.Schema;
import com.lds.orm.dorm.sql.SqlInfo;
import com.lds.orm.dorm.sql.convert.FieldConvertProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ConnectionProcessor
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/09/18
 */
public class ConnectionProcessor {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private DataSource dataSource;
    private DbConfig jdbcConfig;

    public ConnectionProcessor(DbConfig jdbcConfig) {
        super();
        this.jdbcConfig = jdbcConfig;
        this.dataSource = new MyDataSource(jdbcConfig);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Get db connection error", e);
        }

    }

    public void setAutoCommit(Connection conn, boolean commit) {
        try {
            conn.setAutoCommit(commit);
        } catch (SQLException e) {
            ConnectionExceptionCount.add(conn);
            throw new TransactionException("Open transaction error", e);
        }
    }


    public void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new TransactionException("Rollback transaction error", e);
        }
    }

    public void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new TransactionException("Commit transaction error", e);
        }
    }

    public void close(Connection conn) {
        try {
            conn.setAutoCommit(true);
            release(conn);
        } catch (SQLException e) {
            //有事务的连接关闭出现异常直接废弃
            ConnectionExceptionCount.destroy(conn);
            LOGGER.error("Close connection error", e);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new TransactionException("Close transaction error", e1);
            }
            throw new TransactionException("Close transaction error", e);
        }


    }


    public int update(Connection conn, SqlInfo sqlInfo) {
        PreparedStatementWrapper stmt = null;
        try {
            stmt = createPreparedStatement(conn, sqlInfo);
            return stmt.executeUpdate();
        } catch (SQLException e) {
//			log.error(e, "execute sql error");
            throw new ExecuteSqlException(e);
        } finally {
            close(stmt);
            release(conn);
        }
    }

    public Object get(Connection conn, SqlInfo sqlInfo,Class<?> clzz) {
        List<Object> list = list(conn, sqlInfo, clzz);
        if (list == null || list.size()==0) {
            if (clzz.isAssignableFrom(Schema.class)) {
                return Schema.open();
            }
            return null;
        }
        if (list.size()==1) {
            return list.get(0);
        }
        throw new ExecuteSqlException("Query out multiple results!");
    }

    public List<Object> list(Connection conn, SqlInfo sqlInfo,Class<?> clzz) {
        PreparedStatementWrapper stmt = null;
        ResultSet rs = null;
        try {
            stmt = createPreparedStatement(conn, sqlInfo);
            rs = stmt.executeQuery();
            return parseResultSetToObject(rs,clzz,sqlInfo);
        } catch (Exception e) {
            throw new ExecuteSqlException(e);
        } finally {
            close(stmt);
            close(rs);
            release(conn);
        }
    }

    private PreparedStatementWrapper  createPreparedStatement(Connection conn, SqlInfo sqlInfo) throws SQLException {
        if (jdbcConfig.getPrintSql()) {
            LOGGER.info("execute sql:{}", sqlInfo.getSql());
            LOGGER.info("params:{}", sqlInfo.getParam());
        }
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sqlInfo.getSql());
        } catch (SQLException e) {
            ConnectionExceptionCount.add(conn);
            throw new ExecuteSqlException(e);
        }
        List<Object> params = sqlInfo.getParam();
        for (int i = 0; i < params.size(); i++) {
            try {
                stmt.setObject(i + 1, params.get(i));
            } catch (Exception e) {
                LOGGER.error(e);
                throw new ExecuteSqlException("Setting sql param error",e);
            }
        }
        return new PreparedStatementWrapper(stmt, sqlInfo,false,jdbcConfig.getMaxExecuteTime());
    }

    private List<Object> parseResultSetToObject(ResultSet rs,Class<?> clzz, SqlInfo sqlInfo) {
        List<Object> list = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String,Object> result = new HashMap<>();
                for (int i = 0; i < count; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object value = rs.getObject(i + 1);
                    result.put(columnLabel, value);
                }
                Object o = toJavaObject(result, clzz, sqlInfo);
                list.add(o);
            }
            return list;
        } catch (SQLException e) {
            throw new ExecuteSqlException(e);
        }
    }

    private Object toJavaObject(Map<String,Object> result,Class<?> clzz, SqlInfo sqlInfo){
        try {
            if (clzz.isAssignableFrom(Map.class)) {
                return result;
            }
            if (clzz.isAssignableFrom(Schema.class)) {
                Schema s = Schema.open("");
                s.setResult(result);
                return s;
            }
            if (clzz.isAssignableFrom(Short.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return Short.parseShort(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(Float.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return Float.parseFloat(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(Integer.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return Integer.parseInt(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(Double.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return Double.parseDouble(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(Long.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return Long.parseLong(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(BigDecimal.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return new BigDecimal(result.get(result.keySet().toArray()[0]).toString());
            }
            if (clzz.isAssignableFrom(String.class)) {
                if(result.keySet().size() != 1){
                    throw new ExecuteSqlException("Query out result is not the "+clzz.getSimpleName());
                }
                return result.get(result.keySet().toArray()[0]).toString();
            }
            return fillResultObject(clzz, result,true);
        } catch (Exception e) {
            LOGGER.error( "query result convert java object error",e);
        }
        return null;

    }

    private Object fillResultObject(Class<?> clzz, Map<String,Object> result,boolean nextFind){
        Object o;
        try {
            o = clzz.newInstance();
        } catch (InstantiationException | IllegalAccessException e1) {
            return null;
        }
        String aliasUnderline = ClassCache.getAliasUnderline(clzz);
        List<Field> joinAnnotationFields = ClassCache.getJoin(clzz);
        List<Field> findAnnotationFields = ClassCache.getFind(clzz);
        List<Field> cachelist = ClassCache.getAllFieldByCache(clzz);
        List<Field> attrFieldList = new ArrayList<>(cachelist);
        attrFieldList.addAll(joinAnnotationFields);
        attrFieldList.addAll(findAnnotationFields);

        for (Field field : attrFieldList) {
            Join join = field.getAnnotation(Join.class);
            Object v = null;
            if (join != null && nextFind) {
                v = fillResultObject(field.getType(), result,false);
            }else {
                String fieldName = field.getName();
                String k = aliasUnderline + fieldName;
                v = result.get(k);
                if (v == null) {
                    v = result.get(fieldName);
                    if (v == null) { //某些数据库返回的字段全为大写
                        v = result.get(k.toUpperCase());
                        if (v == null) {
                            v = result.get(fieldName.toUpperCase());

                        }
                    }
                }
//				log.debug("key:{},value:{}", k,v);
            }
            if (v != null) {
                try {
                    field.setAccessible(true);
                    field.set(o, FieldConvertProcessor.toJava(field.getType(),v));
                } catch (Exception e) {
                    LOGGER.error( "db type to java type error",e);
                }
            }
        }
        return o;
    }

    private final void release(Connection x) {
        if (x != null) {
            try {
                if (x.getAutoCommit()) {
                    x.close();
                    SingleThreadConnectionHolder.removeConnection(dataSource);
                }
            } catch (Exception e) {//此处若获取事务状态异常，直接移除连接，防止连接占用
                SingleThreadConnectionHolder.removeConnection(dataSource);
                LOGGER.error("close connection error", e);
                try {
                    x.close();
                } catch (SQLException e1) {
                    LOGGER.error("close connection error", e1);
                }
            }
        }
    }

    private final void close(PreparedStatementWrapper x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                LOGGER.error("close statement error", e);
            }
        }
    }

    private final void close(ResultSet x) {
        if (x != null) {
            try {
                x.close();
            } catch (Exception e) {
                LOGGER.error("close resultset error", e);
            }
        }
    }

}
