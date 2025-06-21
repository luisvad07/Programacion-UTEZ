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
package com.db4o.db4ounit.common.exceptions;

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class InvalidSlotExceptionTestCase extends AbstractDb4oTestCase {

	private static final int MAX = 100000;

	public static void main(String[] args) {
		new InvalidSlotExceptionTestCase().runAll();
	}
	
	public void testInvalidSlotException() throws Exception {
		Assert.expect(InvalidIDException.class, InvalidSlotException.class, new CodeBlock(){
			public void run() throws Throwable {
				/*Object byID = */db().getByID(1);		
			}
		});
		
	}
	
	public void _testTimes() throws Exception {
		long ids[] = new long[MAX];
		for (int i = 0; i < MAX; i++) {
			Object o = complexObject();
			db().store(o);
			ids[i] = db().ext().getID(o);
		}
		reopen();
		for (int i = 0; i < MAX; i++) {
			db().ext().getByID(ids[i]);
		}
	}

	public static class A{
		A _a;
		public A(A a) {
			this._a = a;
		}
	}
	private Object complexObject() {
		return new A(new A(new A(null)));
	}
	
	

}
