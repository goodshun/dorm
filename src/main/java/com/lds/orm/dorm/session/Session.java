package com.lds.orm.dorm.session;

import com.lds.orm.dorm.model.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Title: Session
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public class Session implements ObjectOperationService,FutureObjectOperationService,SqlOperationService,FutureSqlOperationService,Transaction {



    private static final Logger LOGGER = LogManager.getLogger(Session.class);


    /**
     * 保存数据
     *
     * @param o 保存数据类型
     * @return 受影响行数
     */
    @Override
    public int save(Object o) {
        return 0;
    }

    /**
     * 删除了数据
     *
     * @param o 保存数据类型
     * @return 受影响行数
     */
    @Override
    public int delete(Object o) {
        return 0;
    }

    /**
     * update data
     *
     * @param o data
     * @return affect rows
     */
    @Override
    public int update(Object o) {
        return 0;
    }

    /**
     * select data
     *
     * @param o select condition
     * @return
     */
    @Override
    public Object get(Object o) {
        return null;
    }

    @Override
    public List<Object> list(Object o) {
        return null;
    }

    /**
     * select data
     *
     * @param o     select condition
     * @param clazz 指定返回类型
     * @return
     */
    @Override
    public List<Object> list(Object o, Class<?> clazz) {
        return null;
    }

    @Override
    public Page<Object> page(Object o) {
        return null;
    }

    @Override
    public Page<Object> page(Object o, Class<?> clazz) {
        return null;
    }

    @Override
    public int sqlSave(String sql, Object... params) {
        return 0;
    }

    @Override
    public int sqlDelete(String sql, Object... params) {
        return 0;
    }

    @Override
    public int sqlUpdate(String sql, Object... params) {
        return 0;
    }

    @Override
    public Object sqlGet(String sql, Class<?> clzz, Object... params) {
        return null;
    }

    @Override
    public List<Object> sqlList(String sql, Class<?> clzz, Object... params) {
        return null;
    }

    @Override
    public Page<Object> sqlPage(String countSql, String listSql, Class<?> clzz, Object... params) {
        return null;
    }


    @Override
    public Future<Integer> ftSave(Object o) {
        return null;
    }

    @Override
    public Future<Integer> ftDelete(Object o) {
        return null;
    }

    @Override
    public Future<Integer> ftUpdate(Object o) {
        return null;
    }

    @Override
    public Future<Object> ftGet(Object o) {
        return null;
    }

    @Override
    public Future<Object> ftGet(Object o, Class<?> clzz) {
        return null;
    }

    @Override
    public Future<List<Object>> ftList(Object o) {
        return null;
    }

    @Override
    public Future<List<Object>> ftList(Object o, Class<?> clzz) {
        return null;
    }

    @Override
    public Future<Page<Object>> ftPage(Object o) {
        return null;
    }

    @Override
    public Future<Page<Object>> ftPage(Object o, Class<?> clzz) {
        return null;
    }

    @Override
    public Future<Integer> ftSqlSave(String sql, Object... params) {
        return null;
    }

    @Override
    public Future<Integer> ftSqlDelete(String sql, Object... params) {
        return null;
    }

    @Override
    public Future<Integer> ftSqlUpdate(String sql, Object... params) {
        return null;
    }

    @Override
    public Future<Object> ftSqlGet(String sql, Class<?> clzz, Object... params) {
        return null;
    }

    @Override
    public Future<List<Object>> ftSqlList(String sql, Class<?> clzz, Object... params) {
        return null;
    }

    @Override
    public Future<Page<Object>> ftSqlPage(String countSql, String listSql, Class<?> clzz, Object... params) {
        return null;
    }


    /**
     * method name   : start
     * description   : 开启事务
     *
     * @return : void
     * @see : *
     */
    @Override
    public void start() {

    }

    /**
     * method name   : rollback
     * description   : 回滚事务
     *
     * @return : void
     * @see : *
     */
    @Override
    public void rollback() {

    }

    /**
     * method name   : commit
     * description   : 提交事务
     *
     * @return : void
     * @see : *
     */
    @Override
    public void commit() {

    }

    /**
     * method name   : close
     * description   : 关闭事务
     *
     * @return : void
     * @see : *
     */
    @Override
    public void close() {

    }
}
