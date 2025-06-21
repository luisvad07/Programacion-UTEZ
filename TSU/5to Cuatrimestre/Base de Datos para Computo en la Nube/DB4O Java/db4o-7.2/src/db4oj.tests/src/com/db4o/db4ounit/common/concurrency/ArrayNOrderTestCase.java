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
package com.db4o.db4ounit.common.concurrency;

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ArrayNOrderTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new ArrayNOrderTestCase().runConcurrency();
	}

	public static class Item {
		public String[][][] s1;

		public Object[][] o1;
	}

	protected void store() {
		Item item = new Item();
		item.s1 = new String[2][2][3];
		item.s1[0][0][0] = "000";
		item.s1[0][0][1] = "001";
		item.s1[0][0][2] = "002";
		item.s1[0][1][0] = "010";
		item.s1[0][1][1] = "011";
		item.s1[0][1][2] = "012";
		item.s1[1][0][0] = "100";
		item.s1[1][0][1] = "101";
		item.s1[1][0][2] = "102";
		item.s1[1][1][0] = "110";
		item.s1[1][1][1] = "111";
		item.s1[1][1][2] = "112";

		item.o1 = new Object[2][2];
		item.o1[0][0] = new Integer(0);
		item.o1[0][1] = "01";
		item.o1[1][0] = new Float(10);
		item.o1[1][1] = new Double(1.1);
		store(item);
	}

	public void conc(ExtObjectContainer oc) {
		Item item = (Item) retrieveOnlyInstance(oc, Item.class);
		assertItem(item);
	}

	public void assertItem(Item item) {
		Assert.areEqual(item.s1[0][0][0], "000");
		Assert.areEqual(item.s1[0][0][1], "001");
		Assert.areEqual(item.s1[0][0][2], "002");
		Assert.areEqual(item.s1[0][1][0], "010");
		Assert.areEqual(item.s1[0][1][1], "011");
		Assert.areEqual(item.s1[0][1][2], "012");
		Assert.areEqual(item.s1[1][0][0], "100");
		Assert.areEqual(item.s1[1][0][1], "101");
		Assert.areEqual(item.s1[1][0][2], "102");
		Assert.areEqual(item.s1[1][1][0], "110");
		Assert.areEqual(item.s1[1][1][1], "111");
		Assert.areEqual(item.s1[1][1][2], "112");
		Assert.areEqual(item.o1[0][0], new Integer(0));
		Assert.areEqual(item.o1[0][1], "01");
		Assert.areEqual(item.o1[1][0], new Float(10));
		Assert.areEqual(item.o1[1][1], new Double(1.1));
	}
}
