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
package db4ounit.fixtures;

import com.db4o.foundation.*;

import db4ounit.*;

/**
 * TODO: experiment with ParallelTestRunner that uses a thread pool to run tests in parallel
 * 
 * TODO: FixtureProviders must accept the index of a specific fixture to run with (to make it easy to reproduce a failure)
 * 
 */
public abstract class FixtureBasedTestSuite implements TestSuiteBuilder {

	public abstract Class[] testUnits();

	public abstract FixtureProvider[] fixtureProviders();

	public Iterator4 iterator() {
		final FixtureProvider[] providers = fixtureProviders();
		
		final Iterable4 decorators = Iterators.map(Iterators.iterable(providers), new Function4() {
			public Object apply(final Object arg) {
				final FixtureProvider provider = (FixtureProvider)arg;
				return Iterators.map(Iterators.enumerate(provider), new Function4() {
					public Object apply(final Object arg) {
						EnumerateIterator.Tuple tuple = (EnumerateIterator.Tuple)arg;
						return new FixtureDecorator(provider.variable(), tuple.value, tuple.index);
					}
				});
			}
		});
		final Iterable4 testsXdecorators = Iterators.crossProduct(new Iterable4[] {
			tests(),
			Iterators.crossProduct(decorators)
		});
		return Iterators.map(testsXdecorators, new Function4() {
			public Object apply(Object arg) {
				Iterator4 tuple = ((Iterable4)arg).iterator();
				Test test = (Test)Iterators.next(tuple);
				Iterable4 decorators = (Iterable4)Iterators.next(tuple);
				return decorate(test, decorators.iterator());
			}
		}).iterator();
	}

	private Iterable4 tests() {
		return new ReflectionTestSuiteBuilder(testUnits());
	}
	
	private Test decorate(Test test, Iterator4 decorators) {
		while (decorators.moveNext()) {
			test = ((TestDecorator)decorators.current()).decorate(test);
		}
		return test;
	}

}