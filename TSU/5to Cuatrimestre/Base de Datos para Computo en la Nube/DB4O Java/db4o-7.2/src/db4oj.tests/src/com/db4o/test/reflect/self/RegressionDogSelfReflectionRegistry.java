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
package com.db4o.test.reflect.self;

import java.util.*;

import com.db4o.reflect.self.*;

/* GENERATE */
public class RegressionDogSelfReflectionRegistry extends SelfReflectionRegistry {
	private final static Hashtable CLASSINFO;

	static {
		CLASSINFO = new Hashtable(2);
		CLASSINFO.put(Animal.class, new ClassInfo(true, Object.class,
				new FieldInfo[] { new FieldInfo("_name", String.class, true,
						false, false) }));
		CLASSINFO.put(Dog.class,
				new ClassInfo(false, Animal.class,
						new FieldInfo[] {
								new FieldInfo("_age", Integer.class, true,
										false, false),
								new FieldInfo("_parents", Dog[].class, true,
										false, false), 
								new FieldInfo("_prices", int[].class, true,
										false, false),
				}));
		// FIELDINFO.put(P1Object.class, new FieldInfo[]{});
	}

	public ClassInfo infoFor(Class clazz) {
		return (ClassInfo) CLASSINFO.get(clazz);
	}

	public Object arrayFor(Class clazz, int length) {
		if (Dog.class.isAssignableFrom(clazz)) {
			return new Dog[length];
		}
		if (Animal.class.isAssignableFrom(clazz)) {
			return new Animal[length];
		}
		return super.arrayFor(clazz, length);
	}

	public Class componentType(Class clazz) {
		if (Dog[].class.isAssignableFrom(clazz)) {
			return Dog.class;
		}
		if (Animal[].class.isAssignableFrom(clazz)) {
			return Animal.class;
		}
		return super.componentType(clazz);
	}
}
