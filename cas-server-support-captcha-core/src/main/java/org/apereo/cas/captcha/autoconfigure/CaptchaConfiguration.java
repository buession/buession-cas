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
package org.apereo.cas.web.flow.config;

import com.buession.httpclient.HttpClient;
import com.buession.security.captcha.CaptchaClient;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.captcha.CaptchaProperties;
import org.apereo.cas.web.flow.CasCaptchaWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

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

	private final CasConfigurationProperties casProperties;

	private final CaptchaProperties properties;

	private final ConfigurableApplicationContext applicationContext;

	private final FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private final FlowBuilderServices flowBuilderServices;

	private final HttpClient httpClient;

	public CaptchaConfiguration(CasConfigurationProperties casProperties, CaptchaProperties properties,
								ConfigurableApplicationContext applicationContext,
								@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
								ObjectProvider<FlowBuilderServices> flowBuilderServices,
								ObjectProvider<HttpClient> httpClient) {
		this.casProperties = casProperties;
		this.properties = properties;
		this.applicationContext = applicationContext;
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
		this.httpClient = httpClient.getIfAvailable();
	}

	protected void afterPropertiesSet(final CaptchaClient captchaClient) {
		captchaClient.setJavaScript(properties.getJavascript());
	}

	@Bean
	@ConditionalOnMissingBean(name = "captchaWebflowConfigurer")
	@DependsOn("defaultWebflowConfigurer")
	public CasWebflowConfigurer captchaWebflowConfigurer() {
		return new CasCaptchaWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry,
				applicationContext, casProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "captchaCasWebflowExecutionPlanConfigurer")
	public CasWebflowExecutionPlanConfigurer captchaCasWebflowExecutionPlanConfigurer() {
		return (plan)->plan.registerWebflowConfigurer(captchaWebflowConfigurer());
	}


	@Bean
	@ConditionalOnMissingBean({CaptchaClient.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "aliyun.enabled", havingValue = "true")
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public AliYunCaptchaClient aliYunCaptchaClient() {
		final AliYunCaptchaClient client = new AliYunCaptchaClient(properties.getAliyun().getAccessKeyId(),
				properties.getAliyun().getAccessKeySecret(), properties.getAliyun().getAppKey(),
				properties.getAliyun().getRegionId(), httpClient);

		afterPropertiesSet(client);

		return client;
	}

	@Bean
	@ConditionalOnMissingBean({CaptchaClient.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "geetest.enabled", havingValue = "true")
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public GeetestCaptchaClient geetestCaptchaClient() {
		final GeetestCaptchaClient client = new GeetestCaptchaClient(properties.getGeetest().getAppId(),
				properties.getGeetest().getSecretKey(), properties.getGeetest().getVersion(), httpClient);

		afterPropertiesSet(client);

		return client;
	}

	@Bean
	@ConditionalOnMissingBean({CaptchaClient.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "tencent.enabled", havingValue = "true")
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public TencentCaptchaClient tencentCaptchaClient() {
		final TencentCaptchaClient client = new TencentCaptchaClient(properties.getTencent().getAppId(),
				properties.getTencent().getSecretKey(), httpClient);

		afterPropertiesSet(client);

		return client;
	}

}
