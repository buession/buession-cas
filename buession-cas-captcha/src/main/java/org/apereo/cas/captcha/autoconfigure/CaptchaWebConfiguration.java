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
package org.apereo.cas.captcha.autoconfigure;

import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.geetest.GeetestParameter;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import com.buession.security.captcha.validator.CaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletAliYunCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletGeetestCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletTencentCaptchaValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enabled", havingValue = "true")
@AutoConfigureAfter({CaptchaConfiguration.class, WebMvcAutoConfiguration.class})
public class CaptchaWebConfiguration {

	private final CaptchaProperties properties;

	public CaptchaWebConfiguration(CaptchaProperties properties){
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnBean({AliYunCaptchaClient.class})
	public ServletAliYunCaptchaValidator aliYunCaptchaValidator(ObjectProvider<AliYunCaptchaClient> captchaClient){
		return new ServletAliYunCaptchaValidator(captchaClient.getIfAvailable(), properties.getAliyun().getParameter());
	}

	@Bean
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnBean({GeetestCaptchaClient.class})
	public ServletGeetestCaptchaValidator geetestCaptchaValidator(ObjectProvider<GeetestCaptchaClient> captchaClient){
		GeetestParameter parameter = "v3".equalsIgnoreCase(
				captchaClient.getIfAvailable().getVersion()) ? properties.getGeetest().getV3()
				.getParameter() : properties.getGeetest().getV4().getParameter();
		return new ServletGeetestCaptchaValidator(captchaClient.getIfAvailable(), parameter);
	}

	@Bean
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnBean({TencentCaptchaClient.class})
	public ServletTencentCaptchaValidator tencentCaptchaValidator(ObjectProvider<TencentCaptchaClient> captchaClient){
		return new ServletTencentCaptchaValidator(captchaClient.getIfAvailable(),
				properties.getTencent().getParameter());
	}

}
