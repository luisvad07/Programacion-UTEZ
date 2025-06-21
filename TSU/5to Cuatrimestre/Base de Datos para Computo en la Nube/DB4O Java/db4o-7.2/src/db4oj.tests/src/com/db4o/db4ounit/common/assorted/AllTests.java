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
package com.db4o.db4ounit.common.assorted;

import db4ounit.extensions.*;

public class AllTests extends Db4oTestSuite {

	public static void main(String[] args) {
		new AllTests().runSolo();
    }

	protected Class[] testCases() {
		return new Class[] {
			AliasesTestCase.class,
            BackupStressTestCase.class,
            CallbackTestCase.class,
            CanUpdateFalseRefreshTestCase.class,
            CascadeDeleteDeletedTestCase.class,
            CascadedDeleteReadTestCase.class,
            ChangeIdentity.class,
            ClassMetadataTestCase.class,
            CloseUnlocksFileTestCase.class,
            ComparatorSortTestCase.class,
            DatabaseGrowthSizeTestCase.class,
            DatabaseUnicityTest.class,
            // FIXME: COR-1060
//            DeleteSetTestCase.class,
            DeleteUpdateTestCase.class,
            DescendToNullFieldTestCase.class,
            DualDeleteTestCase.class,
            GetSingleSimpleArrayTestCase.class,
            HandlerRegistryTestCase.class,
            IndexCreateDropTestCase.class,
            IndexedBlockSizeQueryTestCase.class,
            InMemoryObjectContainerTestCase.class,
            LazyObjectReferenceTestCase.class,
            LockedTreeTestCase.class,
            LongLinkedListTestCase.class,
            MultiDeleteTestCase.class,
            PlainObjectTestCase.class,
            PeekPersistedTestCase.class,
            PersistentIntegerArrayTestCase.class,
            PersistStaticFieldValuesTestCase.class,
            PersistTypeTestCase.class,
            PreventMultipleOpenTestCase.class,
            QueryByInterface.class,
            ReAddCascadedDeleteTestCase.class,
            RepeatDeleteReaddTestCase.class,
            RollbackDeleteTestCase.class,
            RollbackTestCase.class,
			RollbackUpdateTestCase.class,
			RollbackUpdateCascadeTestCase.class,
			SimplestPossibleNullMemberTestCase.class,
            SimplestPossibleTestCase.class,
            SimplestPossibleParentChildTestCase.class,
            SystemInfoTestCase.class,
            UpdateDepthTestCase.class,
		};
	}
}
