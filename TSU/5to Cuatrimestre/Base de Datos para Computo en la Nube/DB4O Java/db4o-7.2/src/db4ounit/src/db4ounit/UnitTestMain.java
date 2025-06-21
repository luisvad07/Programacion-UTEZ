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
package db4ounit;

import java.lang.reflect.*;

import com.db4o.foundation.*;

/**
 * @sharpen.ignore
 */
public class UnitTestMain {
	
	public static void main(String[] args) throws Exception {
		new UnitTestMain().runTests(args);
	}

	public final void runTests(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		new ConsoleTestRunner(build(args), false).run();
	}
	
	protected TestSuiteBuilder builder(Class clazz) {
		return new ReflectionTestSuiteBuilder(clazz);
	}

	private Iterable4 build(String[] args)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		
		return Iterators.concatMap(Iterators.iterable(args), new Function4() {
			public Object apply(Object arg) {
				String testIdentifier = (String)arg;
				try {
					int methodSeparatorIndex = testIdentifier.indexOf('#');
					if (methodSeparatorIndex>0) {
						String className=testIdentifier.substring(0,methodSeparatorIndex);
						String methodName=testIdentifier.substring(methodSeparatorIndex+1);
						return Iterators.cons(testMethod(className, methodName));
					}
					
					return builder(Class.forName(testIdentifier));
					
				} catch (Exception x) {
					return new FailingTest(testIdentifier, x);
				}
			}
		});
	}
	
	protected Test testMethod(String className, String methodName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class clazz = Class.forName(className);
		return new TestMethod(clazz.newInstance(), findMethod(clazz, methodName));
	}

	private Method findMethod(final Class clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new IllegalArgumentException("Method '" + methodName + "' not found in class '" + clazz + "'.");
	}
}
