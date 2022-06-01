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
package org.apereo.cas.captcha.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.httpclient.HttpClient;
import com.buession.security.captcha.CaptchaClient;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.netease.NetEaseCaptchaClient;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import com.buession.security.captcha.validator.AliYunCaptchaValidator;
import com.buession.security.captcha.validator.CaptchaValidator;
import com.buession.security.captcha.validator.GeetestCaptchaValidator;
import com.buession.security.captcha.validator.NetEaseCaptchaValidator;
import com.buession.security.captcha.validator.TencentCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletAliYunCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletGeetestCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletTencentCaptchaValidator;
import org.springframework.beans.factory.ObjectProvider;
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
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enabled", havingValue = "true")
public class CaptchaConfiguration {

	protected final CaptchaProperties properties;

	protected HttpClient httpClient;

	public CaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
		this.properties = properties;
		this.httpClient = httpClient.getIfAvailable();
	}

	protected void afterInitialized(final CaptchaClient captchaClient){
		captchaClient.setJavaScript(properties.getJavascript());
	}

	@Configuration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX + ".aliyun", name = "enabled", havingValue = "true")
	static class AliYunCaptchaConfiguration extends CaptchaConfiguration {

		public AliYunCaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
			super(properties, httpClient);
		}

		@Bean
		public AliYunCaptchaClient aliYunCaptchaClient(){
			final CaptchaProperties.Aliyun config = properties.getAliyun();
			final AliYunCaptchaClient client = Validate.hasText(config.getRegionId()) ? new AliYunCaptchaClient(
					config.getAccessKeyId(), config.getAccessKeySecret(), config.getRegionId(),
					httpClient) : new AliYunCaptchaClient(config.getAccessKeyId(), config.getAccessKeySecret(),
					httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		public AliYunCaptchaValidator aliYunCaptchaValidator(AliYunCaptchaClient aliYunCaptchaClient){
			return new ServletAliYunCaptchaValidator(aliYunCaptchaClient, properties.getAliyun().getParameter());
		}

	}

	@Configuration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX + ".geetest", name = "enabled", havingValue = "true")
	static class GeetestCaptchaConfiguration extends CaptchaConfiguration {

		public GeetestCaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
			super(properties, httpClient);
		}

		@Bean
		public GeetestCaptchaClient geetestCaptchaClient(){
			final CaptchaProperties.Geetest config = properties.getGeetest();
			final GeetestCaptchaClient client = new GeetestCaptchaClient(config.getAppId(), config.getSecretKey(),
					config.getVersion(), httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		public GeetestCaptchaValidator geetestCaptchaValidator(GeetestCaptchaClient geetestCaptchaClient){
			if("v3".equalsIgnoreCase(geetestCaptchaClient.getVersion())){
				return new ServletGeetestCaptchaValidator(geetestCaptchaClient, properties.getGeetest().getV3()
						.getParameter());
			}else{
				return new ServletGeetestCaptchaValidator(geetestCaptchaClient, properties.getGeetest().getV4()
						.getParameter());
			}
		}

	}

	@Configuration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX + ".netease", name = "enabled", havingValue = "true")
	static class NetEaseCaptchaConfiguration extends CaptchaConfiguration {

		public NetEaseCaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
			super(properties, httpClient);
		}

		@Bean
		public NetEaseCaptchaClient netEaseCaptchaClient(){
			final CaptchaProperties.Netease config = properties.getNetease();
			final NetEaseCaptchaClient client = new NetEaseCaptchaClient(config.getAppId(), config.getSecretKey(),
					httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		public NetEaseCaptchaValidator netEaseCaptchaValidator(NetEaseCaptchaClient netEaseCaptchaClient){
			return new NetEaseCaptchaValidator(netEaseCaptchaClient);
		}

	}

	@Configuration
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX + ".tencent", name = "enabled", havingValue = "true")
	static class TencentCaptchaConfiguration extends CaptchaConfiguration {

		public TencentCaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
			super(properties, httpClient);
		}

		@Bean
		public TencentCaptchaClient tencentCaptchaClient(){
			final CaptchaProperties.Tencent config = properties.getTencent();
			final TencentCaptchaClient client = new TencentCaptchaClient(config.getAppId(), config.getSecretKey(),
					httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		public TencentCaptchaValidator tencentCaptchaValidator(TencentCaptchaClient tencentCaptchaClient){
			return new ServletTencentCaptchaValidator(tencentCaptchaClient, properties.getTencent().getParameter());
		}

	}

}
