/**
 * Copyright (C) 2013, Moss Computing Inc.
 *
 * This file is part of geopol-usa.
 *
 * geopol-usa is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * geopol-usa is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with geopol-usa; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version.
 */
package com.moss.geopol.usa;

import java.util.HashMap;
import java.util.Map;

public enum UsaState {
	ALASKA("Alaska ","AK"),
	ALABAMA("Alabama ","AL"),
	ARKANSAS("Arkansas ","AR"),
	ARIZONA("Arizona ","AZ"),
	CALIFORNIA("California ","CA"),
	COLORADO("Colorado ","CO"),
	CONNECTICUT("Connecticut ","CT"),
	DIST_OF_COLUMBIA("Dist of Columbia ","DC"),
	DELAWARE("Delaware ","DE"),
	FEDERAL_GOVERNMENT("Federal Government ","FG"),
	FLORIDA("Florida ","FL"),
	GEORGIA("Georgia ","GA"),
	HAWAII("Hawaii ","HI"),
	IOWA("Iowa ","IA"),
	IDAHO("Idaho ","ID"),
	ILLINOIS("Illinois ","IL"),
	INDIANA("Indiana ","IN"),
	KANSAS("Kansas ","KS"),
	KENTUCKY("Kentucky ","KY"),
	LOUISIANA("Louisiana ","LA"),
	MASSACHUSETTS("Massachusetts ","MA"),
	MARYLAND("Maryland ","MD"),
	MAINE("Maine ","ME"),
	MICHIGAN("Michigan ","MI"),
	MINNESOTA("Minnesota ","MN"),
	MISSOURI("Missouri ","MO"),
	MISSISSIPPI("Mississippi ","MS"),
	MONTANA("Montana ","MT"),
	NORTH_CAROLINA("North Carolina ","NC"),
	NORTH_DAKOTA("North Dakota ","ND"),
	NEBRASKA("Nebraska ","NE"),
	NEW_HAMPSHIRE("New Hampshire ","NH"),
	NEW_JERSEY("New Jersey ","NJ"),
	NEW_MEXICO("New Mexico ","NM"),
	NEVADA("Nevada ","NV"),
	NEW_YORK("New York ","NY"),
	OHIO("Ohio ","OH"),
	OKLAHOMA("Oklahoma ","OK"),
	OREGON("Oregon ","OR"),
	PENNSYLVANIA("Pennsylvania ","PA"),
	RHODE_ISLAND("Rhode Island ","RI"),
	SOUTH_CAROLINA("South Carolina ","SC"),
	SOUTH_DAKOTA("South Dakota ","SD"),
	TENNESSEE("Tennessee ","TN"),
	TEXAS("Texas ","TX"),
	UTAH("Utah ","UT"),
	VIRGINIA("Virginia ","VA"),
	VERMONT("Vermont ","VT"),
	WASHINGTON("Washington ","WA"),
	WISCONSIN("Wisconsin ","WI"),
	WEST_VIRGINIA("West Virginia ","WV"),
	WYOMING("Wyoming ","WY")
	;
	
	private String fullName;
	private String abbreviation;
	private UsaState(String fullName, String abbreviation) {
		this.fullName = fullName;
		this.abbreviation = abbreviation;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public String getFullName() {
		return fullName;
	}
	
	private static Object sync = new Object();
	private static Map<String, UsaState> abbreviationMap;
	public static UsaState getByAbbreviation(String abbreviation) {
		
		synchronized (sync) {
			if (abbreviationMap == null) {
				abbreviationMap = new HashMap<String, UsaState>();
				for (UsaState s : values()) {
					abbreviationMap.put(s.getAbbreviation(), s);
				}
			}
		}
		
		if (abbreviation == null) {
			return null;
		}
		
		return abbreviationMap.get(abbreviation.toUpperCase());
	}
	
}
