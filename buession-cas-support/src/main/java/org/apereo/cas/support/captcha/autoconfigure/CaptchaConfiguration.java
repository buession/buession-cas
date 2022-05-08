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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.support.captcha.autoconfigure;

import com.buession.httpclient.HttpClient;
import com.buession.security.geetest.DefaultGeetestClient;
import com.buession.security.geetest.GeetestClient;
import com.buession.security.geetest.GeetestException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码 Auto-Configuration
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {

	private final CaptchaProperties properties;

	public CaptchaConfiguration(CaptchaProperties properties){
		this.properties = properties;
	}

	@Bean
	@ConditionalOnBean({HttpClient.class})
	@ConditionalOnClass({GeetestClient.class})
	@ConditionalOnMissingBean({GeetestClient.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "geetest")
	public GeetestClient geetestClient(HttpClient httpClient) throws GeetestException{
		return new DefaultGeetestClient(properties.getGeetest().getGeetestId(), properties.getGeetest().getGeetestKey(),
				properties.getGeetest().getVersion(), httpClient);
	}

}
