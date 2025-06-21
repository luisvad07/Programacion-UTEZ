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
import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class DatabaseReadonlyExceptionTestCase
	extends AbstractDb4oTestCase
	implements OptOutTA {

	public static void main(String[] args) {
		new DatabaseReadonlyExceptionTestCase().runAll();
	}

	public void testRollback() {
		configReadOnly();
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				db().rollback();
			}
		});
	}

	public void testCommit() {
		configReadOnly();
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				db().commit();
			}
		});
	}

	public void testSet() {
		configReadOnly();
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				db().store(new Item());
			}
		});
	}

	public void testDelete() {
		configReadOnly();
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				db().delete(null);
			}
		});
	}
	
	public void testNewFile() {
		Assert.expect(DatabaseReadOnlyException.class, new CodeBlock() {
			public void run() throws Throwable {
				fixture().close();
				fixture().clean();
				fixture().config().readOnly(true);
				fixture().open(getClass());
			}
		});
	}

	public void testReserveStorage() {
	    configReadOnly();
		Class exceptionType = isClientServer() && ! isMTOC() ? NotSupportedException.class
				: DatabaseReadOnlyException.class;
		Assert.expect(exceptionType, new CodeBlock() {
			public void run() throws Throwable {
				db().configure().reserveStorageSpace(1);
			}
		});
	}
	
	public void testStoredClasses() {
	    configReadOnly();
	    db().storedClasses();
	}
	
	private void configReadOnly() {
		db().configure().readOnly(true);
	}
	
}
