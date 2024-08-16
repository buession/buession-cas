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

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
@ConfigurationProperties(prefix = CaptchaProperties.PREFIX)
@JsonFilter("CaptchaProperties")
public class CaptchaProperties {

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".captcha";

	/**
	 * 前端 JavaScript 库地址
	 */
	private String[] javascript;

	/**
	 * 阿里云行为验证码配置
	 */
	@NestedConfigurationProperty
	private AliyunCaptchaProperties aliyun = new AliyunCaptchaProperties();

	/**
	 * 极验行为验证码配置
	 */
	private GeetestCaptchaProperties geetest;

	/**
	 * 腾讯行为验证码配置
	 */
	private TencentCaptchaProperties tencent;

	/**
	 * 返回前端 JavaScript 库地址
	 *
	 * @return 前端 JavaScript 库地址
	 */
	public String[] getJavascript() {
		return javascript;
	}

	/**
	 * 设置前端 JavaScript 库地址
	 *
	 * @param javascript
	 * 		前端 JavaScript 库地址
	 */
	public void setJavascript(String[] javascript) {
		this.javascript = javascript;
	}

	/**
	 * 返回阿里云行为验证码配置
	 *
	 * @return 阿里云行为验证码配置
	 */
	public AliyunCaptchaProperties getAliyun() {
		return aliyun;
	}

	/**
	 * 设置阿里云行为验证码配置
	 *
	 * @param aliyun
	 * 		阿里云行为验证码配置
	 */
	public void setAliyun(AliyunCaptchaProperties aliyun) {
		this.aliyun = aliyun;
	}

	/**
	 * 返回极验行为验证码配置
	 *
	 * @return 极验行为验证码配置
	 */
	public GeetestCaptchaProperties getGeetest() {
		return geetest;
	}

	/**
	 * 设置极验行为验证码配置
	 *
	 * @param geetest
	 * 		极验行为验证码配置
	 */
	public void setGeetest(GeetestCaptchaProperties geetest) {
		this.geetest = geetest;
	}

	/**
	 * 返回腾讯行为验证码配置
	 *
	 * @return 腾讯行为验证码配置
	 */
	public TencentCaptchaProperties getTencent() {
		return tencent;
	}

	/**
	 * 设置腾讯行为验证码配置
	 *
	 * @param tencent
	 * 		腾讯行为验证码配置
	 */
	public void setTencent(TencentCaptchaProperties tencent) {
		this.tencent = tencent;
	}

}
