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

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.lang.Status;
import com.buession.security.captcha.core.CaptchaException;
import com.buession.security.captcha.core.CaptchaValidateFailureException;
import com.buession.security.captcha.core.RequiredParameterCaptchaException;
import com.buession.security.captcha.validator.servlet.ServletCaptchaValidator;
import org.apereo.cas.captcha.CaptchaConstants;
import org.apereo.cas.configuration.model.support.captcha.AliyunCaptchaProperties;
import org.apereo.cas.configuration.model.support.captcha.CaptchaProperties;
import org.apereo.cas.configuration.model.support.captcha.GeetestCaptchaProperties;
import org.apereo.cas.configuration.model.support.captcha.TencentCaptchaProperties;
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
 * 验证码验证 Action
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class ValidateCaptchaAction extends AbstractAction {

	/**
	 * Action 名称
	 *
	 * @since 2.1.0
	 */
	public final static String NAME = CasWebflowConstants.ACTION_ID_VALIDATE_CAPTCHA;

	private final CaptchaProperties captchaProperties;

	private final ServletCaptchaValidator captchaValidator;

	private final static Logger logger = LoggerFactory.getLogger(ValidateCaptchaAction.class);

	public ValidateCaptchaAction(final CaptchaProperties captchaProperties,
								 final ServletCaptchaValidator captchaValidator) {
		Assert.isNull(captchaValidator, "Captcha validator cloud not be null.");
		this.captchaProperties = captchaProperties;
		this.captchaValidator = captchaValidator;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext) {
		if(Boolean.FALSE.equals(requestContext.getFlowScope().get(CaptchaConstants.ENABLE_CAPTCHA))){
			logger.info("Captcha is not enable.");
			return null;
		}

		if(logger.isDebugEnabled()){
			AliyunCaptchaProperties aliyun = captchaProperties.getAliyun();
			GeetestCaptchaProperties geetest = captchaProperties.getGeetest();
			TencentCaptchaProperties tencent = captchaProperties.getTencent();

			if(aliyun != null){
				logger.debug("Aliyun captcha validate.");
			}else if(geetest != null){
				logger.debug("Geetest captcha validate.");
			}else if(tencent != null){
				logger.debug("Tencent captcha validate.");
			}
		}

		HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);

		try{
			if(captchaValidator.validate(request) == Status.SUCCESS){
				logger.debug("Captcha has successfully validated the request");
				return null;
			}else{
				logger.warn("Captcha validate failed.");
			}
		}catch(CaptchaException e){
			Event event = getError(requestContext, e);

			if(event != null){
				return event;
			}
		}

		return getError(requestContext, CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR,
				CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR, CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR);
	}

	private Event getError(final RequestContext requestContext, final CaptchaException ex) {
		if(ex instanceof RequiredParameterCaptchaException){
			if(logger.isWarnEnabled()){
				logger.warn("Captcha parameter: {} is null or empty.",
						((RequiredParameterCaptchaException) ex).getParameter());
			}

			return getError(requestContext, CaptchaConstants.CAPTCHA_REQUIRED_EVENT,
					CaptchaConstants.CAPTCHA_REQUIRED_MESSAGE_CODE, CaptchaConstants.CAPTCHA_REQUIRED_MESSAGE_CODE);
		}else if(ex instanceof CaptchaValidateFailureException){
			CaptchaValidateFailureException captchaValidateFailureException = (CaptchaValidateFailureException) ex;

			if(logger.isWarnEnabled()){
				logger.warn("Captcha validate: {}(code: {}).", captchaValidateFailureException.getMessage(),
						captchaValidateFailureException.getCode());
			}

			String messageCode = Validate.hasText(
					captchaValidateFailureException.getMessage()) ? captchaValidateFailureException.getMessage() : CasWebflowConstants.TRANSITION_ID_CAPTCHA_ERROR;
			String defaultText = Validate.hasText(
					captchaValidateFailureException.getText()) ? captchaValidateFailureException.getText() : messageCode;
			return getError(requestContext, CaptchaConstants.CAPTCHA_REQUIRED_EVENT, messageCode, defaultText);
		}else{
			if(logger.isWarnEnabled()){
				logger.warn("Captcha validate failed: {}", ex.getMessage());
			}
		}

		return null;
	}

	private Event getError(final RequestContext requestContext, final String eventId, final String messageCode,
						   final String defaultText) {
		final MessageResolver messageResolver =
				new MessageBuilder().error().code(messageCode).defaultText(defaultText).build();
		requestContext.getMessageContext().addMessage(messageResolver);

		return getEventFactorySupport().event(this, eventId);
	}

}
