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

/**
 * @sharpen.partial
 */
public final class Assert {
	
	public static Throwable expect(Class exception, CodeBlock block) {
		Throwable e = getThrowable(block);
		assertThrowable(exception, e);
		return e;
	}

	public static Throwable expect(Class exception, Class cause, CodeBlock block) {
		Throwable e = getThrowable(block);
		assertThrowable(exception, e);
		assertThrowable(cause, TestPlatform.getExceptionCause(e));
		return e;
	}
	
	private static void assertThrowable(Class exception, Throwable e) {
		if(e == null) {
			fail("Exception '" + exception.getName() + "' expected");
		} 
		if (exception.isInstance(e)) 
			return;
		fail("Expecting '" + exception.getName() + "' but got '" + e.getClass().getName() + "'", e);
	}
	
	private static Throwable getThrowable(CodeBlock block) {
		try {
			block.run();
		} catch (Throwable e) {
			return e;
		}
		return null;
	}
	
	public static void fail() {
		fail("FAILURE");
	}

	public static void fail(String msg) {
		throw new AssertionException(msg);
	}
	
	public static void fail(String msg, Throwable cause) {
		throw new AssertionException(msg, cause);
	}
	
	public static void isTrue(boolean condition) {
		isTrue(condition,"FAILURE");
	}

	public static void isTrue(boolean condition, String msg) {
		if (condition) return;
		fail(msg);
	}
	
	public static void isNull(Object reference) {
		if (reference != null) {
			fail(failureMessage("null", reference));
		}
	}

	public static void isNull(Object reference,String message) {
		if (reference != null) {
			fail(message);
		}
	}

	public static void isNotNull(Object reference) {
		if (reference == null) {
			fail(failureMessage("not null", reference));
		}
	}

	public static void isNotNull(Object reference,String message) {
		if (reference == null) {
			fail(message);
		}
	}

	public static void areEqual(boolean expected, boolean actual) {
		if (expected == actual) return;
		fail(failureMessage(new Boolean(expected), new Boolean(actual)));
	}

	public static void areEqual(int expected, int actual) {
		areEqual(expected,actual,null);
	}
	
	public static void areEqual(int expected, int actual, String message) {
		if (expected == actual) return;
		fail(failureMessage(new Integer(expected), new Integer(actual),message));
	}
	
	public static void areEqual(double expected, double actual) {
		areEqual(expected, actual, null);
	}
	
	public static void areEqual(double expected, double actual, String message) {
		if (expected == actual) return;
		fail(failureMessage(new Double(expected), new Double(actual), message));
	}
	
	public static void areEqual(long expected, long actual) {
		if (expected == actual) return;
		fail(failureMessage(new Long(expected), new Long(actual)));
	}

	public static void areEqual(Object expected, Object actual,String message) {		
		if (Check.objectsAreEqual(expected, actual)) return;
		fail(failureMessage(expected, actual, message));
	}
	
	public static void areEqual(Object expected, Object actual) {		
		areEqual(expected,actual,null);
	}

	public static void areSame(Object expected, Object actual) {
		if (expected == actual) return;
		fail(failureMessage(expected, actual));
	}
	
	public static void areNotSame(Object expected, Object actual) {
		if (expected != actual) return;
		fail("Expecting not '" + expected + "'.");
	}
	
	private static String failureMessage(Object expected, Object actual) {
		return failureMessage(expected,actual,null);
	}

	private static String failureMessage(Object expected, Object actual, String customMessage) {
		return failureMessage(expected, actual, "", customMessage);
	}

	private static String failureMessage(Object expected, Object actual, final String cmpOper, String customMessage) {
		return (customMessage==null ? "" : customMessage+": ")+"Expected " + cmpOper + "'"+ expected + "' but was '" + actual + "'";
	}

	public static void isFalse(boolean condition) {
		isTrue(!condition);
	}
	
	public static void isFalse(boolean condition, String message) {
		isTrue(!condition, message);
	}

	public static void isInstanceOf(Class expectedClass, Object actual) {
		isTrue(expectedClass.isInstance(actual), failureMessage(expectedClass, actual == null ? null : actual.getClass()));
	}

	public static void isGreater(long expected, long actual) {
		if (actual > expected) return;
		fail(failureMessage(new Long(expected), new Long(actual), "greater than ", null));
	}			
	
	public static void isGreaterOrEqual(long expected, long actual) {
		if (actual >= expected) return;
		fail(expected, actual, "greater than or equal to ", null);
	}
	
    public static void isSmaller(long expected, long actual) {
        if (actual < expected) return;
        fail(failureMessage(new Long(expected), new Long(actual), "smaller than ", null));
    }

    public static void isSmallerOrEqual(long expected, long actual) {
        if (actual <= expected) return;
        fail(expected, actual, "smaller than or equal to ", null);
    }
    
	private static void fail(long expected, long actual, final String operator, String customMessage) {
		fail(failureMessage(new Long(expected), new Long(actual), operator, null));
	}

	public static void areNotEqual(long expected, long actual) {
		areNotEqual(expected, actual, null);
	}

	public static void areNotEqual(long expected, long actual, String customMessage) {
		if (actual != expected) return;
		fail(expected, actual, "not equal to ", customMessage);
	}

	public static void areNotEqual(Object notExpected, Object actual) {
		if (!Check.objectsAreEqual(notExpected, actual)) return;
		fail("Expecting not '" + notExpected + "'");
	}

    public static void equalsAndHashcode(Object obj, Object same, Object other) {
        areEqual(obj, obj);
        areEqual(obj, same);
        areNotEqual(obj, other);
        areEqual(obj.hashCode(), same.hashCode());
        areEqual(same, obj);
        areNotEqual(other, obj);
        areNotEqual(obj, null);
    }
}
