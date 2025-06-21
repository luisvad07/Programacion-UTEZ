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
package com.db4o.db4ounit.jre11.migration;

import db4ounit.ConsoleTestRunner;

public class FloatMigrationTestCase extends MigrationTestCaseBase {
	
	public static class Item extends MigrationItem {
		public Float value;
		
		public Item() {
		}
		
		public Item(String name_, Float value_) {
			super(name_);
			value = value_;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value_) {
			value = (Float) value_;
		}
	}
	
	protected MigrationItem newItem(String name, Object value) {
		return new Item(name, (Float)value);
	}
	
	protected String getDatabaseFileName() {
		return "floats.db4o";
	}
	
	protected Object getMinValue() {
		return new Float(Float.MIN_VALUE);
	}

	protected Object getMaxValue() {
		return new Float(Float.MAX_VALUE-1);
	}

	protected Object getOrdinaryValue() {
		return new Float(41.9999);
	}
	
	protected Object getUpdateValue() {
		return new Float(Float.NaN);
	}
	
	public static void main(String[] args) {
		// reference db4o 5.2 and uncomment the line below
		// if you ever need to regenerate the file
//		new FloatMigrationTestCase().generateFile();
		new ConsoleTestRunner(IntegerMigrationTestCase.class).run();
	}
}
