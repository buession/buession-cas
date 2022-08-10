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
package org.apereo.cas.support.loginlog.flow.autoconfigure;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.core.CasCoreConfigurationProperties;
import org.apereo.cas.support.CasSupportConfigurationProperties;
import org.apereo.cas.support.loginlog.flow.LoginLogWebflowConfigurer;
import org.apereo.cas.support.loginlog.flow.action.LoginLogAction;
import org.apereo.cas.support.loginlog.manager.ConsoleLoginLogManager;
import org.apereo.cas.support.loginlog.manager.LoginLogManager;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.config.CasWebflowContextConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

/**
 * @author Yong.Teng
 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasConfigurationProperties.class, CasCoreConfigurationProperties.class,
		CasSupportConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasSupportConfigurationProperties.PREFIX, name = "login-log.enabled", havingValue = "true")
@Import({CasWebflowContextConfiguration.class})
@AutoConfigureAfter({CasWebflowContextConfiguration.class})
public class CasLoginLogConfiguration {

	private CasConfigurationProperties casProperties;

	private CasCoreConfigurationProperties casCoreConfigurationProperties;

	private final ConfigurableApplicationContext applicationContext;

	private final FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private final FlowBuilderServices flowBuilderServices;

	public CasLoginLogConfiguration(CasConfigurationProperties casProperties,
									CasCoreConfigurationProperties casCoreConfigurationProperties,
									ObjectProvider<ConfigurableApplicationContext> applicationContext,
									@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
									ObjectProvider<FlowBuilderServices> flowBuilderServices){
		this.casProperties = casProperties;
		this.casCoreConfigurationProperties = casCoreConfigurationProperties;
		this.applicationContext = applicationContext.getIfAvailable();
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
	}

	@Bean(name = "loginLogWebflowConfigurer")
	@ConditionalOnMissingBean(name = "loginLogWebflowConfigurer")
	public CasWebflowConfigurer loginLogWebflowConfigurer(){
		return new LoginLogWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext,
				casProperties);
	}

	@Bean(name = "loginLogManager")
	@ConditionalOnMissingBean(name = "loginLogManager")
	public LoginLogManager loginLogManager(){
		return new ConsoleLoginLogManager();
	}

	@Bean
	@ConditionalOnMissingBean(name = LoginLogAction.NAME)
	public Action loginLogAction(ObjectProvider<LoginLogManager> loginLogManager){
		return new LoginLogAction(loginLogManager.getIfAvailable(),
				casCoreConfigurationProperties.getClientRealIpHeaderName());
	}

	@Bean
	@ConditionalOnMissingBean(name = "loginLogCasWebflowExecutionPlanConfigurer")
	public CasWebflowExecutionPlanConfigurer loginLogCasWebflowExecutionPlanConfigurer(
			@Qualifier("loginLogWebflowConfigurer") CasWebflowConfigurer loginLogWebflowConfigurer){
		return plan->plan.registerWebflowConfigurer(loginLogWebflowConfigurer);
	}

}
