import java.util.LinkedList;
import java.util.Queue;

/**
 * From ODS. Re-built from textbook, while reading by ED.
 * 
 * @author Pat Morin
 *
 */
public class BinaryTree<Node> {
	// Where does data type Node come from? How is it simply declared in the
	// class statement? ED

	/**
	 * Base class for all binary tree nodes. From ODS textbook.
	 * 
	 * @author Pat Morin
	 *
	 * @param <Node>
	 */
	public static class Node {
		// Why does this class need to be static? ED
		public Node left;
		public Node right;
		public Node parent;
	}

	/**
	 * Used as a mini-factory. From ODS sample code.
	 */
	protected Node sampleNode;

	Node r; // root node of binary tree
	protected Node nil; // protected is merely a scope limiter, how to make it
	// final? ED

	/**
	 * Create a new instance of this class. From ODS sample code.
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new
	 *            node in newNode()
	 * @param nil - a node that will be used in place of null
	 */
	public BinaryTree(Node sampleNode, Node nil) {
		this.sampleNode = sampleNode;
		this.nil = nil;
		r = nil;
	}

	/**
	 * Create a new instance of this class. From ODS sample code.
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new
	 *            node in newNode()
	 */
	public BinaryTree(Node sampleNode) {
		this.sampleNode = sampleNode;
	}

	/**
	 * Allocate a new node for use in this tree. From ODS sample code.
	 * 
	 * @return a new node
	 */
	@SuppressWarnings({ "unchecked" })
	protected Node newNode() {
		try {
			Node u = (Node) sampleNode.getClass().newInstance();
			u.parent = u.left = u.right = nil;
			return u;
		} catch (Exception e) {
			return null;
		}
	}

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
	 * Use recursion to compute the size (number of nodes) of this tree. From
	 * ODS sample code.
	 * 
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}

	/**
	 * Use recursion to compute the size of a binary tree--the number of nodes
	 * in a binary tree. (implementation details: recursively compute the size
	 * of the two subtrees, starting from the root, u. Sum these subtrees and
	 * add one for the root.) From ODS textbook pg. 136.
	 * 
	 * @param u root node of the tree for which the size is to be computed
	 * @return size of the tree, rooted at Node u
	 */
	protected int size(Node u) {
		if (u == nil)
			return 0;
		return 1 + size(u.left) + size(u.right);
	}

	/**
	 * Compute the maximum depth of any node in this tree. From ODS sample code.
	 * 
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}

	/**
	 * Compute the height of the binary tree starting at node u. (TOREMOVE
	 * implementation details: recursively compute the height of u's two
	 * subtrees and take the maximum). From ODS textbook pg. 136.
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
	 * Does the tree contain any nodes? From ODS sample code.
	 * 
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return r == nil;
	}

	/**
	 * Make this tree into an empty tree. From ODS sample code.
	 */
	public void clear() {
		r = nil;
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

	/**
	 * Compute the number of nodes in the tree starting at node u without
	 * recursion. From ODS.
	 * 
	 * @author Pat Morin
	 * @author Eric Dunbar
	 * 
	 * @return size of tree starting at node u
	 */
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

	/**
	 * Compute the number of nodes in this tree without recursion. From ODS.
	 * 
	 * @return
	 */
	public int size2() {
		return sizeWithIteration(r);
	}

	/**
	 * Demonstration of a breadth-first traversal (left, right, left, right,
	 * etc.). From ODS textbook and sample code.
	 */
	public void bfTraverse() {
		Queue<Node> q = new LinkedList<Node>();
		if (r != nil)
			q.add(r);
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (u.left != nil)
				q.add(u.left);
			if (u.right != nil)
				q.add(u.right);
		}
	}

	/**
	 * Find the first node in an in-order traversal. From sample code. Should
	 * this read "Find the first LEAF in an in-order traversal."?
	 * 
	 * @return the first node reported in an in-order traversal
	 */
	public Node firstNode() {
		// TODO what does this do?
		Node w = r;
		if (w == nil)
			return nil;
		while (w.left != nil)
			w = w.left;
		return w;
	}

	public static void main(String[] args) {

		Node nil = new Node();
		BinaryTree<Node> bt = new BinaryTree<Node>(nil);

		Node[] nodes = new Node[10];

		nodes[0] = bt.newNode();
		bt.r = nodes[0];
		for (int i = 1; i < nodes.length; i++) {
			nodes[i] = bt.newNode();
			System.out.println(i + ": " + nodes[i]);
			nodes[i].parent = nodes[i-1];
			nodes[i-1].left = nodes[i];
		}
		bt.r.right = nodes[nodes.length/2];

		System.out.println("Size       " + bt.size());
		System.out.println("Height     " + bt.height());
		System.out.println("First Node " + bt.firstNode());

		System.out.println((BinaryTree) bt);
		System.out.println(bt);
	}
}
