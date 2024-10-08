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

import com.buession.core.builder.ListBuilder;
import com.buession.geoip.Resolver;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.mgt.DefaultLogManager;
import com.buession.logging.core.request.RequestContext;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import com.buession.logging.support.spring.LogHandlerFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.LoggingManager;
import org.apereo.cas.logging.manager.DefaultLoggingManager;
import org.apereo.cas.web.flow.CasLoggingWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.apereo.cas.web.flow.CasWebflowExecutionPlanConfigurer;
import org.apereo.cas.web.flow.action.LoggingAction;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.execution.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingConfiguration {

	private final CasConfigurationProperties casProperties;

	private final LoggingProperties loggingProperties;

	private final ConfigurableApplicationContext applicationContext;

	private final FlowDefinitionRegistry loginFlowDefinitionRegistry;

	private final FlowBuilderServices flowBuilderServices;

	public LoggingConfiguration(CasConfigurationProperties casProperties, LoggingProperties loggingProperties,
								ConfigurableApplicationContext applicationContext,
								@Qualifier("loginFlowRegistry") ObjectProvider<FlowDefinitionRegistry> loginFlowDefinitionRegistry,
								ObjectProvider<FlowBuilderServices> flowBuilderServices) {
		this.casProperties = casProperties;
		this.loggingProperties = loggingProperties;
		this.applicationContext = applicationContext;
		this.loginFlowDefinitionRegistry = loginFlowDefinitionRegistry.getIfAvailable();
		this.flowBuilderServices = flowBuilderServices.getIfAvailable();
	}

	@Bean(name = "loggingWebflowConfigurer")
	@ConditionalOnMissingBean(name = "loggingWebflowConfigurer")
	@DependsOn("defaultWebflowConfigurer")
	public CasWebflowConfigurer loggingWebflowConfigurer() {
		return new CasLoggingWebflowConfigurer(flowBuilderServices, loginFlowDefinitionRegistry,
				applicationContext, casProperties);
	}

	@Bean(name = LoggingAction.NAME)
	@ConditionalOnMissingBean(name = LoggingAction.NAME)
	@ConditionalOnBean(name = "loggingManagers")
	public Action loggingAction(List<LoggingManager> loggingManagers) {
		return new LoggingAction(loggingProperties.getBusinessType(), loggingProperties.getEvent(),
				loggingProperties.getDescription(), loggingProperties.getIdFieldName(),
				loggingProperties.getUsernameFieldName(), loggingProperties.getRealNameFieldName(), loggingManagers);
	}

	@Bean
	@ConditionalOnMissingBean(name = "loggingCasWebflowExecutionPlanConfigurer")
	public CasWebflowExecutionPlanConfigurer loggingCasWebflowExecutionPlanConfigurer() {
		return (plan)->plan.registerWebflowConfigurer(loggingWebflowConfigurer());
	}

	@AutoConfiguration
	@ConditionalOnBean({LogHandlerConfiguration.class})
	@AutoConfigureAfter({LogHandlerConfiguration.Console.class, LogHandlerConfiguration.Elasticsearch.class,
			LogHandlerConfiguration.File.class, LogHandlerConfiguration.Jdbc.class,
			LogHandlerConfiguration.Kafka.class, LogHandlerConfiguration.Mongo.class,
			LogHandlerConfiguration.Rabbit.class, LogHandlerConfiguration.Rest.class})
	static class LoggingManagerConfiguration {

		@Bean(name = "logHandlers")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<LogHandler> logHandlers(
				@Qualifier("consoleLogHandlers") List<LogHandler> consoleLogHandlers,
				@Qualifier("elasticsearchLogHandlers") List<LogHandler> elasticsearchLogHandlers,
				@Qualifier("fileLogHandlers") List<LogHandler> fileLogHandlers,
				@Qualifier("jdbcLogHandlers") List<LogHandler> jdbcLogHandlers,
				@Qualifier("kafkaLogHandlers") List<LogHandler> kafkaLogHandlers,
				@Qualifier("mongoLogHandlers") List<LogHandler> mongoLogHandlers,
				@Qualifier("rabbitLogHandlers") List<LogHandler> rabbitLogHandlers,
				@Qualifier("restLogHandlers") List<LogHandler> restLogHandlers) {
			final List<LogHandler> logHandlers = new ArrayList<>();

			logHandlers.addAll(consoleLogHandlers);
			logHandlers.addAll(elasticsearchLogHandlers);
			logHandlers.addAll(fileLogHandlers);
			logHandlers.addAll(jdbcLogHandlers);
			logHandlers.addAll(kafkaLogHandlers);
			logHandlers.addAll(mongoLogHandlers);
			logHandlers.addAll(rabbitLogHandlers);
			logHandlers.addAll(restLogHandlers);

			return logHandlers;
		}

		@Bean
		@ConditionalOnBean(name = {"logHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<LoggingManager> loggingManagers(RequestContext requestContext, PrincipalHandler<?> principalHandler,
													Resolver geoResolver,
													@Qualifier("logHandlers") List<LogHandler> logHandlers) {
			return logHandlers.stream().map((logHandler)->{
				final DefaultLogManager logManager = new DefaultLogManager();

				logManager.setRequestContext(requestContext);
				logManager.setPrincipalHandler(principalHandler);
				logManager.setGeoResolver(geoResolver);
				logManager.setLogHandler(logHandler);

				return new DefaultLoggingManager(logManager);
			}).collect(Collectors.toList());
		}

	}

}
