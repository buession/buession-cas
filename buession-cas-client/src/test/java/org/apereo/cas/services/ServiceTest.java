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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.services;

import com.buession.core.builder.SetBuilder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author Yong.Teng
 * @since 2.2.0
 */
public class ServiceTest {

	@Test
	public void jsonEncode() throws JsonProcessingException {
		RegexRegisteredService service = new RegexRegisteredService();

		service.setId(1);
		service.setName("test");
		service.setServiceId("http://www.baidu.com");
		service.setLogo("https://static.ws.126.net/163/f2e/www/index20170701/images/sprite_img_20210520.svg");
		service.setDescription("描述");
		service.setTheme("default");
		service.setInformationUrl("http://www.a.com/article/information.html");
		service.setPrivacyUrl("http://www.a.com/article/privacy.html");
		service.setRedirectUrl("http://www.a.com/login");
		service.setLogoutUrl("http://www.a.com/logout");

		DefaultRegisteredServiceContact contact = new DefaultRegisteredServiceContact();

		contact.setName("buession");
		contact.setEmail("webmaster@buession.com");
		contact.setPhone("13800138000");
		contact.setDepartment("研发");

		service.setContacts(Collections.singletonList(contact));
		service.setResponseType(ResponseType.REDIRECT);
		service.setLogoutType(LogoutType.BACK_CHANNEL);
		service.setEnvironments(SetBuilder.of("test"));

		service.setProxyPolicy(new RefuseRegisteredServiceProxyPolicy());
		RegexMatchingRegisteredServiceProxyPolicy regexMatchingRegisteredServiceProxyPolicy =
				new RegexMatchingRegisteredServiceProxyPolicy();
		regexMatchingRegisteredServiceProxyPolicy.setPattern("aaa");
		service.setProxyPolicy(regexMatchingRegisteredServiceProxyPolicy);

		DenyAllAttributeReleasePolicy denyAllAttributeReleasePolicy = new DenyAllAttributeReleasePolicy();
		denyAllAttributeReleasePolicy.setAuthorizedToReleaseAuthenticationAttributes(true);
		denyAllAttributeReleasePolicy.setAuthorizedToReleaseCredentialPassword(false);
		denyAllAttributeReleasePolicy.setAuthorizedToReleaseProxyGrantingTicket(false);

		service.setAttributeReleasePolicy(denyAllAttributeReleasePolicy);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
				ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
		System.out.println("");
		System.out.println(service);
		System.out.println("");
		System.out.println(objectMapper.writeValueAsString(service));
		System.out.println("");
	}

}
