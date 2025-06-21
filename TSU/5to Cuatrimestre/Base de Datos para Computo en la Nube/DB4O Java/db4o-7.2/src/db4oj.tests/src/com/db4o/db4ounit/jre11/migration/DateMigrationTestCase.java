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

import java.util.Date;

import db4ounit.ConsoleTestRunner;

public class DateMigrationTestCase extends MigrationTestCaseBase {
	
	public static class Item extends MigrationItem {
		public Date date;
		
		public Item() {
		}
		
		public Item(String name_, Date date_) {
			super(name_);
			date = date_;
		}
		
		public Object getValue() {
			return date;
		}
		
		public void setValue(Object value) {
			date = (Date)value;
		}
	}
	
	protected MigrationItem newItem(String name, Object value) {
		return new Item(name, (Date)value);
	}
	
	protected Object getMinValue() {
		return new Date(0);
	}

	protected Object getMaxValue() {
		return new Date(Long.MAX_VALUE - 1);
	}

	protected Object getOrdinaryValue() {
		return new Date(1166839200000L);
	}
	
	protected Object getUpdateValue() {
	    return new Date(1296839800000L);
	}

	protected String getDatabaseFileName() {
		return "dates.db4o";
	}
	
	public static void main(String[] args) {
		// reference db4o 5.2 and uncomment the line below
		// if you ever need to regenerate the file
		// new DateMigrationTestCase().generateFile();

		new ConsoleTestRunner(DateMigrationTestCase.class).run();
	}
}
