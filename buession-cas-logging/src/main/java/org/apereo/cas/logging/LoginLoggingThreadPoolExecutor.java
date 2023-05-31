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
package org.apereo.cas.logging;

import com.buession.core.concurrent.ThreadPoolConfiguration;
import com.buession.core.converter.mapper.PropertyMapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class LoginLoggingThreadPoolExecutor extends ThreadPoolExecutor {

	public LoginLoggingThreadPoolExecutor(final ThreadPoolConfiguration threadPoolConfiguration) {
		super(threadPoolConfiguration.getCorePoolSize(), threadPoolConfiguration.getMaximumPoolSize(),
				threadPoolConfiguration.getKeepAliveTime(), threadPoolConfiguration.getTimeUnit(),
				threadPoolConfiguration.getWorkQueue() == null ? new LinkedBlockingQueue<>() :
						threadPoolConfiguration.getWorkQueue(),
				threadPoolConfiguration.getThreadFactory() == null ? new DefaultThreadFactory("Login-Logging",
						threadPoolConfiguration.getDaemon(), threadPoolConfiguration.getPriority()) :
						threadPoolConfiguration.getThreadFactory());

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(threadPoolConfiguration.getRejectedHandler()).to(this::setRejectedExecutionHandler);
		propertyMapper.from(threadPoolConfiguration.getAllowCoreThreadTimeOut()).to(this::allowCoreThreadTimeOut);
	}

	private final static class DefaultThreadFactory implements ThreadFactory {

		private final String name;

		private final Boolean daemon;

		private final Integer priority;

		public DefaultThreadFactory(final String name, final Boolean daemon, final Integer priority) {
			this.name = name;
			this.daemon = daemon;
			this.priority = priority;
		}

		@Override
		public Thread newThread(@NotNull Runnable r) {
			final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
			final Thread thread = new Thread(r, name);

			propertyMapper.from(daemon).to(thread::setDaemon);
			propertyMapper.from(priority).to(thread::setPriority);

			return thread;
		}

	}

}
