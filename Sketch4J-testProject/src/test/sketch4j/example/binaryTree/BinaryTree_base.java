/**
 * @author Lisa Apr 20, 2016 BinaryTree.java 
 */
package test.sketch4j.example.binaryTree;

import java.util.Stack;

public class BinaryTree_base {
	public Node root;
	public int size;
	protected Node _n1_, _n2_;
	public static final int ITER_BOUND = 11;
	protected String toString = "";

	public boolean insert(int k) {
		Node y = null;
		Node x = root;

		while ((x != null)) {
			y = x;

//			if (x.key == k) {
//				return false;
//			}

			if ((x != null) && k < x.key) {
				x = x.left;
			}

			else if ((x != null) && (k > x.key)) {
				x = x.right;
			}
		}

		x = new Node(k);

		if (y == null) {
			root = x;
		} else {
			if (k < y.key) {
				y.left = x;
			} else {
				y.right = x;
			}
		}
		size += 1;
		return true;
	}

	public int[] inorder() {
		int[] result = new int[100]; // assume no more than 100 elements;
		int index = 0;
		Node current, pre;

		if (root == null) {
			return result;
		}

		current = root;
		while (current != null) {
			if (current.left == null) {
				result[index++] = current.key;
				current = current.right;
			} else {
				/* Find the inorder predecessor of current */
				pre = current.left;
				while (pre.right != null && pre.right != current) {
					pre = pre.right;
				}
				/* Make current as right child of its inorder predecessor */
				if (pre.right == null) {
					pre.right = current;
					current = current.left;
				}

				/*
				 * Revert the changes made in if part to restore the original
				 * tree i.e., fix the right child of predecssor
				 */ else {
					pre.right = null;
					result[index++] = current.key;
					current = current.right;
				} /* End of if condition pre->right == NULL */

			} /* End of if condition current->left == NULL */

		} /* End of while */
		return result;
	}

	public int[] preorder() {
		int[] result = new int[100]; // assume no more than 100 elements;
		int index = 0;
		if (root == null)
			return result;

		Stack<Node> stack = new Stack<Node>();
		stack.push(root);

		while (!stack.empty()) {
			Node n = stack.pop();
			result[index++] = n.key;

			if (n.right != null) {
				stack.push(n.right);
			}
			if (n.left != null) {
				stack.push(n.left);
			}
		}
		return result;
	}

	public int[] postorder() {
		int[] result = new int[100]; // assume no more than 100 elements;
		int index = 0;
		Stack<Node> stack = new Stack<Node>();

		// Check for empty tree
		if (root == null) {
			return result;
		}
		stack.push(root);
		Node prev = null;
		while (!stack.isEmpty()) {
			Node current = stack.peek();

			/*
			 * go down the tree in search of a leaf an if so process it and pop
			 * stack otherwise move down
			 */
			if (prev == null || prev.left == current || prev.right == current) {
				if (current.left != null) {
					stack.push(current.left);
				} else if (current.right != null) {
					stack.push(current.right);
				} else {
					stack.pop();
					result[index++] = current.key;
				}

				/*
				 * go up the tree from left node, if the child is right push it
				 * onto stack otherwise process parent and pop stack
				 */
			} else if (current.left == prev) {
				if (current.right != null) {
					stack.push(current.right);
				} else {
					stack.pop();
					result[index++] = current.key;
				}

				/*
				 * go up the tree from right node and after coming back from
				 * right node process parent and pop stack
				 */
			} else if (current.right == prev) {
				stack.pop();
				result[index++] = current.key;
			}

			prev = current;
		}

		return result;
	}

	public String toString() {
		return toString;
	}
}
