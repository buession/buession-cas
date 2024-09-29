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
package org.apereo.cas.client;

import com.buession.core.builder.MapBuilder;
import com.buession.core.builder.SetBuilder;
import com.buession.core.utils.RandomUtils;
import com.buession.httpclient.ApacheHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.cas.services.CasRegisteredService;
import org.apereo.cas.services.DefaultRegisteredServiceProperty;
import org.apereo.cas.services.Protocol;
import org.apereo.cas.services.client.DefaultServiceRegistryClient;
import org.apereo.cas.services.client.ServiceRegistryClient;
import org.apereo.cas.services.client.exception.ServiceRegistryClientException;
import org.apereo.cas.services.DefaultRegisteredServiceContact;
import org.apereo.cas.services.DenyAllAttributeReleasePolicy;
import org.apereo.cas.services.LogoutType;
import org.apereo.cas.services.RefuseRegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegexMatchingRegisteredServiceProxyPolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ResponseType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 2.2.0
 */
public class ServiceRegistryClientTest {

	private final static ServiceRegistryClient client = new DefaultServiceRegistryClient(
			"http://127.0.0.1:30101/actuator/registeredServices", new ApacheHttpClient());

	@Test
	public void save() throws ServiceRegistryClientException {
		CasRegisteredService service = new CasRegisteredService();

		service.setId(RandomUtils.nextInt());
		service.setName("test_" + System.currentTimeMillis());
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

		service.setContacts(new ArrayList<>(Collections.singletonList(contact)));
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
		service.setSupportedProtocols(SetBuilder.of(Protocol.CAS30));

		service.setProperties(MapBuilder.of("email",
				new DefaultRegisteredServiceProperty(SetBuilder.of("webmaster@buession.com"))));

		ObjectMapper mapper = new ObjectMapper();
		try{
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(service));
		}catch(JsonProcessingException e){
			throw new RuntimeException(e);
		}
		//RegisteredService result = client.save(service);
		//System.out.println(result);
	}

	@Test
	public void list() throws ServiceRegistryClientException {
		List<RegisteredService> services = client.list();
		System.out.println(services);
	}

	@Test
	public void get() throws ServiceRegistryClientException {
		RegisteredService service = client.get(2);
		System.out.println(service);
	}

	@Test
	public void delete() throws ServiceRegistryClientException {
		client.delete(3);
	}

}
