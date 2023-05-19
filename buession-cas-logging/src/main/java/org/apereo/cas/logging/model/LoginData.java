/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.logging.model;

import com.buession.web.utils.useragentutils.Browser;
import com.buession.web.utils.useragentutils.OperatingSystem;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class LoginData implements Serializable {

	private final static long serialVersionUID = 6911293150366028130L;

	/**
	 * 用户 ID
	 */
	private String id;

	/**
	 * 登录时间
	 */
	private Date dateTime;

	/**
	 * 客户端 IP
	 */
	private String clientIp;

	/**
	 * User-Agent
	 */
	private String userAgent;

	/**
	 * 操作系统
	 */
	private OperatingSystem operatingSystem;

	/**
	 * 浏览器
	 */
	private Browser browser;

	/**
	 * 位置信息
	 */
	private GeoLocation location;

	/**
	 * 返回用户 ID
	 *
	 * @return 用户 ID
	 */
	public String getId(){
		return id;
	}

	/**
	 * 设置用户 ID
	 *
	 * @param id
	 * 		用户 ID
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 * 返回登录时间
	 *
	 * @return 登录时间
	 */
	public Date getDateTime(){
		return dateTime;
	}

	/**
	 * 设置登录时间
	 *
	 * @param dateTime
	 * 		登录时间
	 */
	public void setDateTime(Date dateTime){
		this.dateTime = dateTime;
	}

	/**
	 * 返回客户端 IP
	 *
	 * @return 客户端 IP
	 */
	public String getClientIp(){
		return clientIp;
	}

	/**
	 * 设置客户端 IP
	 *
	 * @param clientIp
	 * 		客户端 IP
	 */
	public void setClientIp(String clientIp){
		this.clientIp = clientIp;
	}

	/**
	 * 返回 User-Agent
	 *
	 * @return User-Agent
	 */
	public String getUserAgent(){
		return userAgent;
	}

	/**
	 * 设置 User-Agent
	 *
	 * @param userAgent
	 * 		User-Agent
	 */
	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}

	/**
	 * 返回操作系统
	 *
	 * @return 操作系统
	 */
	public OperatingSystem getOperatingSystem(){
		return operatingSystem;
	}

	/**
	 * 设置操作系统
	 *
	 * @param operatingSystem
	 * 		操作系统
	 */
	public void setOperatingSystem(OperatingSystem operatingSystem){
		this.operatingSystem = operatingSystem;
	}

	/**
	 * 返回浏览器
	 *
	 * @return 浏览器
	 */
	public Browser getBrowser(){
		return browser;
	}

	/**
	 * 设置浏览器
	 *
	 * @param browser
	 * 		浏览器
	 */
	public void setBrowser(Browser browser){
		this.browser = browser;
	}

	/**
	 * 返回位置信息
	 *
	 * @return 位置信息
	 */
	public GeoLocation getLocation(){
		return location;
	}

	/**
	 * 设置位置信息
	 *
	 * @param location
	 * 		位置信息
	 */
	public void setLocation(GeoLocation location){
		this.location = location;
	}

}
