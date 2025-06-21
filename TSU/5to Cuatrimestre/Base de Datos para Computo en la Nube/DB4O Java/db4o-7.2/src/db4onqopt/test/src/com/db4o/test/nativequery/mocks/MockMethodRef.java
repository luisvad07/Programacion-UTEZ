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
package com.db4o.test.nativequery.mocks;

import java.lang.reflect.*;

import com.db4o.instrumentation.api.*;

import db4ounit.*;

public class MockMethodRef implements MethodRef {

	private final Method _method;

	public MockMethodRef(Method method) {
		_method = method;
	}

	public String name() {
		return _method.getName();
	}

	public TypeRef[] paramTypes() {
		final Class[] paramTypes = _method.getParameterTypes();
		final TypeRef[] types = new TypeRef[paramTypes.length];
		for (int i=0; i<paramTypes.length; ++i) {
			types[i] = typeRef(paramTypes[i]);
		}
		return types;
	}
	
	public TypeRef declaringType() {
		return typeRef(_method.getDeclaringClass());
	}

	private TypeRef typeRef(Class type) {
		return new MockTypeRef(type);
	}

	public TypeRef returnType() {
		return typeRef(_method.getReturnType());
	}
	
	public String toString() {
		return name();
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof MethodRef)) {
			return false;
		}
		
		MethodRef other = (MethodRef)obj;
		return name().equals(other.name())
			&& Check.objectsAreEqual(declaringType(), other.declaringType())
			&& Check.objectsAreEqual(returnType(), other.returnType())
			&& Check.arraysAreEqual(paramTypes(), other.paramTypes());
	}	
}
