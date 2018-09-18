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

package com.lds.orm.dorm.sql.builder;

import com.lds.orm.dorm.model.Page;
import com.lds.orm.dorm.sql.SqlInfo;

import java.util.Map;

/**
 * ******************  类说明  *********************
 * class       :  GetSqlBuilder
 * @author     :  lds
 * @version    :  1.0  
 * description :  生成get SQL
 * @see        :                        
 * ***********************************************
 */
public class DB2PageListSqlBuilder extends AbstractSqlBuilder {
	private static final String PAGE_LIST_TEMPLATE = "select * from ( select row_.*, rownumber() over() as rownumber_ from (#listSql)as row_ ) as t where rownumber_ > #start and rownumber_ <= #end ";
	private SqlBuilder listBuilder;
	
	public DB2PageListSqlBuilder(SqlBuilder listBuilder) {
		super();
		this.listBuilder = listBuilder;
	}



	@Override
	public SqlInfo builderSql(Object o) {
		Map<String,Integer> pageInfo = Page.getPageInfo();
		SqlInfo sqlInfo = listBuilder.builderSql(o);
		String sql = sqlInfo.getSql();
		Integer pageNum = pageInfo.get("pageNum");
		Integer pageSize = pageInfo.get("pageSize");
		Integer start = (pageNum-1)*pageSize;
		Integer end = pageNum*pageSize;
		sql = PAGE_LIST_TEMPLATE.replace("#listSql", sql);
		sql = sql.replace("#start", start.toString());
		sql = sql.replace("#end", end.toString());
		sqlInfo.setSql(sql);
		
		return sqlInfo;
	}
	
}