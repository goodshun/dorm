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

package com.lds.orm.dorm.identity;

import com.xiaoleilu.hutool.util.RandomUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * class       :  Identity
 * @author     :  zhukaipeng
 * @version    :  1.0  
 * description :  主键生成器 yyyyMMddmm+ip标识+两位随机数+7位自增序列
 * @see        :  *
 */
public class Identity {

	private static Map<String, Identity> identityCache = new HashMap<String, Identity>();
	private final AtomicInteger count = new AtomicInteger(0);
	private String ipAddress;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	private Identity() {
		ipAddress = getIpAddress()+RandomUtil.randomInt(10, 99);
	}

	private String get() {
		String day = sdf.format(System.currentTimeMillis());
		count.compareAndSet(Integer.MAX_VALUE-1000, 0);
		return String.format("%s%s%07d", day,ipAddress,count.incrementAndGet());
	}
	
	private String getIpAddress() {
		String defaultIpAddress = RandomUtil.randomNumbers(4);
		Enumeration<NetworkInterface> allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return defaultIpAddress;
		}
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					try {
						String ipAddress = ip.getHostAddress();
						return ipAddress.replaceAll("\\.", "");
					} catch (Exception e) {
						return defaultIpAddress;
					}
				}
			}
		}
		return defaultIpAddress;
	}
	/**
	 * method name   : nextId 
	 * description   : 获取主键 
	 * @return       : String
	 * @param        : @param key 主键标识
	 * @param        : @return 返回值格式:yyyyMMddHHmm+3位主机号+7位递增序列
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	public static String nextId(String key) {
		Identity i = identityCache.get(key);
		if (i == null) {
			i = new Identity();
			identityCache.put(key, i);
		}
		return i.get();
	}
	/**
	 * method name   : nextId 
	 * description   : 获取主键，默认标识为default
	 * @return       : String
	 * @param        : @return 返回值格式:yyyyMMddHHmm+3位主机号+7位递增序列
	 * modified      : zhukaipeng ,  2017年9月15日
	 * @see          : *
	 */
	public static String nextId() {
		return nextId("default");
	}
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(RandomUtil.randomInt(10, 99));
		}
	}
	
}