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
package com.db4o.internal;

import com.db4o.diagnostic.DefragmentRecommendation.*;
import com.db4o.internal.diagnostic.*;
import com.db4o.internal.slots.*;

/**
 * @exclude
 */
public class DeleteContextImpl extends BufferContext implements DeleteContext {
	
	private final int _handlerVersion;

	public DeleteContextImpl(StatefulBuffer buffer, int handlerVersion){
		super(buffer.getTransaction(), buffer);
		_handlerVersion = handlerVersion;
	}

	public void cascadeDeleteDepth(int depth) {
		((StatefulBuffer)_buffer).setCascadeDeletes(depth);
	}

	public int cascadeDeleteDepth() {
		return ((StatefulBuffer)_buffer).cascadeDeletes();
	}

	public void defragmentRecommended() {
        DiagnosticProcessor dp = container()._handlers._diagnosticProcessor;
        if(dp.enabled()){
            dp.defragmentRecommended(DefragmentRecommendationReason.DELETE_EMBEDED);
        }
	}

	public Slot readSlot() {
		return new Slot(_buffer.readInt(), _buffer.readInt());
	}

	public int handlerVersion() {
		return _handlerVersion;
	}

}
