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
package com.db4o.db4ounit.common.soda.ordered;

import com.db4o.db4ounit.common.soda.util.*;
import com.db4o.query.*;

import db4ounit.extensions.fixtures.*;

/**
 * Tests for COR-1007
 */
public class STOrderingTestCase extends SodaBaseTestCase implements OptOutCS {

    public static void main(String[] args) {
        new STOrderingTestCase().runSolo();
    }

    public Object[] createData() {
        return new Object[] { new OrderTestSubject("Alexandr", 30, 5), // 0
                new OrderTestSubject("Cris", 30, 5), // 1
                new OrderTestSubject("Boris", 30, 5), // 2
                new OrderTestSubject("Helen", 25, 5), // 3
                new OrderTestSubject("Zeus", 25, 3), // 4
                new OrderTestSubject("Alexsandra", 25, 3), // 5
                new OrderTestSubject("Liza", 25, 4), // 6
                new OrderTestSubject("Bred", 25, 3), // 7
                new OrderTestSubject("Liza", 25, 3), // 8
                new OrderTestSubject("Gregory", 25, 4), }; // 9
    }

    public void testFirstAndSecondFieldsAreIrrelevant() {
        Query q = newQuery();
        q.constrain(OrderTestSubject.class);
        q.descend("_seniority").orderAscending();
        q.descend("_age").orderAscending();
        q.descend("_name").orderAscending();

        expectOrdered(q, new int[] { 5, 7, 8, 4, 9, 6, 3, 0, 2, 1 });
    }

    public void testSecondAndThirdFieldsAreIrrelevant() {
        Query q = newQuery();
        q.constrain(OrderTestSubject.class);
        q.descend("_age").orderAscending();
        q.descend("_name").orderAscending();
        q.descend("_seniority").orderAscending();

        expectOrdered(q, new int[] { 5, 7, 9, 3, 8, 6, 4, 0, 2, 1 });
    }

    public void testOrderByNameAscending() {
        Query q = newQuery();
        q.constrain(OrderTestSubject.class);
        q.descend("_name").orderAscending();

        expectOrdered(q, new int[] { 0, 5, 2, 7, 1, 9, 3, 8, 6, 4 });
    }

    public void testOrderByNameAndAgeAscending() {
        Query q = newQuery();
        q.constrain(OrderTestSubject.class);

        q.descend("_age").orderAscending();
        q.descend("_name").orderAscending();

        expectOrdered(q, new int[] { 5, 7, 9, 3, 6, 8, 4, 0, 2, 1 });
    }

    public void testAscendingOrderWithOutAge() {
        Query q = newQuery();
        q.constrain(OrderTestSubject.class);
        q.descend("_seniority").orderAscending();
        q.descend("_name").orderAscending();

        expectOrdered(q, new int[] { 5, 7, 8, 4, 9, 6, 0, 2, 1, 3 });
    }
}
