/**
 * Copyright (c) 2017, lds 刘东顺 (994546508@qq.com).

 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lds.orm.dorm.session;


import com.lds.orm.dorm.model.Page;

import java.util.List;

/**
 * class       :  SqlDataBaseManipulation
 * @version    :  1.0
 * description :  sql同步操作数据库接口
 * @see        :  *
 */
public interface SqlDataBaseManipulation {
	/**
	 * method name   : sqlSave 
	 * description   : 保存
	 * @return       : int
	 * @param        : @param sql 保存SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	int sqlSave(String sql, Object... params);
	/**
	 * method name   : delete
	 * description   : 删除
	 * @return       : int
	 * @param        : @param sql 删除SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	int sqlDelete(String sql, Object... params);
	/**
	 * method name   : update
	 * description   : 更新
	 * @return       : int
	 * @param        : @param sql 更新SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	int sqlUpdate(String sql, Object... params);
	/**
	 * method name   : get
	 * description   : 查询
	 * @return       : Object
	 * @param        : @param sql 查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Object sqlGet(String sql, Class<?> clzz, Object... params);
	/**
	 * method name   : list
	 * description   : 列表查询
	 * @return       : List<Object>
	 * @param        : @param sql 查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	List<Object> sqlList(String sql, Class<?> clzz, Object... params);
	/**
	 * method name   : page
	 * description   : 分页查询
	 * @return       : Page<Object>
	 * @param        : @param countSql 统计SQL
	 * @param        : @param listSql 列表查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Page<Object> sqlPage(String countSql, String listSql, Class<?> clzz, Object... params);
	

}