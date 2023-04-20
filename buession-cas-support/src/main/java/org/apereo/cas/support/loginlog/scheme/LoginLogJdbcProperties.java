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
package org.apereo.cas.support.loginlog.scheme;

import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class LoginLogJdbcProperties extends AbstractJpaProperties {

	/**
	 * 数据表名称
	 */
	private String tableName;

	/**
	 * 标识字段名称
	 */
	private String idFieldName;

	/**
	 * 登录时间字段名称
	 */
	private String loginTimeFieldName;

	/**
	 * 登录 IP 字段名称
	 */
	private String loginIpFieldName;

	/**
	 * User-Agent 字段名称
	 */
	private String userAgentFieldName;

	/**
	 * 操作系统名称字段名称
	 */
	private String operatingSystemNameFieldName;

	/**
	 * 操作系统版本字段名称
	 */
	private String operatingSystemVersionFieldName;

	/**
	 * 设备类型字段名称
	 */
	private String deviceTypeFieldName;

	/**
	 * 浏览器名称字段名称
	 */
	private String browserNameFieldName;

	/**
	 * 浏览器版本字段名称
	 */
	private String browserVersionFieldName;

	/**
	 * 国家 Code 字段名称
	 */
	private String countryCodeFieldName;

	/**
	 * 国家名称字段名称
	 */
	private String countryNameFieldName;

	/**
	 * 地区名称字段名称
	 */
	private String districtNameFieldName;

	/**
	 * 上次登录时间字段名称
	 */
	private String lastLoginTimeFieldName;

	/**
	 * 上次登录 IP 字段名称
	 */
	private String lastLoginIpFieldName;

	/**
	 * 上次登录 User-Agent 字段名称
	 */
	private String lastUserAgentFieldName;

	/**
	 * 上次登录操作系统名称字段名称
	 */
	private String lastOperatingSystemNameFieldName;

	/**
	 * 上次登录操作系统版本字段名称
	 */
	private String lastOperatingSystemVersionFieldName;

	/**
	 * 上次登录设备类型字段名称
	 */
	private String lastDeviceTypeFieldName;

	/**
	 * 上次登录浏览器名称字段名称
	 */
	private String lastBrowserNameFieldName;

	/**
	 * 上次登录浏览器版本字段名称
	 */
	private String lastBrowserVersionFieldName;

	/**
	 * 上次登录国家 Code 字段名称
	 */
	private String lastCountryCodeFieldName;

	/**
	 * 上次登录国家名称字段名称
	 */
	private String lastCountryNameFieldName;

	/**
	 * 上次登录地区名称字段名称
	 */
	private String lastDistrictNameFieldName;

	/**
	 * 返回数据表名称
	 *
	 * @return 数据表名称
	 */
	public String getTableName(){
		return tableName;
	}

	/**
	 * 设置数据表名称
	 *
	 * @param tableName
	 * 		数据表名称
	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	/**
	 * 返回标识字段名称
	 *
	 * @return 标识字段名称
	 */
	public String getIdFieldName(){
		return idFieldName;
	}

	/**
	 * 设置标识字段名称
	 *
	 * @param idFieldName
	 * 		标识字段名称
	 */
	public void setIdFieldName(String idFieldName){
		this.idFieldName = idFieldName;
	}

	/**
	 * 返回登录时间字段名称
	 *
	 * @return 登录时间字段名称
	 */
	public String getLoginTimeFieldName(){
		return loginTimeFieldName;
	}

	/**
	 * 设置登录时间字段名称
	 *
	 * @param loginTimeFieldName
	 * 		登录时间字段名称
	 */
	public void setLoginTimeFieldName(String loginTimeFieldName){
		this.loginTimeFieldName = loginTimeFieldName;
	}

	/**
	 * 返回登录 IP 字段名称
	 *
	 * @return 登录 IP 字段名称
	 */
	public String getLoginIpFieldName(){
		return loginIpFieldName;
	}

	/**
	 * 设置登录 IP 字段名称
	 *
	 * @param loginIpFieldName
	 * 		登录 IP 字段名称
	 */
	public void setLoginIpFieldName(String loginIpFieldName){
		this.loginIpFieldName = loginIpFieldName;
	}

	/**
	 * 返回 User-Agent 字段名称
	 *
	 * @return User-Agent 字段名称
	 */
	public String getUserAgentFieldName(){
		return userAgentFieldName;
	}

	/**
	 * 设置 User-Agent 字段名称
	 *
	 * @param userAgentFieldName
	 * 		User-Agent 字段名称
	 */
	public void setUserAgentFieldName(String userAgentFieldName){
		this.userAgentFieldName = userAgentFieldName;
	}

	/**
	 * 返回操作系统名称字段名称
	 *
	 * @return 操作系统名称字段名称
	 */
	public String getOperatingSystemNameFieldName(){
		return operatingSystemNameFieldName;
	}

	/**
	 * 设置操作系统名称字段名称
	 *
	 * @param operatingSystemNameFieldName
	 * 		操作系统名称字段名称
	 */
	public void setOperatingSystemNameFieldName(String operatingSystemNameFieldName){
		this.operatingSystemNameFieldName = operatingSystemNameFieldName;
	}

	/**
	 * 返回操作系统版本字段名称
	 *
	 * @return 操作系统版本字段名称
	 */
	public String getOperatingSystemVersionFieldName(){
		return operatingSystemVersionFieldName;
	}

	/**
	 * 设置操作系统版本字段名称
	 *
	 * @param operatingSystemVersionFieldName
	 * 		操作系统版本字段名称
	 */
	public void setOperatingSystemVersionFieldName(String operatingSystemVersionFieldName){
		this.operatingSystemVersionFieldName = operatingSystemVersionFieldName;
	}

	/**
	 * 返回设备类型字段名称
	 *
	 * @return 设备类型字段名称
	 */
	public String getDeviceTypeFieldName(){
		return deviceTypeFieldName;
	}

	/**
	 * 设置设备类型字段名称
	 *
	 * @param deviceTypeFieldName
	 * 		设备类型字段名称
	 */
	public void setDeviceTypeFieldName(String deviceTypeFieldName){
		this.deviceTypeFieldName = deviceTypeFieldName;
	}

	/**
	 * 返回浏览器名称字段名称
	 *
	 * @return 浏览器名称字段名称
	 */
	public String getBrowserNameFieldName(){
		return browserNameFieldName;
	}

	/**
	 * 设置浏览器名称字段名称
	 *
	 * @param browserNameFieldName
	 * 		浏览器名称字段名称
	 */
	public void setBrowserNameFieldName(String browserNameFieldName){
		this.browserNameFieldName = browserNameFieldName;
	}

	/**
	 * 返回浏览器版本字段名称
	 *
	 * @return 浏览器版本字段名称
	 */
	public String getBrowserVersionFieldName(){
		return browserVersionFieldName;
	}

	/**
	 * 设置浏览器版本字段名称
	 *
	 * @param browserVersionFieldName
	 * 		浏览器版本字段名称
	 */
	public void setBrowserVersionFieldName(String browserVersionFieldName){
		this.browserVersionFieldName = browserVersionFieldName;
	}

	/**
	 * 返回国家 Code 字段名称
	 *
	 * @return 国家 Code 字段名称
	 */
	public String getCountryCodeFieldName(){
		return countryCodeFieldName;
	}

	/**
	 * 设置国家 Code 字段名称
	 *
	 * @param countryCodeFieldName
	 * 		国家 Code 字段名称
	 */
	public void setCountryCodeFieldName(String countryCodeFieldName){
		this.countryCodeFieldName = countryCodeFieldName;
	}

	/**
	 * 返回国家名称字段名称
	 *
	 * @return 国家名称字段名称
	 */
	public String getCountryNameFieldName(){
		return countryNameFieldName;
	}

	/**
	 * 设置国家名称字段名称
	 *
	 * @param countryNameFieldName
	 * 		国家名称字段名称
	 */
	public void setCountryNameFieldName(String countryNameFieldName){
		this.countryNameFieldName = countryNameFieldName;
	}

	/**
	 * 返回地区名称字段名称
	 *
	 * @return 地区名称字段名称
	 */
	public String getDistrictNameFieldName(){
		return districtNameFieldName;
	}

	/**
	 * 设置地区名称字段名称
	 *
	 * @param districtNameFieldName
	 * 		地区名称字段名称
	 */
	public void setDistrictNameFieldName(String districtNameFieldName){
		this.districtNameFieldName = districtNameFieldName;
	}

	/**
	 * 返回上次登录时间字段名称
	 *
	 * @return 上次登录时间字段名称
	 */
	public String getLastLoginTimeFieldName(){
		return lastLoginTimeFieldName;
	}

	/**
	 * 设置上次登录时间字段名称
	 *
	 * @param lastLoginTimeFieldName
	 * 		上次登录时间字段名称
	 */
	public void setLastLoginTimeFieldName(String lastLoginTimeFieldName){
		this.lastLoginTimeFieldName = lastLoginTimeFieldName;
	}

	/**
	 * 返回上次登录 IP 字段名称
	 *
	 * @return 上次登录 IP 字段名称
	 */
	public String getLastLoginIpFieldName(){
		return lastLoginIpFieldName;
	}

	/**
	 * 设置上次登录 IP 字段名称
	 *
	 * @param lastLoginIpFieldName
	 * 		上次登录 IP 字段名称
	 */
	public void setLastLoginIpFieldName(String lastLoginIpFieldName){
		this.lastLoginIpFieldName = lastLoginIpFieldName;
	}

	/**
	 * 返回上次登录 User-Agent 字段名称
	 *
	 * @return 上次登录 User-Agent 字段名称
	 */
	public String getLastUserAgentFieldName(){
		return lastUserAgentFieldName;
	}

	/**
	 * 设置上次登录 User-Agent 字段名称
	 *
	 * @param lastUserAgentFieldName
	 * 		上次登录 User-Agent 字段名称
	 */
	public void setLastUserAgentFieldName(String lastUserAgentFieldName){
		this.lastUserAgentFieldName = lastUserAgentFieldName;
	}

	/**
	 * 返回上次登录操作系统名称字段名称
	 *
	 * @return 上次登录操作系统名称字段名称
	 */
	public String getLastOperatingSystemNameFieldName(){
		return lastOperatingSystemNameFieldName;
	}

	/**
	 * 设置上次登录操作系统名称字段名称
	 *
	 * @param lastOperatingSystemNameFieldName
	 * 		上次登录操作系统名称字段名称
	 */
	public void setLastOperatingSystemNameFieldName(String lastOperatingSystemNameFieldName){
		this.lastOperatingSystemNameFieldName = lastOperatingSystemNameFieldName;
	}

	/**
	 * 返回上次登录操作系统版本字段名称
	 *
	 * @return 上次登录操作系统版本字段名称
	 */
	public String getLastOperatingSystemVersionFieldName(){
		return lastOperatingSystemVersionFieldName;
	}

	/**
	 * 设置上次登录操作系统版本字段名称
	 *
	 * @param lastOperatingSystemVersionFieldName
	 * 		上次登录操作系统版本字段名称
	 */
	public void setLastOperatingSystemVersionFieldName(String lastOperatingSystemVersionFieldName){
		this.lastOperatingSystemVersionFieldName = lastOperatingSystemVersionFieldName;
	}

	/**
	 * 返回上次登录设备类型字段名称
	 *
	 * @return 上次登录设备类型字段名称
	 */
	public String getLastDeviceTypeFieldName(){
		return lastDeviceTypeFieldName;
	}

	/**
	 * 设置上次登录设备类型字段名称
	 *
	 * @param lastDeviceTypeFieldName
	 * 		上次登录设备类型字段名称
	 */
	public void setLastDeviceTypeFieldName(String lastDeviceTypeFieldName){
		this.lastDeviceTypeFieldName = lastDeviceTypeFieldName;
	}

	/**
	 * 返回上次登录浏览器名称字段名称
	 *
	 * @return 上次登录浏览器名称字段名称
	 */
	public String getLastBrowserNameFieldName(){
		return lastBrowserNameFieldName;
	}

	/**
	 * 设置上次登录浏览器名称字段名称
	 *
	 * @param lastBrowserNameFieldName
	 * 		上次登录浏览器名称字段名称
	 */
	public void setLastBrowserNameFieldName(String lastBrowserNameFieldName){
		this.lastBrowserNameFieldName = lastBrowserNameFieldName;
	}

	/**
	 * 返回上次登录浏览器版本字段名称
	 *
	 * @return 上次登录浏览器版本字段名称
	 */
	public String getLastBrowserVersionFieldName(){
		return lastBrowserVersionFieldName;
	}

	/**
	 * 设置上次登录浏览器版本字段名称
	 *
	 * @param lastBrowserVersionFieldName
	 * 		上次登录浏览器版本字段名称
	 */
	public void setLastBrowserVersionFieldName(String lastBrowserVersionFieldName){
		this.lastBrowserVersionFieldName = lastBrowserVersionFieldName;
	}

	/**
	 * 返回上次登录国家 Code 字段名称
	 *
	 * @return 上次登录国家 Code 字段名称
	 */
	public String getLastCountryCodeFieldName(){
		return lastCountryCodeFieldName;
	}

	/**
	 * 设置上次登录国家 Code 字段名称
	 *
	 * @param lastCountryCodeFieldName
	 * 		上次登录国家 Code 字段名称
	 */
	public void setLastCountryCodeFieldName(String lastCountryCodeFieldName){
		this.lastCountryCodeFieldName = lastCountryCodeFieldName;
	}

	/**
	 * 返回上次登录国家名称字段名称
	 *
	 * @return 上次登录国家名称字段名称
	 */
	public String getLastCountryNameFieldName(){
		return lastCountryNameFieldName;
	}

	/**
	 * 设置上次登录国家名称字段名称
	 *
	 * @param lastCountryNameFieldName
	 * 		上次登录国家名称字段名称
	 */
	public void setLastCountryNameFieldName(String lastCountryNameFieldName){
		this.lastCountryNameFieldName = lastCountryNameFieldName;
	}

	/**
	 * 返回上次登录地区名称字段名称
	 *
	 * @return 上次登录地区名称字段名称
	 */
	public String getLastDistrictNameFieldName(){
		return lastDistrictNameFieldName;
	}

	/**
	 * 设置上次登录地区名称字段名称
	 *
	 * @param lastDistrictNameFieldName
	 * 		上次登录地区名称字段名称
	 */
	public void setLastDistrictNameFieldName(String lastDistrictNameFieldName){
		this.lastDistrictNameFieldName = lastDistrictNameFieldName;
	}

}
