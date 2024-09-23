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
package org.apereo.cas.web.flow.action;

import com.buession.lang.Constants;
import com.buession.logging.core.LogData;
import com.buession.logging.core.Principal;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.logging.LoggingManager;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 日志 {@link Action}
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class LoggingAction extends AbstractAction {

	/**
	 * Action 名称
	 */
	public final static String NAME = "loginLoggingAction";

	/**
	 * {@link com.buession.logging.core.BusinessType} 值
	 */
	private final String businessType;

	/**
	 * {@link com.buession.logging.core.Event} 值
	 */
	private final String event;

	/**
	 * 描述
	 */
	private final String description;

	/**
	 * 日志管理器
	 */
	private final List<LoggingManager> loggingManagers;

	/**
	 * 构造函数
	 *
	 * @param businessType
	 *        {@link com.buession.logging.core.BusinessType}
	 * @param event
	 *        {@link com.buession.logging.core.Event}
	 * @param description
	 * 		描述
	 * @param loggingManagers
	 * 		基本日志管理器
	 */
	public LoggingAction(final String businessType, final String event,
						 final String description, final List<LoggingManager> loggingManagers) {
		this.businessType = businessType;
		this.event = event;
		this.description = Optional.ofNullable(description).orElse(Constants.EMPTY_STRING);
		this.loggingManagers = loggingManagers;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext) {
		final AuthenticationResult authenticationResult = WebUtils.getAuthenticationResult(requestContext);
		final Authentication authentication = authenticationResult.getAuthentication();
		final org.apereo.cas.authentication.principal.Principal principal = authentication.getPrincipal();
		final String uid = principal.getId();
		final Map<String, List<Object>> uAttributes = principal.getAttributes();
		final Credential credential = WebUtils.getCredential(requestContext);

		final LogData loginData = new LogData();

		final Principal logPrincipal = new Principal();

		logPrincipal.setId(uid);
		logPrincipal.setUserName(credential.getId());

		final Map<String, Object> extra = new HashMap<>(uAttributes);

		loginData.setBusinessType(businessType);
		loginData.setEvent(event);
		loginData.setPrincipal(logPrincipal);
		loginData.setDescription(description);
		loginData.setExtra(extra);

		loggingManagers.forEach((loggingManager)->{
			loggingManager.execute(loginData);
		});

		return success();
	}

}
