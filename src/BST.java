import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}
 
	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		if (bst == null) {
			root = null;
		} else {
			copyHelper(bst.root);
		}
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if (node == null) {
			return;
		}
		insert(node.data);
		copyHelper(node.left);
		copyHelper(node.right);
	}

	/**
	 * Creates a BST of minimal height from an array of values
	 * 
	 * @param array the list of values to insert
	 * @precondition array must be sorted in ascending order
	 * @throws IllegalArgumentException when the array is unsorted
	 */
	public BST(T[] array) throws IllegalArgumentException {
		if (array == null) {
			root = null;
			return;
		}
		if (!isSorted(array)) {
			throw new IllegalArgumentException();
		}

		root = arrayHelper(0, array.length - 1, array);

	}

	/**
	 * Private helper method for array constructor to check for a sorted array
	 * 
	 * @param array the array to check
	 * @return whether the array is sorted
	 */
	private boolean isSorted(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Recursive helper for the array constructor
	 * 
	 * @param begin beginning array index
	 * @param end   ending array index
	 * @param array array to search
	 * @return the newly created Node
	 */
	private Node arrayHelper(int begin, int end, T[] array) {
		if (begin > end) {
			return null;
		}

		int mid = (begin + end) / 2;

		Node newRoot = new Node(array[mid]);

		newRoot.left = arrayHelper(begin, mid - 1, array);
		newRoot.right = arrayHelper(mid + 1, end, array);

		return newRoot;
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getRoot: tree is empty");
		}
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		}
		return 1 + getSize(node.left) + getSize(node.right);
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		}
		int leftH = getHeight(node.left);
		int rightH = getHeight(node.right);

		return leftH > rightH ? leftH + 1 : rightH + 1;
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMind(): tree cannot be empty");
		}

		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left != null) {
			return findMin(node.left);
		}
		return node.data;
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMind(): tree cannot be empty");
		}
		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right != null) {
			return findMax(node.right);
		}
		return node.data;
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 */
	public void remove(T data) {
		root = remove(data, root);
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {

		if (node == null) { // base case
			return node;
		}

		if (data.compareTo(node.data) < 0) { // smaller than node.data

			// recursively move to left
			node.left = remove(data, node.left);

		} else if (data.compareTo(node.data) > 0) { // bigger than node.data

			// recursively move to right
			node.right = remove(data, node.right);

		} else { // found the data in the BST

			if (node.left == null && node.right == null) { // if it's a leaf, remove directly
				node = null;
			} else if (node.left != null && node.right == null) {
				node = node.left;
			} else if(node.left == null && node.right != null) {
				node = node.right;
			} else { // have both left and right subtree

				// replace the node with biggest value from the right subtree
				node.data = findMin(node.right);
				// remove the biggest value
				node.right = remove(node.data, node.right);

			}
		}

		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if(root == null) {
            return false;
        } else {
            return search(data, root);
        }
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {

		if (data.compareTo(node.data) == 0) {
			return true;
		} else if (data.compareTo(node.data) < 0) { // search left subtree

			if (node.left == null) {
				return false;
			}
			
			return search(data, node.left);

		} else { // search right subtree

			if (node.right == null) {
				return false;
			}
			return search(data, node.right);
		}

	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String preOrderString() {

		StringBuilder preOrder = new StringBuilder();
		preOrderString(root, preOrder);
		return preOrder.append("\n").toString();

	}

	/**
	 * Helper method to preOrderString Inserts the data in pre order into a String
	 * 
	 * @param node     the current Node
	 * @param preOrder a String containing the data
	 */
	private void preOrderString(Node node, StringBuilder preOrder) {
		if (node == null) {
			return;
		}

		preOrder.append(node.data).append(" "); // root
		preOrderString(node.left, preOrder); // left
		preOrderString(node.right, preOrder); // right
	}

	/**
	 * Returns a String containing the data in order
	 * 
	 * @return a String of data in order
	 */
	public String inOrderString() {
		StringBuilder inOrder = new StringBuilder();
		inOrderString(root, inOrder);
		return inOrder.append("\n").toString();
	}

	/**
	 * Helper method to inOrderString Inserts the data in order into a String
	 * 
	 * @param node    the current Node
	 * @param inOrder a String containing the data
	 */
	private void inOrderString(Node node, StringBuilder inOrder) {
		if (node == null) {
			return;
		}

		inOrderString(node.left, inOrder); // left
		inOrder.append(node.data).append(" "); // root
		inOrderString(node.right, inOrder); // right
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String postOrderString() {
		StringBuilder postOrder = new StringBuilder();
		postOrderString(root, postOrder);
		return postOrder.append("\n").toString();
	}

	/**
	 * Helper method to postOrderString Inserts the data in post order into a String
	 * 
	 * @param node      the current Node
	 * @param postOrder a String containing the data
	 */
	private void postOrderString(Node node, StringBuilder postOrder) {
		if (node == null) {
			return;
		}
		postOrderString(node.left, postOrder); // left
		postOrderString(node.right, postOrder); // right
		postOrder.append(node.data).append(" "); // root
	}

}
