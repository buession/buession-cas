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

import com.buession.lang.Status;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.core.CaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 阿里云验证码验证
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class AliYunCaptchaValidator extends AbstractCaptchaValidator<AliYunCaptchaClient> {

	private final static Logger logger = LoggerFactory.getLogger(AliYunCaptchaValidator.class);

	public AliYunCaptchaValidator(final AliYunCaptchaClient aliYunCaptchaClient){
		super("Aliyun", aliYunCaptchaClient);
	}

	@Override
	public Status validate(final HttpServletRequest request) throws CaptchaException{
		return captchaClient.validate(null);
	}

}
