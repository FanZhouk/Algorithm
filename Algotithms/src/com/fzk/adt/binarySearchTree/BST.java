package com.fzk.adt.binarySearchTree;

import com.fzk.adt.LinkedList;
import com.fzk.adt.LinkedQueue;
import com.fzk.adt.LinkedStack;
import com.fzk.adt.List;
import com.fzk.adt.Queue;
import com.fzk.adt.Stack;

/**
 * 二叉查找树数据结构
 * 节点中存储左右子节点与父节点
 * 
 * @author fanzhoukai
 * 
 */
public class BST<K extends Comparable<K>, V> {

	private Node root; // 根节点

	private int size = 0; // 数据容量

	/**
	 * 创建一个新的二叉查找树
	 */
	public BST() {
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
	 * 删除键最小的键值对，并返回值
	 */
	public V removeMin() {
		if (root == null)
			return null;

		Node n = firstNode(); // 要删除的元素
		V oldValue = n.value;

		if (n == root) {
			root = root.right;
			if (root != null)
				root.parent = null;
		} else {
			n.parent.left = n.right;
			if (n.right != null)
				n.right.parent = n.parent;
		}
		size--;
		return oldValue;
	}

	/**
	 * 删除键最大的键值对，并返回值
	 */
	public V removeMax() {
		if (root == null)
			return null;

		Node n = lastNode();
		V oldValue = n.value;

		if (n == root) {
			root = root.left;
			if (root != null)
				root.parent = null;
		} else {
			n.parent.right = n.left;
			if (n.left != null) {
				n.left.parent = n.parent;
			}
		}
		size--;
		return oldValue;
	}

	/**
	 * 根据键删除一个键值对
	 */
	public V remove(K key) {
		Node p = getNode(key);
		if (p == null)
			return null;
		V oldValue = p.value;
		removeNode(p);
		return oldValue;
	}

	// 测试用方法，获取根节点的键值
	public K getRoot() {
		return root == null ? null : root.key;
	}

	// 删除一个节点
	private void removeNode(Node p) {
		size--;

		// p是叶子节点
		if (p.left == null && p.right == null) {
			if (p == root) // p是根节点
				root = null;
			else if (p == p.parent.left) // p是左节点
				p.parent.left = null;
			else // p是右节点
				p.parent.right = null;
			p.parent = null;
			return;
		}

		Node r = p.left != null ? p.left : p.right;
		if (r != null) { // p有且仅有一个子节点
			r.parent = p.parent;
			if (p == root)
				root = r;
			else if (p == p.parent.left)
				p.parent.left = r;
			else
				p.parent.right = r;
			return;
		}

		// p有左右子节点
		Node replacement = successor(p); // p的后继代替p
		p.key = replacement.key;
		p.value = replacement.value;
		removeNode(replacement);
	}

	// 删除一个节点（仿照TreeMap中的方式写）
	private void removeNode2(Node p) {
		size--;

		// 左右子节点全部存在
		if (p.left != null && p.right != null) {
			Node s = successor(p);
			p.key = s.key;
			p.value = s.value;
			p = s;
		}

		// 尽量找一个存在的子节点
		Node replacement = (p.left != null ? p.left : p.right);

		if (replacement != null) { // 左或右子节点至少存在一个
			replacement.parent = p.parent;
			if (p.parent == null)
				root = replacement;
			else if (p == p.parent.left)
				p.parent.left = replacement;
			else
				p.parent.right = replacement;

			p.left = p.right = p.parent = null;

		} else if (p.parent == null) { // 左右节点都没有，而且还是根节点
			root = null;
		} else { // 叶子节点
			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;
			}
		}
	}

	/**
	 * 清空集合
	 */
	public void clear() {
		size = 0;
		root = null;
	}

	/**
	 * 输出广度优先遍历（层次遍历）结果——仅输出键
	 */
	public void printBFS() {
		if (isEmpty()) {
			System.out.println("[]");
			return;
		}
		Queue<Node> queue = new LinkedQueue<Node>();
		queue.offer(root);
		StringBuilder sb = new StringBuilder("[");
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.left != null)
				queue.offer(node.left);
			if (node.right != null)
				queue.offer(node.right);

			sb.append(node.key);
			if(!queue.isEmpty())
				sb.append(", ");
		}
		System.out.println(sb.append("]"));
	}

	/**
	 * 广度优先搜索（仅对键的遍历）
	 * 
	 * @return 所有键的可迭代对象
	 */
	public Iterable<K> bfs() {
		if (isEmpty())
			return new LinkedList<K>();
		List<Node> list = new LinkedList<Node>();
		list.add(root);
		int cursor = 0;
		while (cursor < list.size()) {
			Node parent = list.get(cursor);
			if (parent.left != null)
				list.add(parent.left);
			if (parent.right != null)
				list.add(parent.right);
			cursor++;
		}
		List<K> result = new LinkedList<K>();
		for (Node node : list)
			result.add(node.key);
		return result;
	}

	/**
	 * 输出深度优先遍历（前序遍历）结果——仅输出键
	 */
	public void printDFS() {
		if (isEmpty()) {
			System.out.println("[]");
			return;
		}
		Stack<Node> queue = new LinkedStack<Node>();
		queue.push(root);
		StringBuilder sb = new StringBuilder("[");
		while (!queue.isEmpty()) {
			Node node = queue.pop();
			if (node.right != null)
				queue.push(node.right);
			if (node.left != null)
				queue.push(node.left);

			sb.append(node.key);
			if(!queue.isEmpty())
				sb.append(", ");
		}
		System.out.println(sb.append("]"));
	}

	/**
	 * 深度优先搜索（仅对键的遍历）
	 * 
	 * @return 所有键的可迭代对象
	 */
	public Iterable<K> dfs() {
		if (isEmpty())
			return new LinkedList<K>();
		List<K> result = new LinkedList<K>();
		Stack<Node> stack = new LinkedStack<Node>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			result.add(node.key);
			if (node.right != null)
				stack.push(node.right);
			if (node.left != null)
				stack.push(node.left);
		}
		return result;
	}

	// 获取指定节点的后继节点
	private Node successor(Node node) {
		if (node == null)
			return null;
		if (node.right != null) {
			Node n = node.right;
			while (n.left != null)
				n = n.left;
			return n;
		} else {
			Node n = node;
			while (n.parent != null) {
				if (n == n.parent.left)
					return n.parent;
				else
					n = n.parent;
			}
		}
		return null;
	}

	// 获取指定节点的前趋节点
	private Node predecessor(Node node) {
		if (node == null)
			return null;
		if (node.left != null) { // 左子节点非空，找左子节点的最右边
			Node n = node.left;
			while (n.right != null)
				n = n.right;
			return n;
		} else { // 左子节点为空，向上找
			Node ch = node;
			Node p = ch.parent;
			while (p != null && ch == p.left) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
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
	private boolean equals(Object o1, Object o2) {
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
