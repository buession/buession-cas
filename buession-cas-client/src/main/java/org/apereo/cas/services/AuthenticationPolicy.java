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

import java.util.Set;

/**
 * This is {@link AuthenticationPolicy}.
 * The authentication policy can be assigned to a service definition to indicate how CAS should respond to
 * authentication requests when processing the assigned service.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class AuthenticationPolicy implements Entity {

	private final static long serialVersionUID = 2233505170020503518L;

	/**
	 * This is {@link DefaultRegisteredServiceAuthenticationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class DefaultRegisteredServiceAuthenticationPolicy extends AuthenticationPolicy {

		private final static long serialVersionUID = 3961058262783916248L;

		/**
		 * The required authentication handlers.
		 */
		private Set<String> requiredAuthenticationHandlers;

		/**
		 * The excluded authentication handlers.
		 */
		private Set<String> excludedAuthenticationHandlers;

		/**
		 * The required authentication policies.
		 */
		private AuthenticationPolicyCriteria criteria;

		/**
		 * Return required authentication handlers by their name/id.
		 *
		 * @return The required authentication handlers.
		 */
		public Set<String> getRequiredAuthenticationHandlers(){
			return requiredAuthenticationHandlers;
		}

		/**
		 * Sets required authentication handlers by their name/id.
		 *
		 * @param requiredAuthenticationHandlers
		 * 		The required authentication handlers.
		 */
		public void setRequiredAuthenticationHandlers(Set<String> requiredAuthenticationHandlers){
			this.requiredAuthenticationHandlers = requiredAuthenticationHandlers;
		}

		/**
		 * Return excluded authentication handlers by their name/id.
		 *
		 * @return The excluded authentication handlers.
		 */
		public Set<String> getExcludedAuthenticationHandlers(){
			return excludedAuthenticationHandlers;
		}

		/**
		 * Sets excluded authentication handlers by their name/id.
		 *
		 * @param excludedAuthenticationHandlers
		 * 		The excluded authentication handlers.
		 */
		public void setExcludedAuthenticationHandlers(Set<String> excludedAuthenticationHandlers){
			this.excludedAuthenticationHandlers = excludedAuthenticationHandlers;
		}

		/**
		 * Return required authentication policy criteria.
		 *
		 * @return The required authentication policies.
		 */
		public AuthenticationPolicyCriteria getCriteria(){
			return criteria;
		}

		/**
		 * Sets required authentication policy criteria.
		 *
		 * @param criteria
		 * 		The required authentication policies.
		 */
		public void setCriteria(AuthenticationPolicyCriteria criteria){
			this.criteria = criteria;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.add("requiredAuthenticationHandlers", requiredAuthenticationHandlers)
					.add("excludedAuthenticationHandlers", excludedAuthenticationHandlers)
					.add("criteria", criteria)
					.asString();
		}

		/**
		 * This is {@link AuthenticationPolicyCriteria}.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
		@JsonInclude(JsonInclude.Include.NON_DEFAULT)
		public abstract static class AuthenticationPolicyCriteria implements Entity {

			private final static long serialVersionUID = 2454096801636097365L;

			/**
			 * This is {@link AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class AllAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = -8830723344550579717L;

				@Override
				public String toString(){
					return super.toString();
				}

			}

			/**
			 * This is {@link AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class AllowedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = 1092618504391502358L;

				@Override
				public String toString(){
					return super.toString();
				}

			}

			/**
			 * This is {@link AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class AnyAuthenticationHandlerRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = 8092498885827119544L;

				private boolean tryAll;

				public boolean isTryAll(){
					return tryAll;
				}

				public void setTryAll(boolean tryAll){
					this.tryAll = tryAll;
				}

				@Override
				public String toString(){
					return StringBuilder.getInstance(this)
							.of("tryAll", tryAll);
				}

			}

			/**
			 * This is {@link ExcludedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class ExcludedAuthenticationHandlersRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = 7290507501286809087L;

				@Override
				public String toString(){
					return super.toString();
				}

			}

			/**
			 * This is {@link GroovyRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class GroovyRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = 7941282852364954804L;

				private String script;

				public String getScript(){
					return script;
				}

				public void setScript(String script){
					this.script = script;
				}

				@Override
				public String toString(){
					return StringBuilder.getInstance(this)
							.of("script", script);
				}

			}

			/**
			 * This is {@link NotPreventedRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class NotPreventedRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = 8849398585580233465L;

				@Override
				public String toString(){
					return super.toString();
				}

			}

			/**
			 * This is {@link RestfulRegisteredServiceAuthenticationPolicyCriteria}.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class RestfulRegisteredServiceAuthenticationPolicyCriteria
					extends AuthenticationPolicyCriteria {

				private final static long serialVersionUID = -5384026225871407864L;

				private String url;

				private String basicAuthUsername;

				private String basicAuthPassword;

				public String getUrl(){
					return this.url;
				}

				public void setUrl(String url){
					this.url = url;
				}

				public String getBasicAuthUsername(){
					return this.basicAuthUsername;
				}

				public void setBasicAuthUsername(String basicAuthUsername){
					this.basicAuthUsername = basicAuthUsername;
				}

				public String getBasicAuthPassword(){
					return this.basicAuthPassword;
				}

				public void setBasicAuthPassword(String basicAuthPassword){
					this.basicAuthPassword = basicAuthPassword;
				}

				@Override
				public String toString(){
					return StringBuilder.getInstance(this)
							.add("url", url)
							.add("basicAuthUsername", basicAuthUsername)
							.add("basicAuthPassword", basicAuthPassword)
							.asString();
				}

			}

		}
	}

}
