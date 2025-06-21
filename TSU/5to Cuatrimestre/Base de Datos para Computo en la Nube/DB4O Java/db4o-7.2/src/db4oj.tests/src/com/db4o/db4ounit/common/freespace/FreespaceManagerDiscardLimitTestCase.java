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
package com.db4o.db4ounit.common.freespace;

import com.db4o.config.*;
import com.db4o.internal.*;
import com.db4o.internal.freespace.*;
import com.db4o.internal.slots.*;

import db4ounit.*;


public class FreespaceManagerDiscardLimitTestCase extends FreespaceManagerTestCaseBase{
	
	public static void main(String[] args) {
		new FreespaceManagerDiscardLimitTestCase().runSolo();
	}
	
	protected void configure(Configuration config) {
		config.freespace().discardSmallerThan(10 * ((Config4Impl)config).blockSize());
	}
	
	public void testGetSlot(){
		for (int i = 0; i < fm.length; i++) {
			if(fm[i].systemType() == AbstractFreespaceManager.FM_IX){
				continue;
			}
			fm[i].free(new Slot(20,15));
			
			Slot slot = fm[i].getSlot(5);
			assertSlot(new Slot(20,5), slot);
			Assert.areEqual(1, fm[i].slotCount());
			fm[i].free(slot);
			Assert.areEqual(1, fm[i].slotCount());
			
			slot = fm[i].getSlot(6);
			assertSlot(new Slot(20,15), slot);
			Assert.areEqual(0, fm[i].slotCount());
			fm[i].free(slot);
			Assert.areEqual(1, fm[i].slotCount());
			slot = fm[i].getSlot(10);
			assertSlot(new Slot(20,15), slot);
			Assert.areEqual(0, fm[i].slotCount());
		}
	}
	
	

}
