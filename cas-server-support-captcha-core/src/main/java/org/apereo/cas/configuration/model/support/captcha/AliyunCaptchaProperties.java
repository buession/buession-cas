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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.configuration.model.support.captcha;

import com.buession.security.captcha.aliyun.AliyunParameter;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;

/**
 * 阿里云行为验证码配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("AliyunCaptchaProperties")
public class AliyunCaptchaProperties {

	/**
	 * AccessKey ID
	 */
	@RequiredProperty
	private String accessKeyId;

	/**
	 * AccessKey Secret
	 */
	@RequiredProperty
	private String accessKeySecret;

	/**
	 * 服务使用的 App Key
	 */
	@RequiredProperty
	private String appKey;

	/**
	 * 区域 ID
	 */
	private String regionId;

	/**
	 * 提交参数配置
	 */
	private AliyunParameter parameter = new AliyunParameter();

	/**
	 * 返回 AccessKey ID
	 *
	 * @return AccessKey ID
	 */
	public String getAccessKeyId() {
		return accessKeyId;
	}

	/**
	 * 设置 AccessKey ID
	 *
	 * @param accessKeyId
	 * 		AccessKey ID
	 */
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	/**
	 * 返回 AccessKey Secret
	 *
	 * @return AccessKey Secret
	 */
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	/**
	 * 设置 AccessKey Secret
	 *
	 * @param accessKeySecret
	 * 		AccessKey Secret
	 */
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 返回服务使用的 App Key
	 *
	 * @return 服务使用的 App Key
	 */
	public String getAppKey() {
		return appKey;
	}

	/**
	 * 设置服务使用的 App Key
	 *
	 * @param appKey
	 * 		服务使用的 App Key
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/**
	 * 返回区域 ID
	 *
	 * @return 区域 ID
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * 设置区域 ID
	 *
	 * @param regionId
	 * 		区域 ID
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	/**
	 * 返回提交参数配置
	 *
	 * @return 提交参数配置
	 */
	public AliyunParameter getParameter() {
		return parameter;
	}

	/**
	 * 设置提交参数配置
	 *
	 * @param parameter
	 * 		提交参数配置
	 */
	public void setParameter(AliyunParameter parameter) {
		this.parameter = parameter;
	}

}
