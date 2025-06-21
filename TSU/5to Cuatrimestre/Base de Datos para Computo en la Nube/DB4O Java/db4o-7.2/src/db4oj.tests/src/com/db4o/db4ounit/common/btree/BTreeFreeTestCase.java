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
package com.db4o.db4ounit.common.btree;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.slots.*;

import db4ounit.*;


public class BTreeFreeTestCase extends BTreeTestCaseBase {

    private static final int[] VALUES = new int[] { 1, 2, 5, 7, 8, 9, 12 };

    public static void main(String[] args) {
        new BTreeFreeTestCase().runSolo();
    }
    
    public void test(){
        
        add(VALUES);
        
        Iterator4 allSlotIDs = _btree.allNodeIds(systemTrans());
        
        Collection4 allSlots = new Collection4();
        
        while(allSlotIDs.moveNext()){
            int slotID = ((Integer)allSlotIDs.current()).intValue();
            Slot slot = getSlotForID(slotID);
            allSlots.add(slot);
        }
        
        Slot bTreeSlot = getSlotForID(_btree.getID());
        allSlots.add(bTreeSlot);
        
        final LocalObjectContainer container = (LocalObjectContainer)stream();
        
        
        final Collection4 freedSlots = new Collection4();
        
        container.installDebugFreespaceManager(
            new FreespaceManagerForDebug(container, new SlotListener() {
                public void onFree(Slot slot) {
                    freedSlots.add(container.toNonBlockedLength(slot));
                }
        }));
        
        _btree.free(systemTrans());
        
        systemTrans().commit();
        
        Assert.isTrue(freedSlots.containsAll(allSlots.iterator()));
        
    }

    private Slot getSlotForID(int slotID) {
        return fileTransaction().getCurrentSlotOfID(slotID);
    }

	private LocalTransaction fileTransaction() {
		return ((LocalTransaction)trans());
	}

}
