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
package org.apereo.cas.logging.manager;

import com.buession.core.utils.StringUtils;
import org.junit.Test;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class DynamicModelTest {

	@Test
	public void className(){
		String collection = "history_log中";
		char[] chars = new char[collection.length()];
		int outOffset = 0;

		chars[outOffset++] = Character.toUpperCase(collection.charAt(0));
		for(int i = 1; i < chars.length; i++){
			char c = collection.charAt(i);
			if(c == '_'){
				c = collection.charAt(++i);
				chars[outOffset++] = Character.toUpperCase(c);
			}else if(('a' <= c && c <= 'z') || ('0' <= c && c <= '9')){
				chars[outOffset++] = c;
			}else{
				System.out.println("outOffset");
			}
		}
		System.out.println(outOffset);
		System.out.println(new String(chars, 0, outOffset));
	}

}
