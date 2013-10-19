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
/**
 * 
 */
package com.moss.geopol.usa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.moss.geopol.usa.jaxb.UsaSsnXmlAdapter;

@XmlType()
@XmlJavaTypeAdapter(UsaSsnXmlAdapter.class)
public final class UsaSSn implements Serializable {
	private String ssn;

	public UsaSSn(String ssn) throws StInvalidSSnException {
		parseSSn(ssn);
	}
	
	public UsaSSn(long ssn) throws StInvalidSSnException {
		parseSSn((new Long(ssn)).toString());
	}
	
	public void parseSSn(String ssn) throws StInvalidSSnException {
		if(ssn.length() != 9) {
			String regex = "-";
			// SPLITS BY HYPHONS OR SPACES
			if(ssn.indexOf('-') != -1) regex = "-";
			else if(ssn.indexOf(' ') != -1) regex = " ";
			String[] parsedCode = ssn.split(regex);
			if(parsedCode.length != 3) throw new StInvalidSSnException("Invalid SSN format");
			
			try {
				Integer.parseInt(parsedCode[0].trim());
				Integer.parseInt(parsedCode[1].trim());
				Integer.parseInt(parsedCode[2].trim());
				if(parsedCode[0].trim().length() != 3 || parsedCode[1].trim().length() != 2 
						|| parsedCode[2].trim().length() != 4) {
					throw new StInvalidSSnException("Invalid SSN format");
				} 
				
				this.ssn = parsedCode[0] + parsedCode[1] + parsedCode[2];
				return;
				
			} catch(NumberFormatException ex) {
				throw new StInvalidSSnException("SSN must only contain numerical data.");
			}
			
		} else {
			// NO DELIMITERS
			try {
				Integer.parseInt(ssn);
				this.ssn = ssn;
				return;
			} catch(NumberFormatException ex) {
				throw new StInvalidSSnException("Social Security Numbers must only contain numerical data");
			}
		}
	}
	
	@Override
	public String toString() {
		return ssn;
	}
	
	public long toLong() {
		return new Long(ssn).longValue();
	}
	
	@Override
	public boolean equals(Object o) {
		return
			o != null
			&&
			o instanceof UsaSSn
			&&
			ssn.equals( ((UsaSSn)o).ssn);
	}
	
	/**
	 * @returns The next sequential ssn
	 */
	public UsaSSn next() {
		UsaSSn newSSN = null;
		try {
			newSSN = new UsaSSn(toLong() + 1);
		} catch(StInvalidSSnException ex) {
			ex.printStackTrace();
		}
		
		return newSSN;
	}
	

	public static boolean withinOneChar(UsaSSn alpha, UsaSSn beta){
		String as = alpha.toString().trim();
		String bs = beta.toString().trim();
		
		if(as.equals(bs)) return true;
		
		int diff = as.length() - bs.length();
		
		if(diff!=0){
			System.out.println("LaxObjectDiffent lengths!" + diff);
			StringBuffer buff = new StringBuffer();
			for(int x=0;x<diff;x++){
				buff.append(' ');
			}
			
			if(diff>0){
				bs = buff.append(bs).toString();
			}else{
				as = buff.append(as).toString();
			}
		}
		
		boolean foundCloseChar = false;
		
		for(int x=0;x<as.length();x++){
			char a = as.charAt(x);
			char b = bs.charAt(x);
			
			try {
				int ia = Integer.valueOf(String.valueOf(a));
				int ib = Integer.valueOf(String.valueOf(b));
				
				if(Math.abs(ia-ib)==1){
					if(foundCloseChar)
						return false;// multiple close chars
					else
						foundCloseChar = true;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
		
		return foundCloseChar;
	}
	
	/**
	 * @returns The next sequential ssn
	 */
	public UsaSSn previous() {
		UsaSSn newSSN = null;
		try {
			newSSN = new UsaSSn(toLong() - 1);
		} catch(StInvalidSSnException ex) {
			ex.printStackTrace();
		}
		
		return newSSN;
	}

	@Override
	public int hashCode() {
		return ssn.hashCode();
	}
	
	
}
