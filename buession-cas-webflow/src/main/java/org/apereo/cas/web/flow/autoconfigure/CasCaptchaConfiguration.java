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
package org.apereo.cas.web.flow.autoconfigure;

import com.buession.security.captcha.handler.Handler;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.support.captcha.CaptchaValidator;
import org.apereo.cas.support.captcha.DefaultCaptchaValidator;
import org.apereo.cas.support.captcha.autoconfigure.CaptchaProperties;
import org.apereo.cas.web.flow.CasCaptchaWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.flow.CasWebflowExecutionPlan;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.action.InitializeCaptchaAction;
import org.apereo.cas.web.flow.action.ValidateCaptchaAction;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

/**
 * @author Yong.Teng
 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasConfigurationProperties.class, CaptchaProperties.class})
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enable", havingValue = "true")
public class CasCaptchaConfiguration implements CasWebflowExecutionPlanConfigurer {

	@Autowired
	private CasConfigurationProperties casProperties;

	@Autowired
	private CaptchaProperties captchaProperties;

	@Autowired
	private ConfigurableApplicationContext applicationContext;

	private FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private FlowBuilderServices flowBuilderServices;

	private CasCaptchaWebflowConfigurer casCaptchaWebflowConfigurer;

	public CasCaptchaConfiguration(@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry, ObjectProvider<FlowBuilderServices> flowBuilderServices, ObjectProvider<CasCaptchaWebflowConfigurer> casCaptchaWebflowConfigurer){
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
		this.casCaptchaWebflowConfigurer = casCaptchaWebflowConfigurer.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean(name = {"captchaWebflowConfigurer"})
	@DependsOn({"defaultWebflowConfigurer"})
	public CasWebflowConfigurer captchaWebflowConfigurer(){
		return new CasCaptchaWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext,
				casProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = {"captchaValidator"})
	public CaptchaValidator captchaValidator(Handler handler){
		return new DefaultCaptchaValidator(handler);
	}

	@RefreshScope
	@Bean
	@ConditionalOnMissingBean(name = {CasWebflowConstants.ACTION_ID_INIT_CAPTCHA})
	public Action initializeCaptchaAction(){
		return new InitializeCaptchaAction(captchaProperties);
	}

	@RefreshScope
	@Bean
	@ConditionalOnMissingBean(name = {CasWebflowConstants.ACTION_ID_VALIDATE_CAPTCHA})
	public Action validateCaptchaAction(CaptchaValidator captchaValidator){
		return new ValidateCaptchaAction(captchaProperties, captchaValidator);
	}

	@Override
	public void configureWebflowExecutionPlan(final CasWebflowExecutionPlan plan){
		plan.registerWebflowConfigurer(casCaptchaWebflowConfigurer);
	}

}
