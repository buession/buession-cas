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
import org.apereo.cas.services.CaseCanonicalizationMode;
import org.apereo.cas.services.Entity;
import org.apereo.cas.services.utils.ToStringBuilder;

/**
 * Strategy interface to define what username attribute should be returned for a given registered service.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class UsernameAttributeProvider implements Entity {

	private final static long serialVersionUID = 3142832654244632132L;

	/**
	 * This is {@link BaseRegisteredServiceUsernameAttributeProvider}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public abstract static class BaseRegisteredServiceUsernameAttributeProvider extends UsernameAttributeProvider {

		private final static long serialVersionUID = -256903697505154347L;

		private CaseCanonicalizationMode canonicalizationMode;

		private boolean encryptUsername;

		public CaseCanonicalizationMode getCanonicalizationMode(){
			return canonicalizationMode;
		}

		public void setCanonicalizationMode(CaseCanonicalizationMode canonicalizationMode){
			this.canonicalizationMode = canonicalizationMode;
		}

		public boolean isEncryptUsername(){
			return encryptUsername;
		}

		public void setEncryptUsername(boolean encryptUsername){
			this.encryptUsername = encryptUsername;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("canonicalizationMode", canonicalizationMode)
					.add("encryptUsername", encryptUsername)
					.asString();
		}

	}

	/**
	 * Resolves the username for the service to be the default principal id.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class DefaultRegisteredServiceUsernameProvider
			extends BaseRegisteredServiceUsernameAttributeProvider {

		private final static long serialVersionUID = 6259003239865782296L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

	/**
	 * Generates a persistent id as username for anonymous service access.
	 * Generated ids are unique per service.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class AnonymousRegisteredServiceUsernameAttributeProvider
			extends BaseRegisteredServiceUsernameAttributeProvider {

		private final static long serialVersionUID = 5691464891465482625L;

		/**
		 * The generates a unique consistent Id based on the principal.
		 */
		private PersistentIdGenerator persistentIdGenerator;

		/**
		 * Return the enerates a unique consistent Id based on the principal.
		 *
		 * @return The generates a unique consistent Id based on the principal.
		 */
		public PersistentIdGenerator getPersistentIdGenerator(){
			return persistentIdGenerator;
		}

		/**
		 * Sets the enerates a unique consistent Id based on the principal.
		 *
		 * @param persistentIdGenerator
		 * 		The generates a unique consistent Id based on the principal.
		 */
		public void setPersistentIdGenerator(
				PersistentIdGenerator persistentIdGenerator){
			this.persistentIdGenerator = persistentIdGenerator;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("persistentIdGenerator", persistentIdGenerator)
					.asString();
		}

		/**
		 * Generates a unique consistent Id based on the principal.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
		@JsonInclude(JsonInclude.Include.NON_DEFAULT)
		public abstract static class PersistentIdGenerator implements Entity {

			private final static long serialVersionUID = 4844825769749202987L;

			/**
			 * Generates PersistentIds based on the Shibboleth algorithm.
			 * The generated ids are based on a principal attribute is specified, or
			 * the authenticated principal id.
			 *
			 * @author Yong.Teng
			 * @since 2.2.0
			 */
			public static class ShibbolethCompatiblePersistentIdGenerator extends PersistentIdGenerator {

				private final static long serialVersionUID = -8471511405431575393L;

				private String salt;

				private String attribute;

				public String getSalt(){
					return salt;
				}

				public void setSalt(String salt){
					this.salt = salt;
				}

				public String getAttribute(){
					return attribute;
				}

				public void setAttribute(String attribute){
					this.attribute = attribute;
				}

				@Override
				public String toString(){
					return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
							.add("salt", salt)
							.add("attribute", attribute)
							.asString();
				}

			}

		}

	}

	/**
	 * Resolves the username for the service to be the default principal id.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class GroovyRegisteredServiceUsernameProvider
			extends BaseRegisteredServiceUsernameAttributeProvider {

		private final static long serialVersionUID = -6305778139709554308L;

		private String groovyScript;

		public String getGroovyScript(){
			return groovyScript;
		}

		public void setGroovyScript(String groovyScript){
			this.groovyScript = groovyScript;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("groovyScript", groovyScript)
					.asString();
		}

	}

	/**
	 * Determines the username for this registered service based on a principal attribute.
	 * If the attribute is not found, default principal id is returned.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class PrincipalAttributeRegisteredServiceUsernameProvider
			extends BaseRegisteredServiceUsernameAttributeProvider {

		private final static long serialVersionUID = 3354169170543960592L;

		private String usernameAttribute;

		public String getUsernameAttribute(){
			return usernameAttribute;
		}

		public void setUsernameAttribute(String usernameAttribute){
			this.usernameAttribute = usernameAttribute;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("usernameAttribute", usernameAttribute)
					.asString();
		}

	}

	/**
	 * This is {@link ScriptedRegisteredServiceUsernameProvider}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class ScriptedRegisteredServiceUsernameProvider
			extends BaseRegisteredServiceUsernameAttributeProvider {

		private final static long serialVersionUID = -2248157523984671350L;

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
					.add("script", script)
					.asString();
		}

	}

}
