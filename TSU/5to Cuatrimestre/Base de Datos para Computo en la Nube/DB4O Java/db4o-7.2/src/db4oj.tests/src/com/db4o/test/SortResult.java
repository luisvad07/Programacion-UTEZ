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

public class SortResult {
	private final static int[] ORIG={2,4,1,3};
	
	public int id;
	
	public SortResult() {
		this(0);
	}
	
	public SortResult(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	public String toString() {
		return "<"+id+">";
	}
	
    public void configure(){
        Db4o.configure().optimizeNativeQueries(false);
    }

	public void store() {
		for (int idx = 0; idx < ORIG.length; idx++) {
			SortResult sortResult = new SortResult(ORIG[idx]);
			Test.store(sortResult);
			//System.err.println(sortResult+"/"+Test.objectContainer().ext().getID(sortResult));
		}
	}
	
	public void test() {
		ObjectSet result=Test.objectContainer().queryByExample(SortResult.class);
// FIXME: Why 0 results?
//		ObjectSet result=Test.objectContainer().query(
//				new Predicate() {
//					public boolean match(SortResult candidate) {
//						return true;
//					}
//				});

/*
		result.ext().sort(new QueryComparator() {
			public int compare(Object first, Object second) {
				return ((SortResult)first).id()-((SortResult)second).id();
			}
		});
		Test.ensure(ORIG.length==result.size());
		for(int i=1;i<=ORIG.length;i++) {
			Test.ensure(((SortResult)result.next()).id()==i);
		}
*/
	}
}
