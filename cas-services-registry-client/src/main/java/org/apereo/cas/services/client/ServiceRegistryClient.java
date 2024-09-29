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
package org.apereo.cas.services.client;

import org.apereo.cas.services.client.exception.ServiceRegistryClientException;
import org.apereo.cas.services.RegisteredService;

import java.util.List;

/**
 * Service 注册客户端
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public interface ServiceRegistryClient {

	/**
	 * 注册 Service
	 *
	 * @param service
	 * 		Service
	 *
	 * @return Service
	 *
	 * @throws ServiceRegistryClientException
	 *        {@link ServiceRegistryClient} 异常
	 */
	RegisteredService save(final RegisteredService service) throws ServiceRegistryClientException;

	/**
	 * 获取 Service 列表
	 *
	 * @return Service 列表
	 *
	 * @throws ServiceRegistryClientException
	 *        {@link ServiceRegistryClient} 异常
	 */
	List<RegisteredService> list() throws ServiceRegistryClientException;

	/**
	 * 获取 Service
	 *
	 * @param id
	 * 		Service Id
	 *
	 * @return Service
	 *
	 * @throws ServiceRegistryClientException
	 *        {@link ServiceRegistryClient} 异常
	 */
	RegisteredService get(final int id) throws ServiceRegistryClientException;

	/**
	 * 删除 Service
	 *
	 * @param id
	 * 		Service Id
	 *
	 * @throws ServiceRegistryClientException
	 *        {@link ServiceRegistryClient} 异常
	 */
	void delete(final int id) throws ServiceRegistryClientException;

}
