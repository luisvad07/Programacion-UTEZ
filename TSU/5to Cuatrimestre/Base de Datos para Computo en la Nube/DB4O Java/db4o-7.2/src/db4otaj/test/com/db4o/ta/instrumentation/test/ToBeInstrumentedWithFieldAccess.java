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

public class ToBeInstrumentedWithFieldAccess {

	public int _externallyAccessibleInt;
	
	private int _int;
	
	private int[] _intArray;
	
	private char _char;
	
	private double _double;
	
	private float _float;
	
	private long _long;
	
	private byte _byte;
	
	private volatile byte _volatileByte;
	
	private transient Object _transientField;

	public boolean compareID(ToBeInstrumentedWithFieldAccess other) {
		return _int == other._int;
	}
	
	public void setInt(int value) {
		_int = value;
	}
	
	public void setChar(char value) {
		_char = value;
	}
	
	public void setByte(byte value) {
		_byte = value;
	}
	
	public void setVolatileByte(byte value) {
		_volatileByte = value;
	}
	
	public void setLong(long value) {
		_long = value;
	}
	
	public void setFloat(float value) {
		_float = value;
	}
	
	public void setDouble(double value) {
		_double = value;
	}
	
	public void setIntArray(int[] value) {
		_intArray = value;
	}
	
	public int setDoubledAndGetInt(int value) {
		_int = value*2; // arbitrarily long expressions
		return _int;
	}
	
	public void wontBeInstrumented() {
		_transientField = null;
	}
}
