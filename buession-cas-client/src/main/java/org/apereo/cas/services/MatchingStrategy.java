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
 * Represents a strategy on how a registered service could be matched against
 * an incoming request using its service id, etc.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class MatchingStrategy implements Entity {

	private final static long serialVersionUID = 5487847944611025130L;

	/**
	 * This is {@link FullRegexRegisteredServiceMatchingStrategy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class FullRegexRegisteredServiceMatchingStrategy extends MatchingStrategy {

		private final static long serialVersionUID = -5475249649783863764L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

	/**
	 * This is {@link PartialRegexRegisteredServiceMatchingStrategy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class PartialRegexRegisteredServiceMatchingStrategy extends MatchingStrategy {

		private final static long serialVersionUID = -7128281837981882105L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

	/**
	 * This is {@link LiteralRegisteredServiceMatchingStrategy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class LiteralRegisteredServiceMatchingStrategy extends MatchingStrategy {

		private final static long serialVersionUID = 5749700415198045976L;

		private boolean caseInsensitive;

		public boolean isCaseInsensitive(){
			return caseInsensitive;
		}

		public void setCaseInsensitive(boolean caseInsensitive){
			this.caseInsensitive = caseInsensitive;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("caseInsensitive", caseInsensitive)
					.asString();
		}

	}

}
