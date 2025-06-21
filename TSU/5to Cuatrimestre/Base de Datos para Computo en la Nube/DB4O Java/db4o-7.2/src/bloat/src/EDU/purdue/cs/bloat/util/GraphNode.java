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
package EDU.purdue.cs.bloat.util;

import java.util.*;

/**
 * GraphNode represents a node in a Graph. Each node has a set of predacessors
 * and successors associated with it as well as a pre-order and post-order
 * traversal index. This information is maintained by the Graph in which the
 * GraphNode resides.
 * 
 * @see Graph
 */
public abstract class GraphNode {
	protected HashSet succs;

	protected HashSet preds;

	protected int preIndex;

	protected int postIndex;

	/**
	 * Constructor.
	 */
	public GraphNode() {
		succs = new HashSet();
		preds = new HashSet();
		preIndex = -1;
		postIndex = -1;
	}

	/**
	 * @return This node's index in a pre-order traversal of the Graph that
	 *         contains it.
	 */
	int preOrderIndex() {
		return preIndex;
	}

	/**
	 * @return This node's index in a post-order traversal of the Graph that
	 *         contains it.
	 */
	int postOrderIndex() {
		return postIndex;
	}

	void setPreOrderIndex(final int index) {
		preIndex = index;
	}

	void setPostOrderIndex(final int index) {
		postIndex = index;
	}

	/**
	 * Returns the successor (or children) nodes of this GraphNode.
	 */
	protected Collection succs() {
		return succs;
	}

	/**
	 * Returns the predacessor (or parent) nodes of this GraphNode.
	 */
	protected Collection preds() {
		return preds;
	}
}
