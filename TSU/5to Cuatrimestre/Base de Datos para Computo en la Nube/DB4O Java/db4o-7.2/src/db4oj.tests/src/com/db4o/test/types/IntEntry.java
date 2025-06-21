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

public class IntEntry extends TEntry {
	
	public TEntry firstElement(){
		return new TEntry(new Integer(101), "firstvalue");
	}

	public TEntry lastElement(){
		return new TEntry(new Integer(9999999), new ObjectSimplePublic("lastValue"));
	}

	public TEntry noElement(){
		return new TEntry(new Integer(-99999), "babe");
	}

	public TEntry[] test(int ver){
		if(ver == 1){
			return new TEntry[]{
				firstElement(),
				new TEntry(new Integer(111), new ObjectSimplePublic("111")),
				new TEntry(new Integer(9999111), new Double(0.4566)),
				lastElement()
			};
		}
		return new TEntry[]{
			new TEntry(new Integer(222), new ObjectSimplePublic("111")),
			new TEntry(new Integer(333), "TrippleThree"),
			new TEntry(new Integer(4444), new ObjectSimplePublic("4444")),
		};
	}
}