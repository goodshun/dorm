package com.lds.orm.dorm.session;

/**
 * Title: Transaction
 * <p>
 * Description:事务管理
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public interface Transaction {

    /**
     * method name   : start
     * description   : 开启事务
     * @return       : void
     * @param        :
     * @see          : *
     */
    void start();
    /**
     * method name   : rollback
     * description   : 回滚事务
     * @return       : void
     * @param        :
     * @see          : *
     */
    void rollback();
    /**
     * method name   : commit
     * description   : 提交事务
     * @return       : void
     * @param        :
     * @see          : *
     */
    void commit();
    /**
     * method name   : close
     * description   : 关闭事务
     * @return       : void
     * @param        :
     * @see          : *
     */
    void close();
}
