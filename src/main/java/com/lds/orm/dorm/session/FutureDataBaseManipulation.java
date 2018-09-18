/**
 * Copyright (c) 2017, ZhuKaipeng 朱开鹏 (2076528290@qq.com).

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

import java.util.List;
import java.util.concurrent.Future;

import kim.zkp.quick.orm.model.Page;

/**
 * class       :  FutureDataBaseManipulation
 * @author     :  zhukaipeng
 * @version    :  1.0  
 * description :  异步数据库操作接口
 * @see        :  *
 */
public interface FutureDataBaseManipulation {
	/**
	 * method name   : ftSave 
	 * description   : 异步保存
	 * @return       : Future<Integer>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftSave(Object o);
	/**
	 * method name   : ftDelete 
	 * description   : 异步删除
	 * @return       : Future<Integer>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftDelete(Object o);
	/**
	 * method name   : ftUpdate 
	 * description   : 异步更新
	 * @return       : Future<Integer>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Integer> ftUpdate(Object o);
	/**
	 * method name   : ftGet 
	 * description   : 异步查询
	 * @return       : Future<Object>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Object> ftGet(Object o);
	/**
	 * method name   : ftGet 
	 * description   : 异步查询
	 * @return       : Future<Object>
	 * @param        : @param o
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Object> ftGet(Object o, Class<?> clzz);
	/**
	 * method name   : ftList 
	 * description   : 异步列表查询
	 * @return       : Future<List<Object>>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<List<Object>> ftList(Object o);
	/**
	 * method name   : ftList 
	 * description   : 异步列表查询
	 * @return       : Future<List<Object>>
	 * @param        : @param o
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<List<Object>> ftList(Object o, Class<?> clzz);
	/**
	 * method name   : ftPage 
	 * description   : 异步分页查询
	 * @return       : Future<Page<Object>>
	 * @param        : @param o
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Page<Object>> ftPage(Object o);
	/**
	 * method name   : ftPage 
	 * description   : 异步分页查询
	 * @return       : Future<Page<Object>>
	 * @param        : @param o
	 * @param        : @param clzz 返回值类型
	 * @param        : @return
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	Future<Page<Object>> ftPage(Object o, Class<?> clzz);


}