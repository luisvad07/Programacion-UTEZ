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
package com.db4o.nativequery.expr.cmp.operand;


public class ConstValue implements ComparisonOperand {
	
	private Object _value;
	
	public ConstValue(Object value) {
		this._value = value;
	}
	
	public Object value() {
		return _value;
	}
	
	public void value(Object value) {
		_value = value;
	}
	
	public String toString() {
		if (_value == null) return "null";
		if (_value instanceof String) return "\"" + _value + "\"";
		return _value.toString();
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other==null || getClass() != other.getClass()) {
			return false;
		}
		Object otherValue = ((ConstValue) other)._value;
		if (otherValue == _value) {
			return true;
		}
		if (otherValue == null || _value == null) {
			return false;
		}
		return _value.equals(otherValue);
	}
	
	public int hashCode() {
		return _value.hashCode();
	}

	public void accept(ComparisonOperandVisitor visitor) {
		visitor.visit(this);
	}
}
