package com.lds.orm.dorm.session;

import com.lds.orm.dorm.model.Page;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Title: FutureDataOperationService
 * <p>
 * Description:异步数据操作服务
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public interface FutureObjectOperationService {

    Future<Integer> ftSave(Object o);

    Future<Integer> ftDelete(Object o);

    Future<Integer> ftUpdate(Object o);

    Future<Object> ftGet(Object o);

    Future<Object> ftGet(Object o, Class<?> clzz);

    Future<List<Object>> ftList(Object o);

    Future<List<Object>> ftList(Object o, Class<?> clzz);

    Future<Page<Object>> ftPage(Object o);

    Future<Page<Object>> ftPage(Object o, Class<?> clzz);
}
