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
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.manager.BasicLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoggingManager;
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
	 * {@link LoggingProperties}
	 */
	private final LoggingProperties loggingProperties;

	private final String description;

	/**
	 * 基本日志管理器
	 */
	private final BasicLoggingManager basicLoggingManager;

	/**
	 * 历史日志管理器接口
	 */
	private final HistoryLoggingManager historyLoggingManager;

	/**
	 * 构造函数
	 *
	 * @param loggingProperties
	 *        {@link LoggingProperties}
	 * @param basicLoggingManager
	 * 		基本日志管理器
	 * @param historyLoggingManager
	 * 		历史日志管理器接口
	 */
	public LoggingAction(final LoggingProperties loggingProperties, final BasicLoggingManager basicLoggingManager,
						 final HistoryLoggingManager historyLoggingManager) {
		this.loggingProperties = loggingProperties;
		this.description = Optional.ofNullable(loggingProperties.getDescription()).orElse(Constants.EMPTY_STRING);
		this.basicLoggingManager = basicLoggingManager;
		this.historyLoggingManager = historyLoggingManager;
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

		loginData.setBusinessType(loggingProperties.getBusinessType());
		loginData.setEvent(loggingProperties.getEvent());
		loginData.setPrincipal(logPrincipal);
		loginData.setDescription(description);
		loginData.setExtra(extra);

		basicLoggingManager.execute(loginData);
		historyLoggingManager.execute(loginData);

		return success();
	}

}
