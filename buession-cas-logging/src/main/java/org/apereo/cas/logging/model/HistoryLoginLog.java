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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryLoginLog {

	@Id
	private String id;

	/**
	 * 用户 ID
	 */
	@Field(name = "user_id", targetType = FieldType.STRING)
	private String userId;

	/**
	 * 用户名
	 */
	@Field(name = "user_name", targetType = FieldType.STRING)
	private String userName;

	/**
	 * 登录时间
	 */
	@Field(name = "login_time", targetType = FieldType.DATE_TIME)
	private Date loginTime;

	/**
	 * 登录 IP
	 */
	@Field(name = "login_ip", targetType = FieldType.STRING)
	private String loginIp;

	/**
	 * User-Agent
	 */
	@Field(name = "user_agent", targetType = FieldType.STRING)
	private String userAgent;

	/**
	 * 操作系统名称
	 */
	@Field(name = "operating_system", targetType = FieldType.IMPLICIT)
	private OperatingSystem operatingSystem;

	/**
	 * 设备类型
	 */
	@Field(name = "device_type", targetType = FieldType.STRING)
	private String deviceType;

	/**
	 * 浏览器
	 */
	@Field(name = "browser", targetType = FieldType.IMPLICIT)
	private Browser browser;

	/**
	 * 地区字段名称
	 */
	@Field(name = "district", targetType = FieldType.IMPLICIT)
	private District district;

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public Date getLoginTime(){
		return loginTime;
	}

	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}

	public String getLoginIp(){
		return loginIp;
	}

	public void setLoginIp(String loginIp){
		this.loginIp = loginIp;
	}

	public String getUserAgent(){
		return userAgent;
	}

	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}

	public OperatingSystem getOperatingSystem(){
		return operatingSystem;
	}

	public void setOperatingSystem(OperatingSystem operatingSystem){
		this.operatingSystem = operatingSystem;
	}

	public String getDeviceType(){
		return deviceType;
	}

	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}

	public Browser getBrowser(){
		return browser;
	}

	public void setBrowser(Browser browser){
		this.browser = browser;
	}

	public District getDistrict(){
		return district;
	}

	public void setDistrict(District district){
		this.district = district;
	}

	public final static class District implements Serializable {

		private final static long serialVersionUID = -2622605869813427465L;

		/**
		 * 国家 Code 字段名称
		 */
		@Field(name = "country_code", targetType = FieldType.STRING)
		private String countryCode;

		/**
		 * 国家名称字段名称
		 */
		@Field(name = "country_name", targetType = FieldType.STRING)
		private String countryName;

		/**
		 * 地区名称字段名称
		 */
		@Field(name = "name", targetType = FieldType.STRING)
		private String name;

		public String getCountryCode(){
			return countryCode;
		}

		public void setCountryCode(String countryCode){
			this.countryCode = countryCode;
		}

		public String getCountryName(){
			return countryName;
		}

		public void setCountryName(String countryName){
			this.countryName = countryName;
		}

		public String getName(){
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

	}

}
