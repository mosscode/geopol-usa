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

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.moss.geopol.usa.jaxb.UsaCityXmlAdapter;

@XmlJavaTypeAdapter(UsaCityXmlAdapter.class)
public final class UsaCity implements Serializable {

	private String name;
	
	UsaCity() {}

	public UsaCity(String name) throws E2CityNameParserException {
		
		if (name == null || name.length() == 0) {
			throw new E2CityNameParserException();
		}
		
		this.name = name;
	}
	
	public UsaCity(UsaCity other){
		this.name = other.name;
	}
	
	public UsaCity copy() {
		return new UsaCity(this);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		return 
			o != null
			&&
			o instanceof UsaCity
			&&
			((UsaCity)o).toString().equals(toString());
	}
	
	public static class E2CityNameParserException extends Exception {
		public E2CityNameParserException() {
			super("City name cannot be null or length zero");
		}
	}

	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		System.out.println(UsaState.values().length);
		for (UsaState s : UsaState.values()) {
			System.out.print(s.getAbbreviation() + "|");
		}
	}
}

