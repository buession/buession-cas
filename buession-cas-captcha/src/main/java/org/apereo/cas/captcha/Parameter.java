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
package org.apereo.cas.captcha;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public interface Parameter {

	interface AliYun {

	}

	interface Geetest {

		interface V3 {

			String CHALLENGE = "geetest_challenge";

			String SECCODE = "geetest_seccode";

			String VALIDATE = "geetest_validate";

			String USER_ID = "user_id";

			String CLIENT_TYPE = "client_type";

		}

		interface V4 {

			String LOT_NUMBER = "lot_number";

			String CAPTCHA_OUTPUT = "captcha_output";

			String PASS_TOKEN = "pass_token";

			String GEN_TIME = "gen_time";

		}

	}

	interface NetEase {

	}

	interface Tencent {

		String RAND_STR = "Randstr";

		String TICKET = "Ticket";

		String CAPTCHA_TYPE = "CaptchaType";

		String BUSINESS_ID = "BusinessId";

		String SCENE_ID = "SceneId";

		String MAC_ADDRESS = "MacAddress";

		String IMEI = "Imei";

		String NEED_GET_CAPTCHA_TIME = "NeedGetCaptchaTime";

	}

}
