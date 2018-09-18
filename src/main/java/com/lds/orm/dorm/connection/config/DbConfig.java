package com.lds.orm.dorm.connection.config;

import com.lds.orm.dorm.exception.ConnectionException;
import com.lds.orm.dorm.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Title: DbConfig
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/07/24
 */
public class DbConfig implements DefaultConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);

    /**数据库地址*/
    private String url;
    /**数据库用户名*/
    private String username;
    /**数据库密码*/
    private String password;
    /**数据库驱动*/
    private String driverClassName;
    /**数据库类型*/
    private String dbType;

    /**最大连接数*/
    private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
    /**最小连接数*/
    private int minPoolSize = DEFAULT_MIN_POOL_SIZE;
    /**初始化连接数*/
    private int initialPoolSize = DEFAULT_INITIAL_POOL_SIZE;
    /**是否启用sql监控*/
    private boolean executeTimeMonitor = DEFAULT_EXECUTE_TIME_MONITOR;
    /**启用sql监控后，sql最大执行时长，超过该时长的sql会被记录在文件中*/
    private long maxExecuteTime = DEFAULT_MAX_EXECUTE_TIME;
    /**最大耗时sql保存文件路径 默认classes目录下*/
    private String maxExecuteTimeFilePath = DEFAULT_MAX_EXECUTE_TIME_FILE_PATH;
    /**单位毫秒 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，超时后将抛出ConnectionException。单位毫秒。默认: 10000*/
    private int maxWaitTime = DEFAULT_MAX_WAIT_TIME;
    /**最大空闲时间*/
    private int maxIdleTime = DEFAULT_MAX_IDLE_TIME;
    /**每X毫秒检查所有连接池中的空闲连接。默认值: 0，不检查*/
    private int idleConnectionTestPeriod = DEFAULT_IDLE_CONNECTION_TEST_PERIOD;
    /**异步执行sql线程池，默认8*/
    private int asyncPoolSize = DEFAULT_ASYNC_POOL_SIZE;
    /**po所在包路径，创建表时使用*/
    private String packagePath;
    /**是否打印SQL*/
    private boolean printSql = DEFAULT_PRINT_SQL;

    public static DbConfig newInstance(String jdbcConfigPath){
        Properties propertieserties = new Properties();
        try {
            propertieserties.load(DbConfig.class.getResourceAsStream("/application.properties"));
            return new DbConfig(propertieserties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DbConfig(Properties properties){
        super();
        if (properties.getProperty("jdbc.url") != null) {
            url = properties.getProperty("jdbc.url");
            dbType = JdbcUtils.getDbType(url);
        }
        if (properties.getProperty("jdbc.username") != null) {
            username = properties.getProperty("jdbc.username");
        }
        if (properties.getProperty("jdbc.password") != null) {
            password = properties.getProperty("jdbc.password");
        }
        if (properties.getProperty("jdbc.driverClassName") != null) {
            driverClassName = properties.getProperty("jdbc.driverClassName");
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                LOGGER.error("加载数据库驱动出错",e);
                throw new ConnectionException("加载数据库驱动出错",e);
            }
        }
        if (properties.getProperty("jdbc.minPoolSize") != null) {
            minPoolSize = Integer.valueOf(properties.getProperty("jdbc.minPoolSize"));
        }
        if (properties.getProperty("jdbc.maxPoolSize") != null) {
            maxPoolSize = Integer.valueOf(properties.getProperty("jdbc.maxPoolSize"));
        }
        if (properties.getProperty("jdbc.initialPoolSize") != null) {
            initialPoolSize = Integer.valueOf(properties.getProperty("jdbc.initialPoolSize"));
        }
        if (properties.getProperty("jdbc.executeTimeMonitor") != null) {
            executeTimeMonitor = Boolean.valueOf(properties.getProperty("jdbc.executeTimeMonitor"));
        }
        if (properties.getProperty("jdbc.maxExecuteTime") != null) {
            maxExecuteTime = Long.valueOf(properties.getProperty("jdbc.maxExecuteTime"));
        }
        if (properties.getProperty("jdbc.maxExecuteTimeFilePath") != null) {
            maxExecuteTimeFilePath = properties.getProperty("jdbc.maxExecuteTimeFilePath");
        }else{
            maxExecuteTimeFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        if (properties.getProperty("jdbc.maxWaitTime") != null) {
            maxWaitTime = Integer.valueOf(properties.getProperty("jdbc.maxWaitTime"));
        }
        if (properties.getProperty("jdbc.maxIdleTime") != null) {
            maxIdleTime = Integer.valueOf(properties.getProperty("jdbc.maxIdleTime"));
        }
        if (properties.getProperty("jdbc.idleConnectionTestPeriod") != null) {
            idleConnectionTestPeriod = Integer.valueOf(properties.getProperty("jdbc.idleConnectionTestPeriod"));
        }
        if (properties.getProperty("jdbc.asyncPoolSize") != null) {
            asyncPoolSize = Integer.valueOf(properties.getProperty("jdbc.asyncPoolSize"));
        }
        if (properties.getProperty("jdbc.packagePath") != null) {
            packagePath = properties.getProperty("jdbc.packagePath");
        }
        if (properties.getProperty("jdbc.printSql") != null) {
            printSql = Boolean.valueOf(properties.getProperty("jdbc.printSql"));
        }
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getDbType() {
        return dbType;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public boolean isExecuteTimeMonitor() {
        return executeTimeMonitor;
    }

    public long getMaxExecuteTime() {
        return maxExecuteTime;
    }

    public String getMaxExecuteTimeFilePath() {
        return maxExecuteTimeFilePath;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public int getAsyncPoolSize() {
        return asyncPoolSize;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public boolean getPrintSql() {
        return printSql;
    }
}
