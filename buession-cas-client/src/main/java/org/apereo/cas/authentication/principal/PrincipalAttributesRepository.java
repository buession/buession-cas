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
package org.apereo.cas.authentication.principal;

import org.apereo.cas.entity.Entity;
import org.apereo.cas.services.AttributeMergingStrategy;

import java.util.Set;

/**
 * Defines operations required for retrieving principal attributes.
 * Acts as a proxy between the external attribute source and CAS,
 * executing such as additional processing or caching on the set
 * of retrieved attributes. Implementations may simply decide to
 * do nothing on the set of attributes that the principal carries
 * or they may attempt to refresh them from the source, etc.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public interface PrincipalAttributesRepository extends Entity {

	/**
	 * Parent class for retrieval principals attributes, provides operations
	 * around caching, merging of attributes.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	abstract class AbstractPrincipalAttributesRepository implements PrincipalAttributesRepository {

		private final static long serialVersionUID = -919901110966922094L;

		/**
		 * The merging strategy that deals with existing principal attributes
		 * and those that are retrieved from the source. By default, existing attributes
		 * are ignored and the source is always consulted.
		 */
		private AttributeMergingStrategy mergingStrategy = AttributeMergingStrategy.MULTIVALUED;

		/**
		 * The attribute repository ids.
		 */
		private Set<String> attributeRepositoryIds;

		private boolean ignoreResolvedAttributes;

		/**
		 * Return the merging strategy that deals with existing principal attributes
		 * and those that are retrieved from the source.
		 *
		 * @return The merging strategy that deals with existing principal attributes
		 * and those that are retrieved from the source.
		 */
		public AttributeMergingStrategy getMergingStrategy(){
			return this.mergingStrategy;
		}

		/**
		 * Sets the merging strategy that deals with existing principal attributes
		 * and those that are retrieved from the source.
		 *
		 * @param mergingStrategy
		 * 		The merging strategy that deals with existing principal attributes and those that are retrieved from the source.
		 */
		public void setMergingStrategy(final AttributeMergingStrategy mergingStrategy){
			this.mergingStrategy = mergingStrategy;
		}

		/**
		 * Return attribute repository ids that should be used to fetch attributes.
		 * An empty collection indicates that all sources available and defined should be used.
		 *
		 * @return The attribute repository ids.
		 */
		public Set<String> getAttributeRepositoryIds(){
			return this.attributeRepositoryIds;
		}

		/**
		 * Sets attribute repository ids that should be used to fetch attributes.
		 *
		 * @param attributeRepositoryIds
		 * 		The attribute repository ids.
		 */
		public void setAttributeRepositoryIds(final Set<String> attributeRepositoryIds){
			this.attributeRepositoryIds = attributeRepositoryIds;
		}

		public boolean isIgnoreResolvedAttributes(){
			return this.ignoreResolvedAttributes;
		}

		public void setIgnoreResolvedAttributes(final boolean ignoreResolvedAttributes){
			this.ignoreResolvedAttributes = ignoreResolvedAttributes;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.add("mergingStrategy", mergingStrategy)
					.add("attributeRepositoryIds", attributeRepositoryIds)
					.add("ignoreResolvedAttributes", ignoreResolvedAttributes)
					.asString();
		}

	}

}
