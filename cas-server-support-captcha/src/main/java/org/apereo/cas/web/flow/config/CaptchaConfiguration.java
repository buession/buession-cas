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

import com.buession.security.captcha.validator.servlet.ServletCaptchaValidator;
import org.apereo.cas.captcha.CaptchaConstants;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.captcha.CaptchaProperties;
import org.apereo.cas.web.flow.CasCaptchaWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.action.InitializeCaptchaAction;
import org.apereo.cas.web.flow.action.ValidateCaptchaAction;
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
import org.springframework.webflow.execution.Action;

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

	private final CaptchaProperties captchaProperties;

	private final ConfigurableApplicationContext applicationContext;

	private final FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private final FlowBuilderServices flowBuilderServices;

	public CaptchaConfiguration(CasConfigurationProperties casProperties, CaptchaProperties captchaProperties,
								ConfigurableApplicationContext applicationContext,
								@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
								ObjectProvider<FlowBuilderServices> flowBuilderServices) {
		this.casProperties = casProperties;
		this.captchaProperties = captchaProperties;
		this.applicationContext = applicationContext;
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
	}

	@Bean(name = "captchaWebflowConfigurer")
	@ConditionalOnMissingBean(name = "captchaWebflowConfigurer")
	@DependsOn("defaultWebflowConfigurer")
	public CasWebflowConfigurer captchaWebflowConfigurer() {
		return new CasCaptchaWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry,
				applicationContext, casProperties);
	}

	@Bean(name = CaptchaConstants.INITIALIZE_CAPTCHA_ACTION_BEAN_NAME)
	@ConditionalOnMissingBean(name = CaptchaConstants.INITIALIZE_CAPTCHA_ACTION_BEAN_NAME)
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public Action initializeCaptchaAction() {
		return new InitializeCaptchaAction(captchaProperties);
	}

	@Bean(name = CaptchaConstants.VALIDATE_CAPTCHA_ACTION_BEAN_NAME)
	@ConditionalOnMissingBean(name = CaptchaConstants.VALIDATE_CAPTCHA_ACTION_BEAN_NAME)
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public Action validateCaptchaAction(ServletCaptchaValidator captchaValidator) {
		return new ValidateCaptchaAction(captchaProperties, captchaValidator);
	}

	@Bean
	@ConditionalOnMissingBean(name = "captchaCasWebflowExecutionPlanConfigurer")
	public CasWebflowExecutionPlanConfigurer captchaCasWebflowExecutionPlanConfigurer() {
		return (plan)->plan.registerWebflowConfigurer(captchaWebflowConfigurer());
	}

}
