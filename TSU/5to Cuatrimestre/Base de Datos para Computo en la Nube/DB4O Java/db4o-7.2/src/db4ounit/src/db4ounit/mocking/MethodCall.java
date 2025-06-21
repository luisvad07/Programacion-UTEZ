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
package db4ounit.mocking;

import com.db4o.foundation.*;

import db4ounit.*;

public class MethodCall {
	
	public static final Object IGNORED_ARGUMENT = new Object() {
		public String toString() {
			return "...";
		}
	};
	
	public static interface ArgumentCondition {
		void verify(Object argument);
	}
	
	public final String methodName;
	public final Object[] args;
	
	public MethodCall(String methodName) {
		this(methodName, new Object[0]);
	}
	
	public MethodCall(String methodName, Object[] args) {
		this.methodName = methodName;
		this.args = args;
	}
	
	public MethodCall(String methodName, Object singleArg) {
		this(methodName, new Object[] { singleArg });
	}
	
	public MethodCall(String methodName, Object arg1, Object arg2) {
		this(methodName, new Object[] { arg1, arg2 });
	}

	public String toString() {
		return methodName + "(" + Iterators.join(Iterators.iterate(args), ", ") + ")";
	}
	
	public boolean equals(Object obj) {
		if (null == obj) return false;
		if (getClass() != obj.getClass()) return false;
		MethodCall other = (MethodCall)obj;
		if (!methodName.equals(other.methodName)) return false;
		if (args.length != other.args.length) return false;
		for (int i=0; i<args.length; ++i) {
			final Object expectedArg = args[i];
			if (expectedArg == IGNORED_ARGUMENT) {
				continue;
			}
			final Object actualArg = other.args[i];
			if (expectedArg instanceof ArgumentCondition) {
				((ArgumentCondition)expectedArg).verify(actualArg);
				continue;
			}
			
			if (!Check.objectsAreEqual(expectedArg, actualArg)) {
				return false;
			}
		}
		return true;
	}
}
