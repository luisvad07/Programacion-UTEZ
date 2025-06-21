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

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ParameterizedEvaluationTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new ParameterizedEvaluationTestCase().runConcurrency();
	}

	public String str;

	protected void store() {
		store("one");
		store("fun");
		store("ton");
		store("sun");
	}

	private void store(String str) {
		ParameterizedEvaluationTestCase pe = new ParameterizedEvaluationTestCase();
		pe.str = str;
		store(pe);
	}

	public void conc(ExtObjectContainer oc) {
		Assert.areEqual(2, queryContains(oc, "un").size());
	}

	private ObjectSet queryContains(ExtObjectContainer oc, final String str) {
		Query q = oc.query();
		q.constrain(ParameterizedEvaluationTestCase.class);
		q.constrain(new MyEvaluation(str));
		return q.execute();
	}
	
	public static class MyEvaluation implements Evaluation {
		public String str;
		public MyEvaluation(String str) {
			this.str = str;
		}
		public void evaluate(Candidate candidate) {
			ParameterizedEvaluationTestCase pe = (ParameterizedEvaluationTestCase) candidate
					.getObject();
			boolean inc = pe.str.indexOf(str) != -1;
			candidate.include(inc);
		}
	}

}
