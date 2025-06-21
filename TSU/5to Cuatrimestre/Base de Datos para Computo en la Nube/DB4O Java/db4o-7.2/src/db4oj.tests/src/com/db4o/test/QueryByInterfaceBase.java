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
package com.db4o.test;

import com.db4o.*;
import com.db4o.query.*;

public abstract class QueryByInterfaceBase {
	
	private static class SimpleEvaluation implements Evaluation {
		private String value;
		
		private SimpleEvaluation(String value) {
			this.value = value;
		}

		public void evaluate(Candidate candidate) {
			candidate.include(((IFoo)candidate.getObject()).s().equals(value));
		}
	}

	public static interface IFoo {
		String s();
	}
	
	public static class Bar implements IFoo {
		public int i;

		public Bar(int i) {
			this.i = i;
		}

		public String s() {
			return String.valueOf((char)('A'+i));
		}
	}

	public static class Baz implements IFoo {
		public String s;

		public Baz(String s) {
			this.s = s;
		}

		public String s() {
			return s;
		}
	}

	protected void assertSODA(String value,int expCount) {
		Query query=Test.objectContainer().query();
		Constraint constraint=query.constrain(IFoo.class);
		Test.ensure(constraint!=null);
		query.descend("s").constrain(value);
		ObjectSet result=query.execute();
		Test.ensure(result.size()==expCount);
	}

	protected void assertEvaluation(String value,int expCount) {
		Query query=Test.objectContainer().query();
		Constraint constraint=query.constrain(IFoo.class);
		Test.ensure(constraint!=null);
		query.constrain(new SimpleEvaluation(value));
		ObjectSet result=query.execute();
		Test.ensure(result.size()==expCount);
	}
}
