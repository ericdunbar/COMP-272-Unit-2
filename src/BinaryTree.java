/**
 * From ODS. Re-built from textbook, while reading by ED.
 * 
 * @author Pat Morin
 *
 */
public class BinaryTree<Node extends BinaryTree.BTNode<Node>> {
	// Where does data type Node come from? How is it simply declared in the
	// class statement? ED

	/**
	 * Base class for all binary tree nodes. From ODS textbook.
	 * 
	 * @author Pat Morin
	 *
	 * @param <Node>
	 */
	public static class BTNode<Node extends BTNode<Node>> {
		// Why does this class need to be static? ED
		public Node left;
		public Node right;
		public Node parent;
	}

	Node r; // root node of binary tree
	protected Node nil; // protected is merely a scope limiter, how to make it
						// final? ED

	/**
	 * Determines the depth of any given node: the number of steps on the path
	 * from u to the root. From ODS textbook pg. 135.
	 * 
	 * @param u Node for which depth needs to be determined
	 * @return Depth of node
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}

	/**
	 * Compute the size of a binary tree--the number of nodes in a binary tree.
	 * (implementation details: recursively compute the size of the two
	 * subtrees, starting from the root, u. Sum these subtrees and add one for
	 * the root.) From ODS textbook pg. 136.
	 * 
	 * @param u root node of the tree for which the size is to be computed
	 * @return size of the tree, rooted at Node u
	 */
	public int size(Node u) {
		if (u == nil)
			return 0;
		return 1 + size(u.left) + size(u.right);
	}

	/**
	 * Compute the height of the binary tree starting at node u. (implementation
	 * details: recursively compute the height of u's two subtrees and take the
	 * maximum. From ODS textbook pg. 136.
	 * 
	 * @param u
	 * @return maximum height of a binary tree rooted at node u, -1 if node is
	 *         empty
	 */
	public int height(Node u) {
		if (u == nil)
			return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	/**
	 * Use recursion to visit every node in the binary tree for the sake of
	 * visiting every node. From ODS textbook pg. 136.
	 * 
	 * @param u root node to traverse all nodes
	 */
	public void traverse(Node u) {
		if (u == nil)
			return;
		traverse(u.left);
		traverse(u.right);
	}

	/**
	 * Visit (traverse) every node in the binary tree for the sake of visiting
	 * every node. From ODS textbook pg. 137
	 * 
	 * @author Pat Morin
	 * @author Eric Dunbar
	 * 
	 * @param u root node
	 */
	public void traverseWithIteration(Node u) {
		Node prev = nil;
		Node next;

		while (u != nil) {

			if (prev == u.parent) {
				if (u.left != nil)
					next = u.left;
				else if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} // end (prev == u.parent)

			else if (prev == u.left) {
				if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} // end (prev == u.left)

			else
				next = u.parent;

			prev = u;
			u = next;
		}
	}

	/**
	 * Demonstration of a non-recursive traversal. From ODS.
	 * 
	 * See also
	 * http://www.refactoring.com/catalog/replaceRecursionWithIteration.html
	 */
	public void traverse2() {
		traverseWithIteration(r);
	}

	public int sizeWithIteration(Node u) {
		Node prev = nil;
		Node next;
		int n = 0;

		while (u != nil) {
			if (prev == u.parent) {
				n++;
				if (u.left != nil)
					next = u.left;
				else if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} // end (prev == u.parent)
			else if (prev == u.left) {
				if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else
				next = u.parent;
			prev = u;
			u = next;
		}
		return n;
	}

	public int size2() {
		return sizeWithIteration(r);
	}
}
