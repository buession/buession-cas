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
package org.apereo.cas.web.flow.action;

import org.apereo.cas.core.Constants;
import org.apereo.cas.support.captcha.CaptchaConstants;
import org.apereo.cas.support.captcha.autoconfigure.CaptchaProperties;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Yong.Teng
 * @since 1.2.0
 */
public class InitializeCaptchaAction extends AbstractAction {

	private final CaptchaProperties captchaProperties;

	public InitializeCaptchaAction(final CaptchaProperties captchaProperties){
		super();
		this.captchaProperties = captchaProperties;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext){
		configureWebflowParameters(requestContext);
		return success();
	}

	private void configureWebflowParameters(final RequestContext requestContext){
		boolean enable = captchaProperties.isEnable();

		if(enable){
			if(captchaProperties.getMaxPasswordFailure() > 0){
				HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
				HttpSession session = request.getSession();

				enable = false;

				if(session != null){
					Object value = session.getAttribute(Constants.PASSWORD_FAILURE_TIMES_SESSION_KEY);

					if(value != null){
						if(value instanceof Short || value instanceof Integer || value instanceof Long){
							enable = (Integer) value > captchaProperties.getMaxPasswordFailure();
						}
					}
				}
			}
		}

		if(enable){
			applyVariables(requestContext);
		}

		requestContext.getFlowScope().put(CaptchaConstants.ENABLE_CAPTCHA, enable);
	}

	private void applyVariables(final RequestContext requestContext){
		MutableAttributeMap<Object> flowScope = requestContext.getFlowScope();
		CaptchaProperties.Geetest geetest = captchaProperties.getGeetest();

		if(geetest != null){
			flowScope.put("captchaAccessKeyId", geetest.getGeetestId());
			flowScope.put("captchaVersion", geetest.getVersion());
			flowScope.put("captchaJavaScriptUrl", geetest.getJavascript());
		}
	}

}
