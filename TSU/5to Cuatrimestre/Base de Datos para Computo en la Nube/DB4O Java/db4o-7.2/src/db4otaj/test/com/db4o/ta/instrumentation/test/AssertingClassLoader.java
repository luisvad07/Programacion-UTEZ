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
package com.db4o.ta.instrumentation.test;

import java.io.*;
import java.net.*;

import com.db4o.test.util.*;

import db4ounit.*;

/**
 * Creates a separate environment to load classes ({@link ExcludingClassLoader}
 * so they can be asserted after instrumentation.
 */
public class AssertingClassLoader {

	private final URLClassLoader _loader;

	public AssertingClassLoader(File classPath, Class[] excludedClasses) throws MalformedURLException {
		ExcludingClassLoader excludingLoader = new ExcludingClassLoader(getClass().getClassLoader(), excludedClasses);		
		_loader = new URLClassLoader(new URL[] { toURL(classPath) }, excludingLoader);
	}

	/**
	 * @deprecated
	 */
	private URL toURL(File classPath) throws MalformedURLException {
		return classPath.toURL();
	}

	public void assertAssignableFrom(Class expected, Class actual) throws ClassNotFoundException {
		if (isAssignableFrom(expected, actual)) {
			return;
		}
		
		fail(expected, actual, "not assignable from");
	}

	public void assertNotAssignableFrom(Class expected, Class actual) throws ClassNotFoundException {
		if (!isAssignableFrom(expected, actual)) {
			return;
		}
		
		fail(expected, actual, "assignable from");
	}
	
	private void fail(Class expected, Class actual, String reason) {
		Assert.fail("'" + actual + "' " + reason + " '" + expected + "'");
	}

	private boolean isAssignableFrom(Class expected, Class actual) throws ClassNotFoundException {
		return expected.isAssignableFrom(loadClass(actual));
	}

	private Class loadClass(Class actual) throws ClassNotFoundException {
		return _loader.loadClass(actual.getName());
	}

	public Object newInstance(Class clazz) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return loadClass(clazz).newInstance();
	}
}
