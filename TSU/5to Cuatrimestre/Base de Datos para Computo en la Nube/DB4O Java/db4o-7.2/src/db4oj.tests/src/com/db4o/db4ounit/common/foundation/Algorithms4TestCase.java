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
package com.db4o.db4ounit.common.foundation;

import com.db4o.foundation.*;

import db4ounit.*;

public class Algorithms4TestCase implements TestCase {
	
	public static class QuickSortableIntArray implements QuickSortable4{
		
		private int[] ints;
		
		public QuickSortableIntArray(int[] ints) {
			this.ints = ints;
		}

		public int compare(int leftIndex, int rightIndex) {
			return ints[leftIndex] - ints[rightIndex]; 
		}

		public int size() {
			return ints.length;
		}

		public void swap(int leftIndex, int rightIndex) {
			int temp = ints[leftIndex];
			ints[leftIndex] = ints[rightIndex];
			ints[rightIndex] = temp;
		}
		
		public void assertSorted(){
			for (int i = 0; i < ints.length; i++) {
				Assert.areEqual( i + 1, ints[i]);
			}
		}
	}
	
	public void testUnsorted(){
		int[] ints = new int[]{ 3 , 5, 2 , 1, 4 };
		assertQSort(ints);
	}

	public void testStackUsage(){
		int[] ints = new int[50000];
		for(int i=0;i<ints.length;i++) {
			ints[i]=i+1;
		}
		assertQSort(ints);
	}

	private void assertQSort(int[] ints) {
		QuickSortableIntArray sample = new QuickSortableIntArray(ints);
		Algorithms4.qsort(sample);
		sample.assertSorted();
	}


}
