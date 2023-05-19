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
package org.apereo.cas.logging.utils;

import com.buession.geoip.Resolver;
import com.buession.geoip.model.Location;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.apereo.cas.logging.model.GeoLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class GeoIpUtils {

	private final static Logger logger = LoggerFactory.getLogger(GeoIpUtils.class);

	private GeoIpUtils(){

	}

	public static GeoLocation resolver(final Resolver resolver, final String clientIp){
		GeoLocation geoLocation = new GeoLocation();

		try{
			Location location = resolver.location(clientIp);

			if(location != null){
				GeoLocation.Country country = new GeoLocation.Country();

				country.setCode(location.getCountry().getCode());
				country.setName(location.getCountry().getName());
				country.setFullName(location.getCountry().getFullName());

				geoLocation.setCountry(country);

				GeoLocation.District district = new GeoLocation.District();

				district.setName(location.getDistrict().getName());
				district.setFullName(location.getDistrict().getFullName());

				geoLocation.setDistrict(district);
			}
		}catch(IOException e){
			logger.error(e.getMessage());
		}catch(GeoIp2Exception e){
			logger.error(e.getMessage());
		}

		return geoLocation;
	}

}
