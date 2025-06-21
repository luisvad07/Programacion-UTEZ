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

import db4ounit.extensions.*;

public class AllTests extends Db4oConcurrencyTestSuite {
	
	public static void main(String[] args) {
		new AllTests().runConcurrency();
	}

	protected Class[] testCases() {
		return new Class[] { 
				ArrayNOrderTestCase.class, 
				ByteArrayTestCase.class,
				CascadeDeleteDeletedTestCase.class,
				CascadeDeleteFalseTestCase.class,
				CascadeOnActivateTestCase.class,
				CascadeOnUpdateTestCase.class,
				CascadeOnUpdate2TestCase.class,
				CascadeToVectorTestCase.class,
				CaseInsensitiveTestCase.class,
				Circular1TestCase.class,
				ClientDisconnectTestCase.class,
				CreateIndexInheritedTestCase.class,
				DeepSetTestCase.class,
				DeleteDeepTestCase.class,
				DifferentAccessPathsTestCase.class,
				ExtMethodsTestCase.class,
				GetAllTestCase.class,
				GreaterOrEqualTestCase.class,
				IndexedByIdentityTestCase.class,
				IndexedUpdatesWithNullTestCase.class,
				InternStringsTestCase.class,
				InvalidUUIDTestCase.class,
				IsStoredTestCase.class,
				MessagingTestCase.class,
				MultiDeleteTestCase.class,
				MultiLevelIndexTestCase.class,
				NestedArraysTestCase.class,
				ObjectSetIDsTestCase.class,
				ParameterizedEvaluationTestCase.class,
				PeekPersistedTestCase.class,
				PersistStaticFieldValuesTestCase.class,
				QueryForUnknownFieldTestCase.class,
				QueryNonExistantTestCase.class,
				ReadObjectNQTestCase.class,
				ReadObjectQBETestCase.class,
				ReadObjectSODATestCase.class,
				RefreshTestCase.class,
				UpdateObjectTestCase.class,
		};
	}

}
