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

package com.lds.orm.dorm.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * class       :  LogicOperate
 * @author     :  lds
 * @version    :  1.0  
 * description :  条件设置类
 * @see        :  @param <T>*
 */
@SuppressWarnings("unchecked")
public abstract class ConditionSetting<T>{
	
	private Map<String,Object> lt;
	private Map<String,Object> gt;
	private Map<String,Object> le;
	private Map<String,Object> ge;
	private Map<String,Object> eq;
	private Map<String,Object> neq;
	private Map<String,Object> like;
	
	/**
	 * method name   : getLt 
	 * description   : 获取查询条件为小于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getLt() {
		return lt;
	}
	/**
	 * method name   : lt 
	 * description   : 设置小于查询条件  column < value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T lt(String column, Object value) {
		if (lt == null) 
			lt = createConditionMap();
		lt.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : lt 
	 * description   : 设置一组小于查询条件
	 * @return       : T
	 * @param        : @param ltAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T lt(Map<String,Object> ltAll) {
		if (lt == null) 
			lt = createConditionMap();
		lt.putAll(ltAll);
		return (T) this;
	}
	
	/**
	 * method name   : getGt 
	 * description   : 获取查询条件为大于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getGt() {
		return gt;
	}
	/**
	 * method name   : gt 
	 * description   : 设置大于查询条件  column > value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T gt(String column, Object value) {
		if (gt == null) 
			gt = createConditionMap();
		gt.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : gt 
	 * description   : 设置一组大于查询条件
	 * @return       : T
	 * @param        : @param gtAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T gt(Map<String,Object> gtAll) {
		if (gt == null) 
			gt = createConditionMap();
		gt.putAll(gtAll);
		return (T) this;
	}
	
	/**
	 * method name   : getLe 
	 * description   : 获取查询条件为小于等于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getLe() {
		return le;
	}
	/**
	 * method name   : le 
	 * description   : 设置小于等于的查询条件  column <= value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T le(String column, Object value) {
		if (le == null) 
			le = createConditionMap();
		le.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : leAll 
	 * description   : 设置一组小于等于查询条件
	 * @return       : T
	 * @param        : @param gtAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T le(Map<String,Object> leAll) {
		if (le == null) 
			le = createConditionMap();
		le.putAll(leAll);
		return (T) this;
	}
	
	/**
	 * method name   : getGe 
	 * description   : 获取查询条件为大于等于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getGe() {
		return ge;
	}
	/**
	 * method name   : ge 
	 * description   : 设置大于等于的查询条件  column >= value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T ge(String column, Object value) {
		if (ge == null) 
			ge = createConditionMap();
		ge.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : ge 
	 * description   : 设置一组大于等于查询条件
	 * @return       : T
	 * @param        : @param geAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T ge(Map<String,Object> geAll) {
		if (ge == null) 
			ge = createConditionMap();
		ge.putAll(geAll);
		return (T) this;
	}
	
	/**
	 * method name   : getEq 
	 * description   : 获取查询条件为等于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getEq() {
		return eq;
	}
	/**
	 * method name   : eq 
	 * description   : 设置等于的查询条件  column = value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T eq(String column, Object value) {
		if (eq == null) 
			eq = createConditionMap();
		eq.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : eq 
	 * description   : 设置一组等于查询条件
	 * @return       : T
	 * @param        : @param eqAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T eq(Map<String,Object> eqAll) {
		if (eq == null) 
			eq = createConditionMap();
		eq.putAll(eqAll);
		return (T) this;
	}
	/**
	 * method name   : getNeq 
	 * description   : 获取查询条件为不等于的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getNeq() {
		return neq;
	}
	/**
	 * method name   : neq 
	 * description   : 设置不等于的查询条件  column != value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T neq(String column, Object value) {
		if (neq == null) 
			neq = createConditionMap();
		neq.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : neq 
	 * description   : 设置一组不等于查询条件
	 * @return       : T
	 * @param        : @param neqAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T neq(Map<String,Object> neqAll) {
		if (neq == null) 
			neq = createConditionMap();
		neq.putAll(neqAll);
		return (T) this;
	}
	/**
	 * method name   : getLike 
	 * description   : 获取查询条件为like的参数
	 * @return       : Map<String,Object>
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public Map<String,Object> getLike() {
		return like;
	}
	/**
	 * method name   : like 
	 * description   : 设置like查询条件  column like value
	 * @return       : T
	 * @param        : @param column
	 * @param        : @param value
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T like(String column, Object value) {
		if (like == null) 
			like = createConditionMap();
		like.put(column, value);
		return (T) this;
	}
	/**
	 * method name   : like 
	 * description   : 设置一组like查询条件
	 * @return       : T
	 * @param        : @param likeAll
	 * @param        : @return
	 * modified      : lds ,  2017年9月15日
	 * @see          : *
	 */
	public T like(Map<String,Object> likeAll) {
		if (like == null) 
			like = createConditionMap();
		like.putAll(likeAll);
		return (T) this;
	}
	
	protected Map<String,Object> createConditionMap(){
		return new LinkedHashMap<>();
	}
}