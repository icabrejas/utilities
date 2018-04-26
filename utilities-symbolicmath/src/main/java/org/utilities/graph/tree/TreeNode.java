package org.utilities.graph.tree;

import java.util.List;

public interface TreeNode {

	TreeNode parent();

	List<TreeNode> children();

}
