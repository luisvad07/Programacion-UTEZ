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

import com.db4o.internal.*;
import com.db4o.internal.freespace.*;
import com.db4o.internal.slots.*;

import db4ounit.*;
import db4ounit.extensions.fixtures.*;


public abstract class FreespaceManagerTestCaseBase extends FileSizeTestCaseBase implements OptOutCS{
	
	protected FreespaceManager[] fm;
	
	protected void db4oSetupAfterStore() throws Exception {
		LocalObjectContainer container = (LocalObjectContainer) db();
		
		BTreeFreespaceManager btreeFm = new BTreeFreespaceManager(container);
		btreeFm.start(0);
		
		fm = new FreespaceManager[]{
			new RamFreespaceManager(container),
			btreeFm,
		};
	}
	
	protected void clear(FreespaceManager freespaceManager){
		Slot slot = null;
		do{
			slot = freespaceManager.getSlot(1);
		}while(slot != null);
		Assert.areEqual(0, freespaceManager.slotCount());
		Assert.areEqual(0, freespaceManager.totalFreespace());
	}
	
	protected void assertSame(FreespaceManager fm1, FreespaceManager fm2 ){
		Assert.areEqual(fm1.slotCount(), fm2.slotCount());
		Assert.areEqual(fm1.totalFreespace(), fm2.totalFreespace());
	}

	protected void assertSlot(Slot expected, Slot actual){
		Assert.areEqual(expected, actual);
	}
    
    protected void produceSomeFreeSpace() {
        FreespaceManager fm = currentFreespaceManager();
        int length = 300;
        Slot slot = localContainer().getSlot(length);
        ByteArrayBuffer buffer = new ByteArrayBuffer(length);
        localContainer().writeBytes(buffer, slot.address(), 0);
        fm.free(slot);
    }

    protected FreespaceManager currentFreespaceManager() {
        return localContainer().freespaceManager();
    }
    
    public static class Item{
        public int _int; 
    }

     protected void storeSomeItems() {
        for (int i = 0; i < 3; i++) {
            store(new Item());
        }
        db().commit();
    }
    
    protected LocalObjectContainer localContainer() {
        return fixture().fileSession();
    }

}
