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

import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class UntypedEvaluationTestCase extends AbstractDb4oTestCase {

	private final static Class EXTENT = Object.class; // replace with Data.class -> green
	
	public static class Data {
		public int _id;

		public Data(int id) {
			_id = id;
		}
	}

	public static class UntypedEvaluation implements Evaluation {
		public boolean _value;
		
		public UntypedEvaluation(boolean value) {
			_value = value;
		}

		public void evaluate(Candidate candidate) {
			candidate.include(_value);
		}
	}

	protected void store() throws Exception {
		store(new Data(42));
	}

	public void testUntypedRaw() {
		Query query = newQuery(EXTENT); 
		Assert.areEqual(1, query.execute().size());
	}

	public void testUntypedEvaluationNone() {
		Query query = newQuery(EXTENT);
		query.constrain(new UntypedEvaluation(false));
		Assert.areEqual(0, query.execute().size());
	}

	public void testUntypedEvaluationAll() {
		Query query = newQuery(EXTENT);
		query.constrain(new UntypedEvaluation(true));
		Assert.areEqual(1, query.execute().size());
	}

}
