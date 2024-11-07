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
package org.apereo.cas.web.flow;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.flow.action.LoggingAction;
import org.apereo.cas.web.flow.configurer.AbstractCasWebflowConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

/**
 * This is a captcha {@link CasWebflowConfigurer}.
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class CasLoggingWebflowConfigurer extends AbstractCasWebflowConfigurer {

	public CasLoggingWebflowConfigurer(final FlowBuilderServices flowBuilderServices,
									   final FlowDefinitionRegistry loginFlowDefinitionRegistry,
									   final ConfigurableApplicationContext applicationContext,
									   final CasConfigurationProperties casProperties) {
		super(flowBuilderServices, loginFlowDefinitionRegistry, applicationContext, casProperties);
	}

	@Override
	protected void doInitialize() {
		Flow flow = getLoginFlow();
		if(flow != null){
			createLoginLoggingAction(flow);
		}
	}

	private void createLoginLoggingAction(final Flow flow) {
		ActionState state = getState(flow, CasWebflowConstants.STATE_ID_CREATE_TICKET_GRANTING_TICKET,
				ActionState.class);
		state.getExitActionList().add(createEvaluateAction(LoggingAction.NAME));
	}

}
