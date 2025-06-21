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

public class IsStoredTestCase extends Db4oClientServerTestCase {

	public static void main(String[] args) {
		new IsStoredTestCase().runConcurrency();
	}

	public String myString;
	
	public void conc(ExtObjectContainer oc) {
		IsStoredTestCase isStored = new IsStoredTestCase();
		isStored.myString = "isStored";
		oc.store(isStored);
		Assert.isTrue(oc.isStored(isStored));
		oc.commit();
		oc.delete(isStored);
		Assert.isFalse(oc.isStored(isStored));
		oc.rollback();
		Assert.isTrue(oc.isStored(isStored));
		oc.delete(isStored);
		Assert.isFalse(oc.isStored(isStored));
		oc.commit();
		Assert.isFalse(oc.isStored(isStored));
	}

	public void check(ExtObjectContainer oc) {
		assertOccurrences(oc, IsStoredTestCase.class, 0);
	}

}
