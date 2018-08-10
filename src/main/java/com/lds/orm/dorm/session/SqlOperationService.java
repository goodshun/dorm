package com.lds.orm.dorm.session;

import com.lds.orm.dorm.model.Page;

import java.util.List;

/**
 * Title: SqlOperationService
 * <p>
 * Description:sql 操作
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public interface SqlOperationService {


    int sqlSave(String sql, Object ... params);

    int sqlDelete(String sql, Object ... params);

    int sqlUpdate(String sql, Object ... params);

    Object sqlGet(String sql, Class<?> clzz, Object ... params);

    List<Object> sqlList(String sql, Class<?> clzz, Object ... params);

    Page<Object> sqlPage(String countSql, String listSql, Class<?> clzz, Object ... params);
}
