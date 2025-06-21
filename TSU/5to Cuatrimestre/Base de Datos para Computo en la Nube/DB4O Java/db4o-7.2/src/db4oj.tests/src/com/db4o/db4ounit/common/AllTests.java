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
package com.db4o.db4ounit.common;

import db4ounit.extensions.Db4oTestSuite;

/**
 * 
 */
public class AllTests extends Db4oTestSuite {
	
	/**
	 * @sharpen.ignore test suited is executed differently under .net
	 */
	public static void main(String[] args) {
		System.exit(new AllTests().runSolo());
	}

	protected Class[] testCases() {
		return new Class[] {
			com.db4o.db4ounit.common.acid.AllTests.class,
			com.db4o.db4ounit.common.activation.AllTests.class,
			com.db4o.db4ounit.common.assorted.AllTests.class,
            com.db4o.db4ounit.common.btree.AllTests.class,
            com.db4o.db4ounit.common.classindex.AllTests.class,
            com.db4o.db4ounit.common.config.AllTests.class,
            com.db4o.db4ounit.common.constraints.AllTests.class,
            com.db4o.db4ounit.common.cs.AllTests.class,
			com.db4o.db4ounit.common.defragment.AllTests.class,
			com.db4o.db4ounit.common.events.AllTests.class,
			com.db4o.db4ounit.common.exceptions.AllTests.class,
			com.db4o.db4ounit.common.ext.AllTests.class,
            com.db4o.db4ounit.common.fatalerror.AllTests.class,
            com.db4o.db4ounit.common.fieldindex.AllTests.class,
            com.db4o.db4ounit.common.foundation.AllTests.class,
            com.db4o.db4ounit.common.freespace.AllTests.class,
			com.db4o.db4ounit.common.handlers.AllTests.class,
			com.db4o.db4ounit.common.header.AllTests.class,
			com.db4o.db4ounit.common.interfaces.AllTests.class,
			com.db4o.db4ounit.common.internal.AllTests.class,
			com.db4o.db4ounit.common.io.AllTests.class,
            com.db4o.db4ounit.common.refactor.AllTests.class,
            com.db4o.db4ounit.common.references.AllTests.class,
            com.db4o.db4ounit.common.reflect.AllTests.class,
			com.db4o.db4ounit.common.regression.AllTests.class,
			com.db4o.db4ounit.common.querying.AllTests.class,
			com.db4o.db4ounit.common.set.AllTests.class,
			com.db4o.db4ounit.common.soda.AllTests.class,
			com.db4o.db4ounit.common.stored.AllTests.class,
			com.db4o.db4ounit.common.ta.AllCommonTATests.class,
			com.db4o.db4ounit.common.tp.AllTests.class,
            com.db4o.db4ounit.common.types.AllTests.class,
            com.db4o.db4ounit.common.uuid.AllTests.class,
			com.db4o.db4ounit.util.test.AllTests.class,
		};
	}
}
