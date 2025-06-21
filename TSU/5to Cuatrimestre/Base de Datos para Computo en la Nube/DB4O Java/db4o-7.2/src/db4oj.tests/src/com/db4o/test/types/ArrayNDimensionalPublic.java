/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package com.db4o.test.types;

public class ArrayNDimensionalPublic extends RTest
{
	public String[][][] s1;
	public Object[][] o1;
	
	public void set(int ver){
		if(ver == 1){
			s1 = new String[2][2][3];
			s1[0][0][0] = "000";
			s1[0][0][1] = "001";
			s1[0][1][0] = "010";
			s1[0][1][1] = "011";
			s1[1][0][0] = "100";
			s1[1][0][1] = "101";
			s1[1][1][0] = "110";
			s1[1][1][1] = "111";
		
			o1 = new Object[2][2];
			o1[0][0] = new Integer(0);
			o1[0][1] = "01";
			o1[1][0] = new Float(10);
			o1[1][1] = new Double(1.1);
		}else{
			s1 = new String[1][2][2];
			s1[0][0][0] = "2000";
			s1[0][1][0] = "2010";
			s1[0][0][1] = "2001";
			s1[0][1][1] = "2011";
		
			o1 = new Object[2][3];
			o1[0][0] = null;
			o1[0][1] = new Integer(1);
			o1[0][2] = new Integer(2);
			o1[1][0] = new Float(10);
			o1[1][1] = new Double(1.1);
			o1[1][2] = new Double(1.2);
		}
	}
}
