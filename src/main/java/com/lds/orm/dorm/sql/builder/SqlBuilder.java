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


import com.lds.orm.dorm.sql.SqlInfo;

/**
 * ******************  类说明  *********************
 * class       :  ISqlBuilder
 * @author     :  lds
 * @version    :  1.0  
 * description :  sql生成器接口
 * @see        :                        
 * ***********************************************
 */
public interface SqlBuilder {
	
	enum SBType{
		SAVE,
		BATCH_SAVE,
		DELETE,
		UPDATE,
		GET,
		LIST,
		PAGE_COUNT,
		PAGE_LIST,
		CREATE_TABLE
	}
	
	SqlInfo builderSql(Object o);
	
	
	
}