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

import junit.framework.TestCase;

public class PostalCodeTest extends TestCase{
	
	UsaPostalCode postalCode;
	
	public PostalCodeTest() throws Exception{
		postalCode = new UsaPostalCode("33333", "4444");
	}
	public void testRun() throws Exception {
		String[] validFormats = {
				"111111111",
				"11119-2345",
				"99999 2345",
				"44444"
				};
		for (String format : validFormats) {
			assertValid(format);
		}
		
		String[] invalidFormats = {
				"1234", 
				"123456",
				"1234567890",
				"1"
				};
		for (String format : invalidFormats) {
			assertInvalid(format);
		}
		
	}
	private void assertInvalid(String format){
		String message = "Converter should have choked on this format, but it didn't: " + format;
		try {
			postalCode = new UsaPostalCode(format);
			fail(message);
		} catch (Exception e) {
			//ok, expected
		}

	}
	private void assertValid(String format){
		String message = "Converter choked on this format: " + format;
		try {
			postalCode = new UsaPostalCode(format);
		} catch (Exception e) {
			e.printStackTrace();
			fail(message);
		}
	}
}