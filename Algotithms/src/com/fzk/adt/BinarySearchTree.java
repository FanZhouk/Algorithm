package com.fzk.adt;

/**
 * 二叉查找树数据结构（类似于java.util.TreeMap<K, V>）
 * 
 * @author fanzhoukai
 * 
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

	private Node root; // 根节点

	private int size = 0; // 数据容量

	/**
	 * 创建一个新的二叉查找树
	 */
	public BinarySearchTree() {
	}

	/**
	 * 获取数据容量
	 */
	public int size() {
		return size;
	}

	/**
	 * 判断树是否为空
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 判断是否包含指定键
	 */
	public boolean containsKey(K key) {
		return getNode(key) != null;
	}

	/**
	 * 判断是否包含指定值
	 */
	public boolean containsValue(V value) {
		for (Node n = firstNode(); n != null; n = successor(n))
			if (equals(n.value, value))
				return true;
		return false;
	}

	/**
	 * 插入一个键值对
	 */
	public V put(K key, V value) {
		if (root == null) {
			root = new Node(key, value, null, null, null);
			size = 1;
			return null;
		}

		Node parent = root;
		Node t = parent;
		int comp;
		do {
			parent = t;
			comp = key.compareTo(t.key);
			if (comp < 0)
				t = t.left;
			else if (comp > 0)
				t = t.right;
			else {
				V oldValue = t.value;
				t.value = value;
				return oldValue;
			}
		} while (t != null);

		Node n = new Node(key, value, null, null, parent);
		if (comp < 0)
			parent.left = n;
		else
			parent.right = n;
		size++;
		return null;
	}

	/**
	 * 根据键获取对应的值
	 */
	public V get(K key) {
		Node n = getNode(key);
		return n == null ? null : n.value;
	}

	// 根据键获取对应的节点
	private Node getNode(K key) {
		Node n = root;
		while (n != null) {
			int comp = key.compareTo(n.key);
			if (comp < 0)
				n = n.left;
			else if (comp > 0)
				n = n.right;
			else
				return n;
		}
		return null;
	}

	/**
	 * 获取最小的键
	 */
	public K firstKey() {
		if (root == null)
			return null;
		return firstNode().key;
	}

	/**
	 * 获取最大的键
	 */
	public K lastKey() {
		if (root == null)
			return null;
		return lastNode().key;
	}

	/**
	 * 键向上取整
	 */
	public K ceilingKey(K key) {
		// TODO
		return null;
	}

	/**
	 * 键向下取整
	 */
	public K floorKey(K key) {
		//TODO
		return null;
	}
	
	/**
	 * 根据键删除一个键值对
	 */
	public V delete(K key) {
		if (root == null)
			return null;
		Node n = getNode(key);
		if (n == null)
			return null;

		V oldValue = n.value;
		if (n.left != null) {
			Node ex = predecessor(n); // 前趋不为空
			if (ex == ex.parent.left)
				ex.parent.left = null;
			else
				ex.parent.right = null;

		} else if (n.right != null) {
			Node ex = successor(n); // 后继不为空
			//TODO
		} else { // 是叶子节点
			if (n == n.parent.left)
				n.parent.left = null;
			else
				n.parent.right = null;
		}
		return oldValue;
	}

	/**
	 * 清空集合
	 */
	public void clear() {
		size = 0;
		root = null;
	}

	// 获取指定节点的后继节点
	private Node successor(Node node) {
		if (node == null || node.right == null)
			return null;
		Node n = node.right;
		while (n.left != null)
			n = n.left;
		return n;
	}

	// 获取指定节点的前趋节点
	private Node predecessor(Node node) {
		if (node == null || node.left == null)
			return null;
		Node n = node.left;
		while (n.right != null)
			n = n.right;
		return n;
	}

	// 获取首个节点
	private Node firstNode() {
		Node n = root;
		if (n != null)
			while (n.left != null)
				n = n.left;
		return n;
	}

	// 获取末尾节点
	private Node lastNode() {
		Node n = root;
		if (n != null)
			while (n.right != null)
				n = n.right;
		return n;
	}

	// 判断两个值是否相等（仅用于value的比较）
	public boolean equals(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * 二叉查找树节点
	 */
	private class Node {
		K key;
		V value;
		Node left;
		Node right;
		Node parent;

		public Node(K key, V value, Node left, Node right, Node parent) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

}
