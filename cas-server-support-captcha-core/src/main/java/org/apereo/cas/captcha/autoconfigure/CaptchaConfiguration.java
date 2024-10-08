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
package org.apereo.cas.captcha.autoconfigure;

import com.buession.httpclient.HttpClient;
import com.buession.security.captcha.CaptchaClient;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.geetest.GeetestParameter;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import com.buession.security.captcha.validator.servlet.ServletAliYunCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletGeetestCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletTencentCaptchaValidator;
import org.apereo.cas.configuration.model.support.captcha.CaptchaProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.function.Supplier;

/**
 * 验证码 Auto-Configuration
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enabled", havingValue = "true")
public class CaptchaConfiguration {

	abstract static class BaseManufacturerCaptchaConfiguration {

		protected final CaptchaProperties properties;

		protected final HttpClient httpClient;

		public BaseManufacturerCaptchaConfiguration(CaptchaProperties properties, HttpClient httpClient) {
			this.properties = properties;
			this.httpClient = httpClient;
		}

		protected <T extends CaptchaClient> T createCaptchaClient(Supplier<T> supplier) {
			final T captchaClient = supplier.get();
			captchaClient.setJavaScript(properties.getJavascript());
			return captchaClient;
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "aliyun.enabled", havingValue = "true")
	@ConditionalOnMissingBean({CaptchaClient.class})
	static class Aliyun extends BaseManufacturerCaptchaConfiguration {

		public Aliyun(CaptchaProperties properties, HttpClient httpClient) {
			super(properties, httpClient);
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public AliYunCaptchaClient aliYunCaptchaClient() {
			return createCaptchaClient(()->new AliYunCaptchaClient(properties.getAliyun().getAccessKeyId(),
					properties.getAliyun().getAccessKeySecret(), properties.getAliyun().getAppKey(),
					properties.getAliyun().getRegionId(), httpClient));
		}

		@Bean
		@ConditionalOnMissingBean({ServletAliYunCaptchaValidator.class})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ServletAliYunCaptchaValidator aliYunCaptchaValidator(AliYunCaptchaClient captchaClient) {
			return new ServletAliYunCaptchaValidator(captchaClient, properties.getAliyun().getParameter());
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "geetest.enabled", havingValue = "true")
	@ConditionalOnMissingBean({CaptchaClient.class})
	static class Geetest extends BaseManufacturerCaptchaConfiguration {

		public Geetest(CaptchaProperties properties, HttpClient httpClient) {
			super(properties, httpClient);
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public GeetestCaptchaClient geetestCaptchaClient() {
			return createCaptchaClient(()->new GeetestCaptchaClient(properties.getGeetest().getAppId(),
					properties.getGeetest().getSecretKey(), properties.getGeetest().getVersion(), httpClient));
		}

		@Bean
		@ConditionalOnMissingBean({ServletGeetestCaptchaValidator.class})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ServletGeetestCaptchaValidator geetestCaptchaValidator(GeetestCaptchaClient captchaClient) {
			GeetestParameter parameter = "v3".equalsIgnoreCase(captchaClient.getVersion()) ? properties.getGeetest()
					.getV3() : properties.getGeetest().getV4();
			return new ServletGeetestCaptchaValidator(captchaClient, parameter);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "tencent.enabled", havingValue = "true")
	@ConditionalOnMissingBean({CaptchaClient.class})
	static class Tencent extends BaseManufacturerCaptchaConfiguration {

		public Tencent(CaptchaProperties properties, HttpClient httpClient) {
			super(properties, httpClient);
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public TencentCaptchaClient tencentCaptchaClient() {
			return createCaptchaClient(()->new TencentCaptchaClient(properties.getTencent().getAppId(),
					properties.getTencent().getSecretKey(), httpClient));
		}

		@Bean
		@ConditionalOnMissingBean({ServletTencentCaptchaValidator.class})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ServletTencentCaptchaValidator tencentCaptchaValidator(TencentCaptchaClient captchaClient) {
			return new ServletTencentCaptchaValidator(captchaClient, properties.getTencent().getParameter());
		}

	}

}
