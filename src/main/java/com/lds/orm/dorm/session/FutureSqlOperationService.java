package com.lds.orm.dorm.session;

import com.lds.orm.dorm.model.Page;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Title: FutureSqlOperationService
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public interface FutureSqlOperationService {


    Future<Integer> ftSqlSave(String sql, Object ... params);

    Future<Integer> ftSqlDelete(String sql, Object ... params);

    Future<Integer> ftSqlUpdate(String sql, Object ... params);

    Future<Object> ftSqlGet(String sql, Class<?> clzz, Object ... params);

    Future<List<Object>> ftSqlList(String sql, Class<?> clzz, Object ... params);

    Future<Page<Object>> ftSqlPage(String countSql,String listSql, Class<?> clzz, Object ... params);
}
