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

import com.buession.security.captcha.geetest.api.v3.GeetestV3Parameter;
import com.buession.security.captcha.geetest.api.v4.GeetestV4Parameter;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;

import java.io.Serializable;

/**
 * 极验行为验证码配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("GeetestCaptchaProperties")
public class GeetestCaptchaProperties implements Serializable {

	private final static long serialVersionUID = -367740360001445826L;

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
	 * 版本
	 */
	private String version = "v4";

	/**
	 * V3 版本配置
	 */
	private GeetestV3Parameter v3 = new GeetestV3Parameter();

	/**
	 * V4 版本配置
	 */
	private GeetestV4Parameter v4 = new GeetestV4Parameter();

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
	 * 返回版本
	 *
	 * @return 版本
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 *
	 * @param version
	 * 		版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 返回 V3 版本配置
	 *
	 * @return V3 版本配置
	 */
	public GeetestV3Parameter getV3() {
		return v3;
	}

	/**
	 * 设置 V3 版本配置
	 *
	 * @param v3
	 * 		V3 版本配置
	 */
	public void setV3(GeetestV3Parameter v3) {
		this.v3 = v3;
	}

	/**
	 * 返回 V4 版本配置
	 *
	 * @return V4 版本配置
	 */
	public GeetestV4Parameter getV4() {
		return v4;
	}

	/**
	 * 设置 V4 版本配置
	 *
	 * @param v4
	 * 		V4 版本配置
	 */
	public void setV4(GeetestV4Parameter v4) {
		this.v4 = v4;
	}

}
