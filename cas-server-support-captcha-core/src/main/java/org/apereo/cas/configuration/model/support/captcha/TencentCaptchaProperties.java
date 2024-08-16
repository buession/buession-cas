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

import com.buession.security.captcha.tencent.TencentParameter;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;

/**
 * 腾讯行为验证码配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("TencentCaptchaProperties")
public class TencentCaptchaProperties {

	/**
	 * 应用 ID
	 */
	@RequiredProperty
	private String appId;

	/**
	 * 密钥
	 */
	@RequiredProperty
	private String secretKey;

	/**
	 * 提交参数配置
	 */
	private TencentParameter parameter = new TencentParameter();

	/**
	 * 返回应用 ID
	 *
	 * @return 应用 ID
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * 设置应用 ID
	 *
	 * @param appId
	 * 		应用 ID
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 返回密钥
	 *
	 * @return 密钥
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * 设置密钥
	 *
	 * @param secretKey
	 * 		密钥
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * 返回提交参数配置
	 *
	 * @return 提交参数配置
	 */
	public TencentParameter getParameter() {
		return parameter;
	}

	/**
	 * 设置提交参数配置
	 *
	 * @param parameter
	 * 		提交参数配置
	 */
	public void setParameter(TencentParameter parameter) {
		this.parameter = parameter;
	}

}
