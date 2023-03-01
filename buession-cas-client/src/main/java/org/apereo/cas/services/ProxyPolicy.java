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
package org.apereo.cas.services.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.services.Entity;
import org.apereo.cas.services.utils.ToStringBuilder;

/**
 * Defines the proxying policy for a registered service.
 * While a service may be allowed proxying on a general level,
 * it may still want to restrict who is authorizes to receive
 * the proxy granting ticket. This interface defines the behavior
 * for both options.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class ProxyPolicy implements Entity {

	private final static long serialVersionUID = -4257776404506361349L;

	/**
	 * A proxy policy that disallows proxying.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class RefuseRegisteredServiceProxyPolicy extends ProxyPolicy {

		private final static long serialVersionUID = 3965612694117621168L;

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this).asString();
		}

	}

	/**
	 * A proxy policy that only allows proxying to pgt urls that match the specified regex pattern.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class RegexMatchingRegisteredServiceProxyPolicy extends ProxyPolicy {

		private final static long serialVersionUID = -6511849385537022958L;

		private String pattern;

		public String getPattern(){
			return pattern;
		}

		public void setPattern(String pattern){
			this.pattern = pattern;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("pattern", pattern)
					.asString();
		}

	}

}
