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
package com.db4o.db4ounit.jre5.collections.typehandler;

import java.util.*;

import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;
import db4ounit.fixtures.*;

public class ArrayListTypeHandlerTestSuite extends FixtureBasedTestSuite implements Db4oTestCase {
	
	public FixtureProvider[] fixtureProviders() {
		ArrayListHandlerTestElementsSpec[] elementSpecs = {
				new ArrayListHandlerTestElementsSpec(new Object[]{ "zero", "one" }, "two"),
				new ArrayListHandlerTestElementsSpec(new Object[]{ new Integer(0), new Integer(1) }, new Integer(2)),
				new ArrayListHandlerTestElementsSpec(new Object[]{ new FirstClassElement(0), new FirstClassElement(2) }, new FirstClassElement(2)),
		};
		return new FixtureProvider[] {
			new Db4oFixtureProvider(),
			new SimpleFixtureProvider(
				ArrayListHandlerTestVariables.LIST_IMPLEMENTATION,
				new Deferred4() {
					public Object value() {
						return new ArrayList();
					}
				}
			),
			new SimpleFixtureProvider(
				ArrayListHandlerTestVariables.ELEMENTS_SPEC,
				elementSpecs
			),
//			new SimpleFixtureProvider(
//				AbstractDb4oTestCase.FIXTURE_VARIABLE,
//				new Object[] {
//					new Db4oInMemory(),
//					new Db4oSolo(),
//					new Db4oClientServer(configSource(), true, "c/s emb"),
//					new Db4oClientServer(configSource(), false, "c/s net"),
//				}
//			),
		};
	}

	public Class[] testUnits() { 
		return new Class[] {
			ArrayListTypeHandlerTestUnit.class,
		};
	}

	private IndependentConfigurationSource configSource() {
		return new IndependentConfigurationSource();
	}
	
}
