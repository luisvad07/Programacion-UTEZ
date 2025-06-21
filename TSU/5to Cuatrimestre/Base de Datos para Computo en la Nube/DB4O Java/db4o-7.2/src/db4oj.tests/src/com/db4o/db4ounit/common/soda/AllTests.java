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
package com.db4o.db4ounit.common.soda;

import com.db4o.db4ounit.common.soda.classes.simple.*;
import com.db4o.db4ounit.common.soda.classes.typedhierarchy.*;
import com.db4o.db4ounit.common.soda.classes.untypedhierarchy.*;
import com.db4o.db4ounit.common.soda.joins.typed.*;
import com.db4o.db4ounit.common.soda.joins.untyped.STOrUTestCase;
import com.db4o.db4ounit.common.soda.ordered.*;
import com.db4o.db4ounit.common.soda.wrapper.untyped.*;

import db4ounit.extensions.Db4oTestSuite;

public class AllTests extends Db4oTestSuite {

	protected Class[] testCases() {
		return new Class[]{
				STOrderingTestCase.class,
				com.db4o.db4ounit.common.soda.arrays.AllTests.class,
				AndJoinOptimizationTestCase.class,
				CollectionIndexedJoinTestCase.class,
				NullIdentityConstraintTestCase.class,
				OrderFollowedByConstraintTestCase.class,
				QueryUnknownClassTestCase.class,
				SortMultipleTestCase.class,
				STBooleanTestCase.class,				
				STBooleanWUTestCase.class,
				STByteTestCase.class,
				STByteWUTestCase.class,
				STCharTestCase.class,
				STCharWUTestCase.class,
				STDoubleTestCase.class,
				STDoubleWUTestCase.class,
				STETH1TestCase.class,
				STFloatTestCase.class,
				STFloatWUTestCase.class,
				STIntegerTestCase.class,
				STIntegerWUTestCase.class,
				STLongTestCase.class,
				STLongWUTestCase.class,
				STOrTTestCase.class,
				STOrUTestCase.class,
				STOStringTestCase.class,
				STOIntegerTestCase.class,
				STOIntegerWTTestCase.class,
				STRTH1TestCase.class,
				STSDFT1TestCase.class,
				STShortTestCase.class,
				STShortWUTestCase.class,
				STStringUTestCase.class,
				STRUH1TestCase.class,
				STTH1TestCase.class,
				STUH1TestCase.class,
				UntypedEvaluationTestCase.class,
				JointEqualsIdentityTestCase.class,
		};
	}
	
	public static void main(String[] args) {
		new AllTests().runSolo();
	}

}
