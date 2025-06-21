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
package com.db4o.db4ounit.common.handlers;

import com.db4o.config.*;
import com.db4o.diagnostic.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.handlers.*;
import com.db4o.query.*;
import com.db4o.typehandlers.*;
import com.db4o.types.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class StringBufferHandlerTestCase extends AbstractDb4oTestCase {

    public static void main(String[] args) {
        new StringBufferHandlerTestCase().runAll();
    }

    public static class Item implements SecondClass {
        public StringBuffer buffer;

        public Item(StringBuffer contents) {
            buffer = contents;
        }
    }

    static String _bufferValue = "42"; //$NON-NLS-1$

    protected void configure(Configuration config) throws Exception {
        config.exceptionsOnNotStorable(true);
        config.registerTypeHandler(new SingleClassTypeHandlerPredicate(
                StringBuffer.class), new StringBufferHandler());
        config.diagnostic().addListener(new DiagnosticListener() {

            public void onDiagnostic(Diagnostic d) {
                if (d instanceof DeletionFailed)
                    throw new Db4oException();
            }
        });
    }

    protected void store() throws Exception {
        store(new Item(new StringBuffer(_bufferValue)));
    }

    public void testRetrieve() {
        Item item = retrieveItem();
        Assert.areEqual(_bufferValue, item.buffer.toString());
    }

    public void testTopLevelStore() {
        Assert.expect(ObjectNotStorableException.class, new CodeBlock() {
            public void run() throws Throwable {
                store(new StringBuffer("a")); //$NON-NLS-1$
            }
        });
    }

    public void testDelete() {
        Item item = retrieveItem();
        Assert.areEqual(_bufferValue, item.buffer.toString());
        db().delete(item);
        Query query = newQuery();
        query.constrain(Item.class);
        Assert.areEqual(0, query.execute().size());
    }

    public void testPrepareComparison() {
        StringBufferHandler handler = new StringBufferHandler();
        PreparedComparison preparedComparison = handler.prepareComparison(trans().context(), _bufferValue);
        Assert.isGreater(preparedComparison.compareTo("43"), 0); //$NON-NLS-1$
        
    }

    private Item retrieveItem() {
        return (Item) retrieveOnlyInstance(Item.class);
    }

}
