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

public class DeepSetTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new DeepSetTestCase().runConcurrency();
	}
	
	public DeepSetTestCase child;

	public String name;

	protected void store() {
		name = "1";
		child = new DeepSetTestCase();
		child.name = "2";
		child.child = new DeepSetTestCase();
		child.child.name = "3";
		store(this);
	}

	public void conc(ExtObjectContainer oc, int seq) {
		DeepSetTestCase example = new DeepSetTestCase();
		example.name = "1";
		DeepSetTestCase ds = (DeepSetTestCase) oc.queryByExample(example).next();
		Assert.areEqual("1", ds.name);
		Assert.areEqual("3", ds.child.child.name);
		ds.name = "1";
		ds.child.name = "12" + seq;
		ds.child.child.name = "13" + seq;
		oc.store(ds, 2);
	}

	public void check(ExtObjectContainer oc) {
		DeepSetTestCase example = new DeepSetTestCase();
		example.name = "1";
		DeepSetTestCase ds = (DeepSetTestCase) oc.queryByExample(example).next();
		Assert.isTrue(ds.child.name.startsWith("12"));
		Assert.isTrue(ds.child.name.length() > "12".length());
		Assert.areEqual("3", ds.child.child.name);
	}

}
