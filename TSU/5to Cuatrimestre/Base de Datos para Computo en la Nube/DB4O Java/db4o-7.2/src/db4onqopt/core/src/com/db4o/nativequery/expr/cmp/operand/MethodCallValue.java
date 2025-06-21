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

import com.db4o.foundation.*;
import com.db4o.instrumentation.api.*;


public class MethodCallValue extends ComparisonOperandDescendant {
	private final MethodRef _method;
	private final ComparisonOperand[] _args;
	private final CallingConvention _callingConvention;
	
	public MethodCallValue(MethodRef method, CallingConvention callingConvention, ComparisonOperandAnchor parent, ComparisonOperand[] args) {
		super(parent);
		_method = method;
		_args = args;
		_callingConvention = callingConvention;
	}

	public void accept(ComparisonOperandVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * @sharpen.property
	 */
	public ComparisonOperand[] args() {
		return _args;
	}
	
	public boolean equals(Object obj) {
		if(!super.equals(obj)) {
			return false;
		}
		MethodCallValue casted=(MethodCallValue)obj;
		return _method.equals(casted._method)
			&& _callingConvention == casted._callingConvention;
	}

	public int hashCode() {
		int hc=super.hashCode();
		hc*=29+_method.hashCode();
		hc*=29+_args.hashCode();
		hc*=29+_callingConvention.hashCode();
		return hc;
	}
	
	public String toString() {
		
		return super.toString()
			+ "."
			+ _method.name()
			+ Iterators.join(Iterators.iterate(_args), "(", ")", ", ");
	}
	
	/**
	 * @sharpen.property
	 */
	public MethodRef method() {
		return _method;
	}

	/**
	 * @sharpen.property
	 */
	public CallingConvention callingConvention() {
		return _callingConvention;
	}
}
