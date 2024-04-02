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
package org.apereo.cas.logging.web.flow.autoconfigure;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.core.CasCoreConfigurationProperties;
import org.apereo.cas.logging.autoconfigure.BasicLoginLoggingConfiguration;
import org.apereo.cas.logging.autoconfigure.HistoryLoginLoggingConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.BasicLoginLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.apereo.cas.logging.web.flow.LoginLoggingWebflowConfigurer;
import org.apereo.cas.logging.web.flow.action.LoginLoggingAction;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.config.AbstractWebflowConfiguration;
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
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasConfigurationProperties.class, CasCoreConfigurationProperties.class,
		CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
@Import({CasWebflowContextConfiguration.class})
@AutoConfigureAfter({CasWebflowContextConfiguration.class, BasicLoginLoggingConfiguration.class,
		HistoryLoginLoggingConfiguration.class})
public class CasLoginLoggingConfiguration extends AbstractWebflowConfiguration {

	private final CasLoggingConfigurationProperties casLoggingConfigurationProperties;

	public CasLoginLoggingConfiguration(CasConfigurationProperties casProperties,
										CasLoggingConfigurationProperties casLoggingConfigurationProperties,
										ObjectProvider<ConfigurableApplicationContext> applicationContext,
										@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
										ObjectProvider<FlowBuilderServices> flowBuilderServices) {
		super(casProperties, applicationContext, loginFlowDefinitionRegistry, flowBuilderServices);
		this.casLoggingConfigurationProperties = casLoggingConfigurationProperties;
	}

	@Bean(name = "loginLoggingWebflowConfigurer")
	@ConditionalOnMissingBean(name = "loginLoggingWebflowConfigurer")
	public CasWebflowConfigurer loginLogWebflowConfigurer() {
		return new LoginLoggingWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext,
				casProperties);
	}

	@Bean(name = LoginLoggingAction.NAME)
	@ConditionalOnMissingBean(name = LoginLoggingAction.NAME)
	public Action loginLoggingAction(ObjectProvider<BasicLoginLoggingManager> basicLoginLoggingManager,
									 ObjectProvider<HistoryLoginLoggingManager> historyLoginLoggingManager) {
		return new LoginLoggingAction(casLoggingConfigurationProperties, basicLoginLoggingManager.getIfAvailable(),
				historyLoginLoggingManager.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean(name = "loggingCasWebflowExecutionPlanConfigurer")
	public CasWebflowExecutionPlanConfigurer loginLoggingCasWebflowExecutionPlanConfigurer(
			@Qualifier("loginLoggingWebflowConfigurer") CasWebflowConfigurer loginLogWebflowConfigurer) {
		return plan->plan.registerWebflowConfigurer(loginLogWebflowConfigurer);
	}

}
