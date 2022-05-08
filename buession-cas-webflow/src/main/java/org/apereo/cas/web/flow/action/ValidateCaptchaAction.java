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
package org.apereo.cas.web.flow.action;

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.lang.Status;
import org.apereo.cas.support.captcha.CaptchaConstants;
import org.apereo.cas.support.captcha.CaptchaValidator;
import org.apereo.cas.support.captcha.autoconfigure.CaptchaProperties;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.web.flow.CasWebflowConstants;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageResolver;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yong.Teng
 * @since 1.2.0
 */
public class ValidateCaptchaAction extends AbstractAction {

	private final CaptchaProperties captchaProperties;

	private final CaptchaValidator captchaValidator;

	private final static Logger logger = LoggerFactory.getLogger(ValidateCaptchaAction.class);

	public ValidateCaptchaAction(final CaptchaProperties captchaProperties, final CaptchaValidator captchaValidator){
		Assert.isNull(captchaValidator, "Captcha validator cloud not be null.");
		this.captchaProperties = captchaProperties;
		this.captchaValidator = captchaValidator;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext){
		if(captchaProperties.isEnable() == false ||
				Boolean.FALSE.equals(requestContext.getFlowScope().get(CaptchaConstants.ENABLE_CAPTCHA))){
			logger.info("Captcha is not enable.");
			return null;
		}

		HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
		String code = request.getParameter(CaptchaConstants.CAPTCHA_PARAMETER_NAME);

		if(Validate.hasText(code)){
			String userAgent = HttpRequestUtils.getHttpServletRequestUserAgent(request);

			if(captchaValidator.validate(code, userAgent) == Status.SUCCESS){
				logger.debug("Captcha has successfully validated the request");
				return null;
			}else{
				logger.warn("Captcha validate failed.");
				return getError(requestContext, CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR,
						CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR);
			}
		}else{
			logger.warn("Captcha parameter is null or empty.");
			return getError(requestContext, CaptchaConstants.CAPTCHA_REQUIRED_EVENT,
					CaptchaConstants.CAPTCHA_REQUIRED_MESSAGE_CODE);
		}
	}

	private Event getError(final RequestContext requestContext, final String eventId, final String messageCode){
		MessageResolver messageResolver =
				new MessageBuilder().error().code(messageCode).defaultText(messageCode).build();
		requestContext.getMessageContext().addMessage(messageResolver);
		return result(eventId);
	}

}
