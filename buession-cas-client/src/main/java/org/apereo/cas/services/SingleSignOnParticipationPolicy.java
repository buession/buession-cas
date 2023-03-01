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

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * This is {@link SingleSignOnParticipationPolicy}. This contract allows applications registered with CAS to
 * define an expiration policy for SSO sessions. For example, an application
 * may be decide to opt out of participating in SSO, if one exists,
 * if the existing SSO session is somewhat stale or idle given
 * certain timestamps. Likewise, validation events may start to issue
 * failures if the validated assertion is before/after a certain timestamp
 * or fails to pass other requirements for the specific registered service.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class SingleSignOnParticipationPolicy implements Entity {

	/**
	 * This is {@link DefaultRegisteredServiceSingleSignOnParticipationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class DefaultRegisteredServiceSingleSignOnParticipationPolicy
			extends SingleSignOnParticipationPolicy {

		private final static long serialVersionUID = 8434938077322638136L;

		/**
		 * That indicates whether to create SSO session on re-newed authentication event
		 * when dealing with this service.
		 */
		private TriStateBoolean createCookieOnRenewedAuthentication;

		/**
		 * Flag that indicates whether to create SSO session on re-newed authentication event
		 * when dealing with this service.
		 *
		 * @return true/false
		 */
		public TriStateBoolean getCreateCookieOnRenewedAuthentication(){
			return createCookieOnRenewedAuthentication;
		}

		/**
		 * Sets that indicates whether to create SSO session on re-newed authentication event
		 * when dealing with this service.
		 *
		 * @param createCookieOnRenewedAuthentication
		 * 		That indicates whether to create SSO session on re-newed authentication event
		 * 		when dealing with this service.
		 */
		public void setCreateCookieOnRenewedAuthentication(
				TriStateBoolean createCookieOnRenewedAuthentication){
			this.createCookieOnRenewedAuthentication = createCookieOnRenewedAuthentication;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.of("createCookieOnRenewedAuthentication", createCookieOnRenewedAuthentication);
		}

	}

	/**
	 * This is {@link BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public abstract static class BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy
			extends DefaultRegisteredServiceSingleSignOnParticipationPolicy {

		private final static long serialVersionUID = 6096729656112591576L;

		private TimeUnit timeUnit;

		private long timeValue;

		private int order;

		public TimeUnit getTimeUnit(){
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit){
			this.timeUnit = timeUnit;
		}

		public long getTimeValue(){
			return timeValue;
		}

		public void setTimeValue(long timeValue){
			this.timeValue = timeValue;
		}

		public int getOrder(){
			return order;
		}

		public void setOrder(int order){
			this.order = order;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("timeUnit=" + timeUnit)
					.add("timeValue=" + timeValue)
					.add("order" + order)
					.toString();
		}

		/**
		 * This is {@link AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy}.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public static class AuthenticationDateRegisteredServiceSingleSignOnParticipationPolicy
				extends BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy {

			private final static long serialVersionUID = -2234621198076472341L;

			@Override
			public String toString(){
				return super.toString();
			}

		}

		/**
		 * This is {@link LastUsedTimeRegisteredServiceSingleSignOnParticipationPolicy}.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public static class LastUsedTimeRegisteredServiceSingleSignOnParticipationPolicy
				extends BaseDateTimeRegisteredServiceSingleSignOnParticipationPolicy {

			private final static long serialVersionUID = -1700035502556676976L;

			@Override
			public String toString(){
				return super.toString();
			}

		}

	}

	/**
	 * This is {@link ChainingRegisteredServiceSingleSignOnParticipationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class ChainingRegisteredServiceSingleSignOnParticipationPolicy
			extends SingleSignOnParticipationPolicy {

		private final static long serialVersionUID = -35639220413464229L;

		private List<SingleSignOnParticipationPolicy> policies;

		public List<SingleSignOnParticipationPolicy> getPolicies(){
			return policies;
		}

		public void setPolicies(List<SingleSignOnParticipationPolicy> policies){
			this.policies = policies;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("policies=" + policies)
					.toString();
		}

	}

	/**
	 * This is {@link NeverRegisteredServiceSingleSignOnParticipationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class NeverRegisteredServiceSingleSignOnParticipationPolicy
			extends SingleSignOnParticipationPolicy {

		private final static long serialVersionUID = 1993111264216653785L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

}
