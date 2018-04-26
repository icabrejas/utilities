package org.utilities.graph.tree;

import org.junit.Test;
import org.mockito.Mockito;

import junit.framework.TestCase;

public class TreeNodeUtilsTest extends TestCase {

	@Test
	public void test() {
		TreeNode a = Mockito.mock(TreeNode.class);
		TreeNode b = Mockito.mock(TreeNode.class);
		TreeNode c = Mockito.mock(TreeNode.class);
		Mockito.when(a.parent())
				.thenReturn(c);
		TreeNode parent = TreeNodeUtils.parent(a, b);
		assertEquals(c, parent);
	}

}
