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
package com.db4o.db4ounit.common.reflect.custom;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.reflect.*;

/**
 * Type information is handled by CustomClassRepository.
 */
public class CustomReflector implements Reflector {
	
	private final Reflector _delegate = Platform4.reflectorForType(Object.class);
	private final CustomClassRepository _classRepository;
	private Reflector _parent;

	public CustomReflector(CustomClassRepository classRepository) {
		classRepository.initialize(this);
		_classRepository = classRepository;
	}

	public ReflectArray array() {
		return _delegate.array();
	}

	public boolean constructorCallsSupported() {
		return false;
	}

	public ReflectClass forClass(Class clazz) {
		return _delegate.forClass(clazz);
	}

	public ReflectClass forName(String className) {
		logMethodCall("forName", className);

		ReflectClass klass = repositoryForName(className);
		if (null != klass) {
			return klass;
		}
		return _delegate.forName(className);
	}

	private ReflectClass repositoryForName(String className) {
		if (_classRepository == null) {
			return null;
		}
		return _classRepository.forName(className);
	}

	public ReflectClass forObject(Object obj) {
		logMethodCall("forObject", obj);

		ReflectClass klass = repositoryForObject(obj);
		if (null != klass) {
			return klass;
		}
		return _delegate.forObject(obj);
	}

	private ReflectClass repositoryForObject(Object obj) {
		if (_classRepository == null) {
			return null;
		}

		if (!(obj instanceof PersistentEntry)) {
			return null;
		}

		PersistentEntry entry = (PersistentEntry) obj;
		return _classRepository.forName(entry.className);
	}

	public boolean isCollection(ReflectClass clazz) {
		return _delegate.isCollection(clazz);
	}

	public void setParent(Reflector reflector) {
		logMethodCall("setParent", reflector);
		_parent = reflector;
		_delegate.setParent(reflector);
	}

	public Object deepClone(Object context) {
		logMethodCall("deepClone", context);
		throw new NotImplementedException();
	}

	public CustomClass defineClass(String className, String[] fieldNames,
			String[] fieldTypes) {
		return _classRepository.defineClass(className, fieldNames, fieldTypes);
	}

	public String toString() {
		return "CustomReflector(" + _classRepository + ")";
	}

	private void logMethodCall(String methodName, Object arg) {
		Logger.logMethodCall(this, methodName, arg);
	}

	public ReflectClass forFieldType(Class type) {
		return _parent.forClass(type);
	}

	public Iterator4 customClasses() {
		return _classRepository.iterator();
	}
}
