package com.fzk.adt.binarySearchTree;

/**
 * 红黑二叉查找树
 * 节点中仅存储左右子节点，使用递归实现插入与删除方法
 * 
 * @author fanzhoukai
 *
 */
public class RedBlackBST<K extends Comparable<K>, V> {

	// 根结点
	private Node root;

	/**
	 * 构造一个新的红黑二叉树
	 */
	public RedBlackBST() {
	}

	/**
	 * 获取树中元素的个数
	 * 
	 * @return 树中元素的个数
	 */
	public int size() {
		return size(root);
	}

	// 获取指定结点中的元素个数
	private int size(Node node) {
		return node == null ? 0 : node.N;
	}

	/**
	 * 判断树是否为空
	 */
	public boolean isEmpty() {
		return size(root) == 0;
	}

	/**
	 * 向红黑树中插入一个键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(K key, V value) {
		root = put(root, key, value);
		root.color = BLACK;
	}

	// 在指定结点下插入一个键值对
	private Node put(Node node, K key, V value) {
		if (node == null)
			return new Node(key, value, 1, RED);
		int comp = key.compareTo(node.key);
		if (comp < 0)
			node.left = put(node.left, key, value);
		else if (comp > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;

		if (isRed(node.right) && !isRed(node.left))
			node = rotateLeft(node);
		if (isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if (isRed(node.left) && isRed(node.right))
			flipColors(node);

		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * 删除最小的键及关联的值
	 */
	public void deleteMin() {
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMin(root);
	}

	// 删除指定结点下的最小键
	private Node deleteMin(Node node) {
		if (node.left == null)
			return null;
		if (!isRed(node.left) && !isRed(node.left.left))
			node = moveRedLeft(node);
		node.left = deleteMin(node.left);
		return balance(node);
	}

	private Node moveRedLeft(Node node) {
		flipColors(node);
		if (isRed(node.right.left)) {
			node.right = rotateRight(node.right);
			node = rotateLeft(node);
		}
		return node;
	}

	private Node balance(Node node) {
		if (isRed(node.right))
			node = rotateLeft(node);
		if (isRed(node.right) && !isRed(node.left))
			node = rotateLeft(node);
		if (isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if (isRed(node.left) && isRed(node.right))
			flipColors(node);

		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * 删除最小的键及关联的值
	 */
	public void deleteMax() {
		if (isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMax(root);
		if (!isEmpty())
			root.color = BLACK;
	}

	// 删除指定结点下的最大键
	private Node deleteMax(Node node) {
		if (isRed(root.left))
			node = rotateRight(node);
		if (node.right == null)
			return null;
		if (!isRed(node.right) && !isRed(node.right.left))
			node = moveRedRight(node);
		node.right = deleteMax(node.right);
		return balance(node);
	}

	private Node moveRedRight(Node node) {
		flipColors(node);
		if (!isRed(node.left.left))
			node = rotateRight(node);
		return node;
	}

	/**
	 * 删除指定键及关联的值
	 * 
	 * @param key 要删除的键
	 */
	public void delete(K key) {
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = delete(root, key);
		if (!isEmpty())
			root.color = BLACK;
	}

	// 删除指定结点下的指定键
	private Node delete(Node node, K key) {
		if (key.compareTo(node.key) < 0) {
			if (!isRed(node.left) && !isRed(node.left.left))
				node = moveRedLeft(node);
			node.left = delete(node.left, key);
		} else {
			if (isRed(node.left))
				node = rotateRight(node);
			if (key.compareTo(node.key) == 0 && node.right == null)
				return null;
			if (!isRed(node.right) && !isRed(node.right.left))
				node = moveRedRight(node);
			if (key.compareTo(node.key) == 0) {
				node.value = get(node.right, min(node.right).key);
				node.key = min(node.right).key;
				node.right = deleteMin(node.right);
			} else {
				node.right = delete(node.right, key);
			}
		}
		return balance(node);
	}

	// 获取指定结点下的指定键对应的值
	private V get(Node node, K key) {
		if (node == null)
			return null;
		int comp = key.compareTo(node.key);
		if (comp < 0)
			return get(node.left, key);
		else if (comp > 0)
			return get(node.right, key);
		return node.value;
	}

	// 计算指定节点下的最小节点
	private Node min(Node node) {
		Node min = node;
		if (min == null)
			return null;
		while (min.left != null)
			min = min.left;
		return min;
	}

	/**
	 * 根据键获取对应的值
	 * 
	 * @param key 键
	 * @return 键对应的值
	 */
	public V get(K key) {
		Node node = root;
		while (node != null) {
			int comp = key.compareTo(node.key);
			if (comp == 0)
				return node.value;
			node = comp < 0 ? node.left : node.right;
		}
		return null;
	}

	// 左旋一个结点，并返回该节点的右节点来替换当前结点
	// 调用方式：h = rotateLeft(h);
	private Node rotateLeft(Node node) {
		Node x = node.right;
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = RED;
		x.N = node.N;
		node.N = size(node.left) + size(node.right) + 1;
		return x;
	}

	// 右旋一个结点，并返回该节点的左节点来替换当前结点
	private Node rotateRight(Node node) {
		Node x = node.left;
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = RED;
		x.N = node.N;
		node.N = size(node.left) + size(node.right) + 1;
		return x;
	}

	// 颜色转换
	private void flipColors(Node node) {
		node.color = RED;
		node.left.color = BLACK;
		node.right.color = BLACK;
	}

	// 红/黑颜色
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	// 判断一个结点是否是红结点
	private boolean isRed(Node node) {
		return node == null ? false : node.color == RED;
	}

	private class Node {
		K key;
		V value;
		Node left;
		Node right;
		int N;
		boolean color;

		Node(K key, V value, int n, boolean color) {
			super();
			this.key = key;
			this.value = value;
			N = n;
			this.color = color;
		}
	}
}
