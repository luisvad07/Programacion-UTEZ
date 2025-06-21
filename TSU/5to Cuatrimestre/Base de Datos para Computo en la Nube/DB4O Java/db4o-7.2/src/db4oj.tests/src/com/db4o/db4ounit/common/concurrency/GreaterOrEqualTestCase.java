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

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class GreaterOrEqualTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new GreaterOrEqualTestCase().runConcurrency();
	}

	public int val;

	public GreaterOrEqualTestCase() {

	}

	public GreaterOrEqualTestCase(int val) {
		this.val = val;
	}

	protected void store() {
		store(new GreaterOrEqualTestCase(1));
		store(new GreaterOrEqualTestCase(2));
		store(new GreaterOrEqualTestCase(3));
		store(new GreaterOrEqualTestCase(4));
		store(new GreaterOrEqualTestCase(5));
	}

	public void conc(ExtObjectContainer oc) {
		int[] expect = { 3, 4, 5 };
		Query q = oc.query();
		q.constrain(GreaterOrEqualTestCase.class);
		q.descend("val").constrain(new Integer(3)).greater().equal();
		ObjectSet res = q.execute();
		while (res.hasNext()) {
			GreaterOrEqualTestCase r = (GreaterOrEqualTestCase) res.next();
			for (int i = 0; i < expect.length; i++) {
				if (expect[i] == r.val) {
					expect[i] = 0;
				}
			}
		}
		for (int i = 0; i < expect.length; i++) {
			Assert.areEqual(0, expect[i]);
		}
	}

}
