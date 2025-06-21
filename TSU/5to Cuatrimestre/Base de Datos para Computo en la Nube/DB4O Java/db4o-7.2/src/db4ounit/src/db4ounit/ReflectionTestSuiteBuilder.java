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

import db4ounit.fixtures.*;

public class ReflectionTestSuiteBuilder implements TestSuiteBuilder {
	
	public static Object getTestSubject(Test test) {
		return ((TestMethod)undecorate(test)).getSubject();
	}

	private static Test undecorate(Test test) {
		while (test instanceof TestDecoration) {
			test = ((TestDecoration)test).test();
		}
		return test;
	}
	
	private Class[] _classes;
	
	public ReflectionTestSuiteBuilder(Class clazz) {
		this(new Class[] { clazz });
	}
	
	public ReflectionTestSuiteBuilder(Class[] classes) {
		if (null == classes) throw new IllegalArgumentException("classes");
		_classes = classes;
	}
	
	public Iterator4 iterator() {
		return Iterators.flatten(
					Iterators.map(
						_classes,
						new Function4() {
							public Object apply(Object arg) {
								return fromClass((Class) arg);
							}
						})
					);
	}	
	
	/**
	 * Can be overriden in inherited classes to inject new fixtures into
	 * the context.
	 * 
	 * @param closure
	 * @return
	 */
	protected Object withContext(Closure4 closure) {
		return closure.run();
	}
	
	protected Iterator4 fromClass(final Class clazz) {
		return (Iterator4)withContext(new Closure4() {
			public Object run() {
				try {
					return new ContextfulIterator(suiteFor(clazz));
				} catch (Exception e) {
					return Iterators.cons(new FailingTest(clazz.getName(), e)).iterator();
				}
			}
		});
	}

	private Iterator4 suiteFor(Class clazz) {
		if(!isApplicable(clazz)) {
			TestPlatform.emitWarning("DISABLED: " + clazz.getName());
			return Iterators.EMPTY_ITERATOR;
		}
		if (TestSuiteBuilder.class.isAssignableFrom(clazz)) {
			return ((TestSuiteBuilder)newInstance(clazz)).iterator();
		}
		if (Test.class.isAssignableFrom(clazz)) {
			return Iterators.iterateSingle(newInstance(clazz));
		}
		validateTestClass(clazz);
		return fromMethods(clazz);
	}

	private void validateTestClass(Class clazz) {
		if (!(TestCase.class.isAssignableFrom(clazz))) {
			throw new IllegalArgumentException("" + clazz + " is not marked as " + TestCase.class);
		}
	}

	protected boolean isApplicable(Class clazz) {
		return clazz != null; // just removing the 'parameter not used' warning
	}
	
	private Iterator4 fromMethods(final Class clazz) {
		return Iterators.map(clazz.getMethods(), new Function4() {
			public Object apply(Object arg) {
				Method method = (Method)arg;
				if (!isTestMethod(method)) {
					emitWarningOnIgnoredTestMethod(clazz, method);
					return Iterators.SKIP;			
				}
				return fromMethod(clazz, method);
			}
		});
	}
	
	private void emitWarningOnIgnoredTestMethod(Class clazz, Method method) {
		if (!startsWithIgnoreCase(method.getName(), "_test")) {
			return;
		}
		TestPlatform.emitWarning("IGNORED: " + createTest(newInstance(clazz), method).label());
	}

	protected boolean isTestMethod(Method method) {
		return hasTestPrefix(method)
			&& TestPlatform.isPublic(method)
			&& !TestPlatform.isStatic(method)
			&& !TestPlatform.hasParameters(method);
	}

	private boolean hasTestPrefix(Method method) {
		return startsWithIgnoreCase(method.getName(), "test");
	}

	protected boolean startsWithIgnoreCase(final String s, final String prefix) {
		return s.toUpperCase().startsWith(prefix.toUpperCase());
	}

	protected Object newInstance(Class clazz) {		
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new TestException(e);
		}
	}
	
	protected Test createTest(Object instance, Method method) {
		return new TestMethod(instance, method);
	}
	
	protected final Test fromMethod(final Class clazz, final Method method) {
		return new ContextfulTest(new TestFactory() {
			public Test newInstance() {
				return createTest(ReflectionTestSuiteBuilder.this.newInstance(clazz), method);
			}
		});
	}
}
