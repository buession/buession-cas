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
package org.apereo.cas.web.flow.autoconfigure;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.support.captcha.validator.CaptchaValidator;
import org.apereo.cas.support.captcha.autoconfigure.CaptchaConfiguration;
import org.apereo.cas.support.captcha.autoconfigure.CaptchaProperties;
import org.apereo.cas.web.flow.CasCaptchaWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.action.InitializeCaptchaAction;
import org.apereo.cas.web.flow.action.ValidateCaptchaAction;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

/**
 * @author Yong.Teng
 * @since 1.2.0
 */
@Configuration
@EnableConfigurationProperties({CasConfigurationProperties.class, CaptchaProperties.class})
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enable", havingValue = "true")
@Import({CasWebflowContextConfiguration.class, CaptchaConfiguration.class})
@AutoConfigureAfter({CasWebflowContextConfiguration.class})
public class CasCaptchaConfiguration implements CasWebflowExecutionPlanConfigurer {

	private final CasConfigurationProperties casProperties;

	private final CaptchaProperties captchaProperties;

	private final ConfigurableApplicationContext applicationContext;

	private final FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private final FlowBuilderServices flowBuilderServices;

	private final static Logger logger = LoggerFactory.getLogger(CasCaptchaConfiguration.class);

	public CasCaptchaConfiguration(CasConfigurationProperties casProperties, CaptchaProperties captchaProperties,
								   ObjectProvider<ConfigurableApplicationContext> applicationContext,
								   @Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
								   ObjectProvider<FlowBuilderServices> flowBuilderServices){
		this.casProperties = casProperties;
		this.captchaProperties = captchaProperties;
		this.applicationContext = applicationContext.getIfAvailable();
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
	}

	@RefreshScope
	@Bean(name = "captchaWebflowConfigurer")
	@ConditionalOnMissingBean(name = {"captchaWebflowConfigurer"})
	public CasWebflowConfigurer captchaWebflowConfigurer(){
		return new CasCaptchaWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext,
				casProperties);
	}

	@RefreshScope
	@Bean
	@ConditionalOnMissingBean(name = {CasWebflowConstants.ACTION_ID_INIT_CAPTCHA})
	public Action initializeCaptchaAction(
			@Qualifier("defaultWebflowConfigurer") CasWebflowConfigurer loginWebflowConfigurer){
		InitializeCaptchaAction action = new InitializeCaptchaAction(captchaProperties);
		Flow loginFlow = loginWebflowConfigurer.getLoginFlow();

		loginFlow.getStartActionList().add(action);
		logger.debug("Initialized InitializeCaptchaAction");

		return action;
	}

	@RefreshScope
	@Bean
	@ConditionalOnMissingBean(name = {CasWebflowConstants.ACTION_ID_VALIDATE_CAPTCHA})
	public Action validateCaptchaAction(
			@Qualifier("defaultWebflowConfigurer") CasWebflowConfigurer loginWebflowConfigurer,
			CaptchaValidator captchaValidator){
		ValidateCaptchaAction action = new ValidateCaptchaAction(captchaProperties, captchaValidator);
		Flow loginFlow = loginWebflowConfigurer.getLoginFlow();

		loginFlow.getEndActionList().add(action);

		logger.debug("Initialized ValidateCaptchaAction");

		return action;
	}

	@Override
	public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan){
		plan.registerWebflowConfigurer(captchaWebflowConfigurer());
	}

}
