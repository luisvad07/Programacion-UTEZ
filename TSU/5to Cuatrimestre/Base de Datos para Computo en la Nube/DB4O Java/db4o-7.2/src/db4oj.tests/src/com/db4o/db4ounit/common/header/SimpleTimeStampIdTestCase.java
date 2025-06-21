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
package com.db4o.db4ounit.common.header;

import com.db4o.config.*;
import com.db4o.internal.*;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.OptOutCS;

public class SimpleTimeStampIdTestCase extends AbstractDb4oTestCase implements
		OptOutCS {

	public static void main(String[] arguments) {
		new SimpleTimeStampIdTestCase().runSolo();
	}

	public static class STSItem {

		public String _name;

		public STSItem() {
		}

		public STSItem(String name) {
			_name = name;
		}
	}

	protected void configure(Configuration config) {
		ObjectClass objectClass = config.objectClass(STSItem.class);
		objectClass.generateUUIDs(true);
		objectClass.generateVersionNumbers(true);
	}

	protected void store() {
		db().store(new STSItem("one"));
	}

	public void test() throws Exception {
		STSItem item = (STSItem) db().queryByExample(STSItem.class).next();

		long version = db().getObjectInfo(item).getVersion();
		Assert.isGreater(0, version);
		Assert.isGreaterOrEqual(version, currentVersion());

		reopen();

		STSItem item2 = new STSItem("two");
		db().store(item2);

		long secondVersion = db().getObjectInfo(item2).getVersion();

		Assert.isGreater(version, secondVersion);
		Assert.isGreaterOrEqual(secondVersion, currentVersion());
	}

	private long currentVersion() {
		return ((LocalObjectContainer) db()).currentVersion();
	}
}
