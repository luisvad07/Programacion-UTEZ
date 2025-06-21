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


public class ArrayAssert {
	
	public static void contains(long[] array, long expected) {
		if (-1 != indexOf(array, expected)) {
			return;
		}
		Assert.fail("Expecting '" + expected + "'.");
	}
	
    public static void contains(Object[] array, Object[] expected){
        for (int i = 0; i < expected.length; i++) {
            if (-1 == indexOf(array, expected[i])) {
                Assert.fail("Expecting contains '" + expected[i] + "'.");
            }
        }
    }
    
    public static int indexOf(Object[] array, Object expected) {
        for (int i = 0; i < array.length; ++i) {                
            if (expected.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
	
	public static void areEqual(Object[] expected, Object[] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
		Assert.areSame(expected.getClass(), actual.getClass());
	    for (int i = 0; i < expected.length; i++) {
	        Assert.areEqual(expected[i], actual[i], indexMessage(i));
	    }
	}
	
	public static void areEqual(int[][] expected, int[][] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
		Assert.areSame(expected.getClass(), actual.getClass());
	    for (int i = 0; i < expected.length; i++) {
	        areEqual(expected[i], actual[i]);
	    }
	}
	
	public static void areEqual(Object[][] expected, Object[][] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
		Assert.areSame(expected.getClass(), actual.getClass());
	    for (int i = 0; i < expected.length; i++) {
	        areEqual(expected[i], actual[i]);
	    }
	}

	private static String indexMessage(int i) {
		return "expected[" + i + "]";
	}

	public static void areEqual(byte[] expected, byte[] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
	    for (int i = 0; i < expected.length; i++) {
	        Assert.areEqual(expected[i], actual[i], indexMessage(i));
	    }
	}

	public static void areNotEqual(byte[] expected, byte[] actual) {
		Assert.areNotSame(expected, actual);		
		for (int i = 0; i < expected.length; i++) {
	        if (expected[i] != actual[i]) return;
	    }
		Assert.isTrue(false);
	}

	public static void areEqual(int[] expected, int[] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
	    for (int i = 0; i < expected.length; i++) {
	        Assert.areEqual(expected[i], actual[i], indexMessage(i));
	    }
	}

	public static void areEqual(double[] expected, double[] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
	    for (int i = 0; i < expected.length; i++) {
	        Assert.areEqual(expected[i], actual[i], indexMessage(i));
	    }
	}

	public static void areEqual(char[] expected, char[] actual) {
		if (expected == actual) return;
		if (expected == null || actual == null) Assert.areSame(expected, actual);
		Assert.areEqual(expected.length, actual.length);
	    for (int i = 0; i < expected.length; i++) {
	        Assert.areEqual(expected[i], actual[i], indexMessage(i));
	    }
	}
	
	private static int indexOf(long[] array, long expected) {
		for (int i = 0; i < array.length; ++i) {				
			if (expected == array[i]) {
				return i;
			}
		}
		return -1;
	}
}
