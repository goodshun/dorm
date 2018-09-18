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
import java.util.concurrent.Future;

/**
 * class       :  FutureDataBaseManipulation
 * @version    :  1.0
 * description :  异步数据库操作接口
 * @see        :  *
 */
public interface SqlFutureDataBaseManipulation {
	/**
	 * method name   : ftSave 
	 * description   : 异步保存
	 * @return       : Future<Integer>
	 * @param        : @param sql 保存SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftSqlSave(String sql, Object... params);
	/**
	 * method name   : ftDelete
	 * description   : 异步删除
	 * @return       : Future<Integer>
	 * @param        : @param sql 删除SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftSqlDelete(String sql, Object... params);
	/**
	 * method name   : ftUpdate
	 * description   : 异步更新
	 * @return       : Future<Integer>
	 * @param        : @param sql 更新SQL
	 * @param        : @param params 参数
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftSqlUpdate(String sql, Object... params);
	/**
	 * method name   : ftGet
	 * description   : 异步查询
	 * @return       : Future<Object>
	 * @param        : @param sql 查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<Object> ftSqlGet(String sql, Class<?> clzz, Object... params);
	/**
	 * method name   : ftList
	 * description   : 异步列表查询
	 * @return       : Future<List<Object>>
	 * @param        : @param sql 查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<List<Object>> ftSqlList(String sql, Class<?> clzz, Object... params);
	/**
	 * method name   : ftPage
	 * description   : 异步分页查询
	 * @return       : Future<Page<Object>>
	 * @param        : @param countSql 统计SQL
	 * @param        : @param listSql 列表查询SQL
	 * @param        : @param params 参数
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	Future<Page<Object>> ftSqlPage(String countSql, String listSql, Class<?> clzz, Object... params);


}