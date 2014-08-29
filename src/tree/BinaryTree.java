package tree;

/**
 * 参考: http://cslibrary.stanford.edu/110/BinaryTrees.html
 * 
 */
public class BinaryTree {

	static int tMaxLength = 0; // 最大距离

	void findMaxLength1(Node root) {
		// 递归结束
		if (root == null)
			return;

		//
		// 左树为空
		//
		if (root.left == null)
			root.nMaxLeft = 0;
		//
		// 右树为空
		//
		if (root.right == null)
			root.nMaxRight = 0;
		//
		// 左树不为空
		//
		if (root.left != null) {
			findMaxLength1(root.left);
		}
		//
		// 右树不为空
		//
		if (root.right != null) {
			findMaxLength1(root.right);
		}
		//
		// 求左子树最大距离
		//
		if (root.left != null) {
			int nTempMax = 0;
			if (root.left.nMaxLeft > root.left.nMaxRight)
				nTempMax = root.left.nMaxLeft;
			else
				nTempMax = root.left.nMaxRight;
			root.nMaxLeft = nTempMax + 1;
		}
		//
		// 求右子树最大距离
		//
		if (root.right != null) {
			int nTempMax = 0;
			if (root.right.nMaxLeft > root.right.nMaxRight)
				nTempMax = root.right.nMaxLeft;
			else
				nTempMax = root.right.nMaxRight;
			root.nMaxRight = nTempMax + 1;
		}
		//
		// 更新最大距离
		//
		if ((root.nMaxLeft + root.nMaxRight) > tMaxLength)
			tMaxLength = root.nMaxLeft + root.nMaxRight;
	} // Root node pointer. Will be null for an empty tree.

	private Node root;

	private static class Node {

		Node left;
		Node right;
		int nMaxLeft;
		int nMaxRight;
		int data;

		Node(int data) {
			this.left = null;
			this.right = null;
			this.data = data;
		}
	}

	/**
	 * Creates an empty binary tree -- a null root pointer.
	 */

	public BinaryTree() {
		root = null;
	}

	/**
	 * Inserts the given data into the binary tree.
	 * 
	 * Uses a recursive helper.
	 */

	public void insert(int data) {
		root = insert(root, data);
	}

	/**
	 * Recursive insert -- given a node pointer, recur down and
	 * 
	 * insert the given data into the tree. Returns the new
	 * 
	 * node pointer (the standard way to communicate
	 * 
	 * a changed pointer back to the caller).
	 */

	private Node insert(Node node, int data) {
		if (node == null) {
			node = new Node(data);
		} else {
			if (data <= node.data) {
				node.left = insert(node.left, data);
			} else {
				node.right = insert(node.right, data);
			}
		}
		return node; // in any case, return the new pointer to the caller
	}

	/**
	 * Returns true if the given target is in the binary tree. Uses a recursive
	 * helper.
	 */
	public boolean lookup(int data) {
		return lookup(root, data);
	}

	private boolean lookup(Node node, int data) {
		if (node == null)
			return false;
		if (node.data == data)
			return true;
		else if (node.data > data)
			return lookup(node.left, data);
		else
			return lookup(node.right, data);
	}

	/**
	 * Returns the number of nodes in the tree. Uses a recursive helper that
	 * recurs down the tree and counts the nodes.
	 */
	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		return size(node.left) + 1 + size(node.right);
	}

	/**
	 * 用一个函数判断一棵树是否平衡
	 */
	public boolean isBalanced() {
		return isBalanced(root);
	}

	private boolean isBalanced(Node root) {
		return (maxDepth(root) - minDepth(root) <= 1);
	}

	/**
	 * Returns the max root-to-leaf depth of the tree. Uses a recursive helper
	 * that recurs down to find the max depth.
	 */
	public int maxDepth() {
		return maxDepth(root);
	}

	private int maxDepth(Node node) {
		if (node == null)
			return 0;
		int lDepth = maxDepth(node.left);
		int rDepth = maxDepth(node.right);
		return Math.max(lDepth, rDepth) + 1;
	}

	public int minDepth() {
		return minDepth(root);
	}

	private int minDepth(Node node) {
		if (node == null)
			return 0;
		int lDepth = maxDepth(node.left);
		int rDepth = maxDepth(node.right);
		return Math.min(lDepth, rDepth) + 1;
	}

	/**
	 * Returns the min value in a non-empty binary search tree. Uses a helper
	 * method that iterates to the left to find the min value.
	 */
	public int minValue() {
		return minValue(root);
	}

	private int minValue(Node node) {
		Node current = node;
		while (current.left != null) {
			current = current.left;
		}
		return current.data;
	}

	public int maxValue() {
		return maxValue(root);
	}

	private int maxValue(Node node) {
		Node current = node;
		while (current.right != null) {
			current = current.right;
		}
		return current.data;
	}

	public int getAbsDiff() {
		return getAbsDiff(root);
	}

	public int getAbsDiff(Node node) {
		Node current = node;
		while (current.right != null) {
			current = current.right;
		}
		int rigValue = current.data;

		while (current.left != null) {
			current = current.left;
		}
		int lefValue = current.data;

		return Math.abs(rigValue - lefValue);

	}

	// private int getAbsDiff(Node node) {
	// return Math.abs(maxValue(node) - minValue(root));
	// }

	public void buildTree(int[] data) {
		for (int i = 0; i < data.length; i++) {
			insert(data[i]);
		}
	}

	/**
	 * Prints the node values in the "inorder" order. Uses a recursive helper to
	 * do the traversal.
	 */
	public void printTree() {
		printTree(root);
		System.out.println();
	}

	private void printTree(Node node) {
		if (node == null)
			return;

		// left, node itself, right
		printTree(node.left);
		System.out.print(node.data + "  ");
		printTree(node.right);
	}

	/**
	 * Prints the node values in the "postorder" order. Uses a recursive helper
	 * to do the traversal.
	 */
	public void printPostorder() {
		printPostorder(root);
		System.out.println();
	}

	public void printPostorder(Node node) {
		if (node == null)
			return;

		// first recur on both subtrees
		printPostorder(node.left);
		printPostorder(node.right);

		// then deal with the node
		System.out.print(node.data + "  ");
	}

	/**
	 * Given a tree and a sum, returns true if there is a path from the root
	 * down to a leaf, such that adding up all the values along the path equals
	 * the given sum. Strategy: subtract the node value from the sum when
	 * recurring down, and check to see if the sum is 0 when you run out of
	 * tree.
	 */
	public boolean hasPathSum(int sum) {
		return (hasPathSum(root, sum));
	}

	boolean hasPathSum(Node node, int sum) {
		// return true if we run out of tree and sum==0
		if (node == null) {
			return (sum == 0);
		} else {
			// otherwise check both subtrees
			int subSum = sum - node.data;
			return (hasPathSum(node.left, subSum) || hasPathSum(node.right, subSum));
		}
	}

	/**
	 * Given a binary tree, prints out all of its root-to-leaf paths, one per
	 * line. Uses a recursive helper to do the work.
	 */
	public void printPaths() {
		int[] path = new int[1000];
		printPaths(root, path, 0);
	}

	/**
	 * Recursive printPaths helper -- given a node, and an array containing the
	 * path from the root node up to but not including this node, prints out all
	 * the root-leaf paths.
	 */
	private void printPaths(Node node, int[] path, int pathLen) {
		if (node == null)
			return;

		// append this node to the path array
		path[pathLen] = node.data;
		pathLen++;

		// it's a leaf, so print the path that led to here
		if (node.left == null && node.right == null) {
			printArray(path, pathLen);
		} else {
			// otherwise try both subtrees
			printPaths(node.left, path, pathLen);
			printPaths(node.right, path, pathLen);
		}
	}

	/**
	 * Utility that prints ints from an array on one line.
	 */
	private void printArray(int[] ints, int len) {
		int i;
		for (i = 0; i < len; i++) {
			System.out.print(ints[i] + " ");
		}
		System.out.println();
	}

	/**
	 * Changes the tree into its mirror image.
	 * 
	 * <pre>
	 * 	 So the tree... 
	 * 	       4 
	 * 	      / \ 
	 * 	     2   5 
	 * 	    / \ 
	 * 	   1   3
	 * 
	 * 	 is changed to... 
	 * 	       4 
	 * 	      / \ 
	 * 	     5   2 
	 * 	        / \ 
	 * 	       3   1
	 * </pre>
	 * 
	 * Uses a recursive helper that recurs over the tree, swapping the
	 * left/right pointers.
	 */
	public void mirror() {
		mirror(root);
	}

	// 从下往上,左右交换
	private void mirror(Node node) {
		if (node != null) {
			// do the sub-trees
			mirror(node.left);
			mirror(node.right);

			// swap the left/right pointers
			Node tmp = node.left;
			node.left = node.right;
			node.right = tmp;
		}
	}

	/**
	 * Changes the tree by inserting a duplicate node on each nodes's .left.
	 * 
	 * <pre>
	 * 	 So the tree... 
	 * 	    2 
	 * 	   / \ 
	 * 	  1   3
	 * 
	 * 	 Is changed to... 
	 * 	       2 
	 * 	      / \ 
	 * 	     2   3 
	 * 	    /   / 
	 * 	   1   3 
	 * 	  / 
	 * 	 1
	 * </pre>
	 * 
	 * Uses a recursive helper to recur over the tree and insert the duplicates.
	 */
	public void doubleTree() {
		doubleTree(root);
	}

	private void doubleTree(Node node) {
		Node oldLeft;
		if (node != null) {
			// do the subtrees
			doubleTree(node.left);
			doubleTree(node.right);

			// duplicate this node to its left
			oldLeft = node.left;
			node.left = new Node(node.data);
			node.left.left = oldLeft;

			// Node newNode = new Node(node.data);
			// newNode.left = node.left;
			// node.left = newNode;
		}
	}

	/*
	 * Compares the receiver to another tree to see if they are structurally
	 * identical.
	 */
	public boolean sameTree(BinaryTree other) {
		return (sameTree(root, other.root));
	}

	private boolean sameTree(Node a, Node b) {
		// 1. both empty -> true
		if (a == null && b == null)
			return (true);

		// 2. both non-empty -> compare them
		else if (a != null && b != null) {
			return (a.data == b.data && sameTree(a.left, b.left) && sameTree(a.right, b.right));
		}
		// 3. one empty, one not -> false
		else
			return (false);
	}

	/**
	 * 给定一个有序数组(递增)，写程序构建一棵具有最小高度的二叉树。
	 */
	private static Node addToTree(int arr[], int start, int end) {
		if (end < start)
			return null;
		int mid = start + (end - start) / 2;
		Node parent = new Node(arr[mid]);
		parent.left = addToTree(arr, start, mid - 1);
		parent.right = addToTree(arr, mid + 1, end);
		return parent;
	}

	public static Node createMinimalBST(int array[]) {
		return addToTree(array, 0, array.length - 1);
	}

	public static void main(String[] args) {
		BinaryTree biTree = new BinaryTree();
		biTree.insert(5);
		biTree.insert(6);
		biTree.insert(3);
		biTree.insert(-1);
		// int[] data = { 2, 8, 7, 4 };
		// biTree.buildTree(data);
		biTree.printTree();

		biTree.findMaxLength1(biTree.root);
		System.out.format("递归法:%d\n", tMaxLength);

		System.out.println(biTree.minValue());
		System.out.println(biTree.maxValue());
		System.out.println(biTree.getAbsDiff());

	}

}