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
package com.db4o.db4ounit.common.fatalerror;

import com.db4o.query.Predicate;

import db4ounit.Assert;
import db4ounit.CodeBlock;
import db4ounit.extensions.AbstractDb4oTestCase;

public class NativeQueryTestCase extends AbstractDb4oTestCase {
	
	public static class Item {
		
		public String str;

		public Item(String s) {
			str = s;
		}
	}

	public static void main(String[] args) {
		new NativeQueryTestCase().runSoloAndClientServer();
	}

	protected void store() throws Exception {
		store(new Item("hello"));
	}

	public void _test() {
		Assert.expect(NQError.class, new CodeBlock() {
			public void run() throws Throwable {
				Predicate fatalErrorPredicate = new FatalErrorPredicate();
				db().query(fatalErrorPredicate);
			}
		});
		Assert.isTrue(db().isClosed());
	}

	public static class FatalErrorPredicate extends Predicate {
		public boolean match(Object item) {
			throw new NQError("nq error!");
		}
	}
	
	public static class NQError extends Error {
		public NQError(String msg) {
			super(msg);
		}
	}
}
