	/**
	 * Base class for all binary tree nodes. From ODS textbook.
	 * 
	 * @author Pat Morin
	 *
	 * @param <Node>
	 */
	public class XBTNode<Node extends XBTNode<Node>> {
		// Why does this class need to be static? ED
		public Node left;
		public Node right;
		public Node parent;
	}
