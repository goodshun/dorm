package com.lds.orm.dorm.session;

import com.lds.orm.dorm.model.Page;

import java.util.List;

/**
 * Title: DataOperationService
 * <p>
 * Description:
 * </p>
 *
 * @author liudongshun
 * @version V1.0
 * @since 2018/08/09
 */
public interface ObjectOperationService {

    /**
     * 保存数据
     *
     * @param o 保存数据类型
     * @return 受影响行数
     */
    int save(Object o);

    /**
     * method name   : batchSave
     * description   : 保存一批数据
     * @return       : int
     * @param        : @param list
     * @param        : @return
     * modified      : zhukaipeng ,  2018年1月28日
     */
//	int batchSave(List<?> list);

    /**
     * 删除了数据
     *
     * @param o 保存数据类型
     * @return 受影响行数
     */
    int delete(Object o);

    /**
     * update data
     * @param o data
     * @return  affect rows
     */
    int update(Object o);


    /**
     *
     * select data
     *
     * @param o   select condition
     * @return
     */
    Object get(Object o);


    List<Object> list(Object o);


    /**
     *
     * select data
     *
     * @param o   select condition
     *
     * @param clazz  指定返回类型
     * @return
     */
    List<Object> list(Object o,Class<?> clazz);


    Page<Object> page(Object o);

    Page<Object> page(Object o, Class<?> clazz);
}
