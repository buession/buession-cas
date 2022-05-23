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
package org.apereo.cas.support.captcha.validator;

import com.buession.core.utils.EnumUtils;
import com.buession.core.validator.Validate;
import com.buession.lang.Status;
import com.buession.security.captcha.core.CaptchaException;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.geetest.api.v3.GeetestV3RequestData;
import com.buession.security.captcha.geetest.api.v4.GeetestV4RequestData;
import com.buession.security.captcha.geetest.core.ClientType;
import com.buession.web.servlet.http.request.RequestUtils;
import org.apereo.cas.support.captcha.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 极验验证码验证
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class GeetestCaptchaValidator extends AbstractCaptchaValidator<GeetestCaptchaClient> {

	private final static Logger logger = LoggerFactory.getLogger(GeetestCaptchaValidator.class);

	public GeetestCaptchaValidator(final GeetestCaptchaClient geetestCaptchaClient){
		super("Geetest", geetestCaptchaClient);
	}

	@Override
	public Status validate(final HttpServletRequest request) throws CaptchaException{
		if("v3".equals(captchaClient.getVersion())){
			GeetestV3RequestData requestData = new GeetestV3RequestData();

			requestData.setChallenge(request.getParameter(Parameter.Geetest.V3.CHALLENGE));
			requestData.setSeccode(request.getParameter(Parameter.Geetest.V3.SECCODE));
			requestData.setValidate(request.getParameter(Parameter.Geetest.V3.VALIDATE));
			requestData.setUserId(request.getParameter(Parameter.Geetest.V3.USER_ID));
			requestData.setIpAddress(RequestUtils.getClientIp(request));

			String clientType = request.getParameter(Parameter.Geetest.V3.CLIENT_TYPE);

			if(Validate.hasText(clientType)){
				requestData.setClientType(EnumUtils.getEnumIgnoreCase(ClientType.class, clientType));
			}else{
				requestData.setClientType(ClientType.WEB);
			}

			if(logger.isDebugEnabled()){
				logger.debug("Validate {} version: v3, params: {}", name, requestData);
			}

			return captchaClient.validate(requestData);
		}else if("v4".equals(captchaClient.getVersion())){
			GeetestV4RequestData requestData = new GeetestV4RequestData();

			requestData.setLotNumber(request.getParameter(Parameter.Geetest.V4.LOT_NUMBER));
			requestData.setCaptchaOutput(request.getParameter(Parameter.Geetest.V4.CAPTCHA_OUTPUT));
			requestData.setPassToken(request.getParameter(Parameter.Geetest.V4.PASS_TOKEN));
			requestData.setGenTime(request.getParameter(Parameter.Geetest.V4.GEN_TIME));

			if(logger.isDebugEnabled()){
				logger.debug("Validate {} version: v4, params: {}", name, requestData);
			}

			return captchaClient.validate(requestData);
		}

		return Status.FAILURE;
	}

}
