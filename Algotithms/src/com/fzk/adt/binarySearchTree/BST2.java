package com.fzk.adt.binarySearchTree;

import com.fzk.adt.LinkedQueue;
import com.fzk.adt.Queue;

/**
 * 二叉查找树数据结构
 * 节点中仅存储左右子节点
 * 
 * @author fanzhoukai
 *
 */
public class BST2<K extends Comparable<K>, V> {
	private Node root; // 根节点

	/**
	 * 构造一个空的二叉查找树
	 */
	public BST2() {
	}

	/**
	 * 获取树中元素个数
	 * 
	 * @return 树中元素个数
	 */
	public int size() {
		return size(root);
	}

	// 获取指定节点下的元素个数，包括节点自身
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
	 * 插入一个键值对
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void put(K key, V value) {
		if (root == null) {
			root = new Node(key, value, null, null, 1);
			return;
		}
		Node pNode = root; // 记录要插入到哪个节点下面，即新节点的父节点
		int comp; // 记录新节点要插入到pNode的左边还是右边
		while (true) {
			comp = key.compareTo(pNode.key);
			if (comp == 0) { // 键与当前节点相同，直接替换值
				pNode.value = value;
				return;
			}
			pNode.N++;
			Node next = null; // 记录下一个值
			if (comp < 0) // 当前节点小于父节点
				next = pNode.left;
			else // 当前节点大于父节点
				next = pNode.right;
			if (next == null)
				break;
			pNode = next;
		}
		Node newNode = new Node(key, value, null, null, 1);
		if (comp < 0)
			pNode.left = newNode;
		else
			pNode.right = newNode;
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

	/**
	 * 删除指定键及关联的值
	 * 
	 * @param key 要删除的键
	 */
	public void delete(K key) {
		root = delete(root, key);
	}

	// 删除指定节点下的指定键值对
	private Node delete(Node node, K key) {
		if (node == null)
			return null;
		int comp = key.compareTo(node.key);
		if (comp < 0)
			node.left = delete(node.left, key);
		else if (comp > 0)
			node.right = delete(node.right, key);
		else {
			if (node.right == null)
				return node.left;
			if (node.left == null)
				return node.right;
			Node t = node;
			node = min(t.right);
			node.right = deleteMin(t.right);
			node.left = t.left;
		}
		node.N = size(node.left) + size(node.right) + 1;
		return node;
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
	 * 删除最小的键及关联的值
	 */
	public void deleteMin() {
		root = deleteMin(root);
	}

	// 删除指定节点下的最小键
	private Node deleteMin(Node node) {
		if (node == null) // 处理根节点为空的情况
			return null;
		if (node.left == null)
			return node.right;
		node.left = deleteMin(node.left);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * 删除最大的键及关联的值
	 */
	public void deleteMax() {
		root = deleteMax(root);
	}

	// 删除指定节点下的最大键
	private Node deleteMax(Node node) {
		if (node == null) // 处理根节点为空的情况
			return null;
		if (node.right == null)
			return node.right;
		node.right = deleteMax(node.right);
		node.N = size(node.left) + size(node.right) + 1;
		return node;
	}

	/**
	 * 获取最大的键
	 * 
	 * @return 最大的键
	 */
	public K max() {
		Node max = root;
		if (max == null)
			return null;
		while (max.right != null)
			max = max.right;
		return max.key;
	}

	/**
	 * 获取最小的键
	 * 
	 * @return 最小的键
	 */
	public K min() {
		Node min = root;
		if (min == null)
			return null;
		while (min.left != null)
			min = min.left;
		return min.key;
	}

	/**
	 * 将键向下取整，即取小于等于指定键的最大的键
	 * 
	 * @param key 指定的键
	 * @return 指定键向下取整的结果
	 */
	public K floor(K key) {
		Node node = floor(root, key);
		return node == null ? null : node.key;
	}

	// 在指定树中向下取整
	private Node floor(Node node, K key) {
		if (node == null)
			return null;
		int comp = key.compareTo(node.key);
		if (comp == 0)
			return node;
		if (comp < 0)
			return floor(node.left, key);
		Node t = floor(node.right, key);
		return t == null ? node : t;
	}

	/**
	 * 将键向上取整
	 * 
	 * @param key 指定的键
	 * @return 指定键向下取整的结果
	 */
	public K ceiling(K key) {
		Node node = ceiling(root, key);
		return node == null ? null : node.key;
	}

	// 在指定树中向上取整
	private Node ceiling(Node node, K key) {
		if (node == null)
			return null;
		int comp = key.compareTo(node.key);
		if (comp == 0)
			return node;
		if (comp > 0)
			return ceiling(node.right, key);
		Node t = ceiling(node.left, key);
		return t == null ? node : t;
	}

	/**
	 * 获取排序为k的键
	 * 
	 * @param k 要获取键的排序数
	 * @return 排序为k的键
	 */
	public K select(int k) {
		Node node = select(root, k);
		return node == null ? null : node.key;
	}

	// 在指定树中找出排序为k的节点
	private Node select(Node node, int k) {
		if (node == null)
			return null;
		int leftSize = size(node.left);
		if (leftSize > k)
			return select(node.left, k);
		else if (leftSize < k)
			return select(node.right, k - leftSize - 1);
		return node;
	}

	/**
	 * 获取指定键的排序数
	 * 
	 * @param key 要排序的键
	 * @return 键的排序数
	 */
	public int rank(K key){
		return rank(root, key);
	}

	// 在制定树中计算键key的排序
	private int rank(Node node, K key) {
		if (node == null)
			return 0;
		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			return rank(node.left, key);
		else if (cmp > 0)
			return size(node.left) + rank(node.right, key) + 1;
		return size(node.left);
	}

	/**
	 * 获取所有键的可迭代对象
	 * 中序遍历——键顺序从小到大
	 * 
	 * @return 所有键的可迭代对象
	 */
	public Iterable<K> keys() {
		return keys(min(), max());
	}

	/**
	 * 获取指定范围的键的可迭代对象
	 * 
	 * @param min 最小键值，包括
	 * @param max 最大键值，包括
	 * @return 在最小最大之间的键值的可迭代对象
	 */
	public Iterable<K> keys(K min, K max) {
		Queue<K> queue = new LinkedQueue<K>();
		inorderTranversal(root, queue, min, max);
		return queue;
	}

	// 修改的中序遍历，可判断遍历范围
	private void inorderTranversal(Node node, Queue<K> queue, K min, K max) {
		if (node == null)
			return;
		int compMin = min.compareTo(node.key);
		int compMax = max.compareTo(node.key);
		if (compMin < 0)
			inorderTranversal(node.left, queue, min, max);
		if (compMin <= 0 && compMax >= 0)
			queue.offer(node.key);
		if (compMax > 0)
			inorderTranversal(node.right, queue, min, max);
	}

	/**
	 * 二叉查找树节点类
	 */
	private class Node {
		private K key;
		private V value;
		private Node left;
		private Node right;
		private int N;

		public Node(K key, V value, Node left, Node right, int N) {
			super();
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.N = N;
		}
	}
}
