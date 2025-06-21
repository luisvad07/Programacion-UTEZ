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

import java.util.*;

public class ArrayMixedInObjectPublic extends RTest
{
	public Object o1;
	public Object o2;
	public Object o3;	
	public Object o4;
	public Object o5;
	public Object o6;	

	public void set(int ver){
		if(ver == 1){
			o1 = new Boolean[]{new Boolean(true), new Boolean(false), null };
			o2 = null;
			o3 = new Byte[]{ new Byte(Byte.MAX_VALUE), new Byte(Byte.MIN_VALUE), new Byte((byte)0), null};
			o4 = new Float[] {new Float(Float.MAX_VALUE - 1), new Float(Float.MIN_VALUE), new Float(0), null};
			o5 = new String[] {"db4o rules", "cool", "supergreat"};
			o6 = new Date[] {new GregorianCalendar(2000,0,1).getTime(), new GregorianCalendar(2000,0,1).getTime(), new GregorianCalendar(2001,11,31).getTime(), null};
		}else{
			o1 = new Date[] {new GregorianCalendar(2000,0,1).getTime(), new GregorianCalendar(2000,0,1).getTime(), new GregorianCalendar(2001,11,31).getTime(), null};
			o2 = null;
			o3 = new String[] {};
			o4 = new Boolean[]{new Boolean(false), new Boolean(true), new Boolean(true)};
			o5 = new Double[]{new Double(Double.MIN_VALUE), new Double(0)};
			o6 = new Object[]{"ohje", new Double(Double.MIN_VALUE), new Float(4), null};
		}

	}
}
