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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.support.captcha.autoconfigure;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = CaptchaProperties.PREFIX)
public class CaptchaProperties {

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".captcha";

	/**
	 * 是否启用验证码
	 */
	private boolean enable;

	/**
	 * 密码错误 maxPasswordFailure 后启用验证码，0 为始终启用
	 */
	private int maxPasswordFailure;

	public boolean isEnable(){
		return enable;
	}

	public void setEnable(boolean enable){
		this.enable = enable;
	}

	public int getMaxPasswordFailure(){
		return maxPasswordFailure;
	}

	public void setMaxPasswordFailure(int maxPasswordFailure){
		this.maxPasswordFailure = maxPasswordFailure;
	}

}
