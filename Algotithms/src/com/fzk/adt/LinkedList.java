package com.fzk.adt;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * 双向循环链表数据结构
 * 
 * @author fanzhoukai
 * 
 */
public class LinkedList<E> implements List<E>, Stack<E>, Iterable<E> {

	// 虚拟头结点
	// header的value永远为空
	// header的next域是集合第一个元素，header的previous域是集合的最后一个元素
	private Node<E> header = new Node<E>(null, null, null);

	// 链表的实际长度
	private int size = 0;

	/**
	 * 创建一个空链表
	 */
	public LinkedList() {
		header.next = header.previous = header;
	}

	// ***************************************** 查询相关方法 *****************************************

	/**
	 * 获取集合实际长度
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 判断集合是否为空
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 判断是否包含指定元素
	 */
	@Override
	public boolean contains(Object o) {
		for (Node<E> n = header.next; n != header; n = n.next)
			if ((n.value == null && o == null) || n.value.equals(o))
				return true;
		return false;
	}

	/**
	 * 获取正序迭代器
	 */
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/**
	 * 正序迭代器
	 * 
	 * @author fanzhoukai
	 * 
	 */
	private class Itr implements Iterator<E> {

		// 当前访问的到的节点
		Node<E> current;

		Itr() {
			current = header;
		}

		@Override
		public boolean hasNext() {
			return current.next != header;
		}

		@Override
		public E next() {
			current = current.next;
			return current.value;
		}

		@Override
		public void remove() {
			removeNode(current);
			// current = current.next;
		}
	}

	/**
	 * 转为Object数组
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[size];
		int cursor = 0;
		for (Node<E> n = header.next; n != header; n = n.next)
			arr[cursor++] = n.value;
		return arr;
	}

	/**
	 * 转为指定类型的数组
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	// ***************************************** 修改相关方法 *****************************************
	/**
	 * 向链表末尾添加一个元素
	 */
	@Override
	public boolean add(E e) {
		addBefore(header, e);
		return true;
	}

	/**
	 * 在指定节点前面添加一个元素
	 */
	private Node<E> addBefore(Node<E> node, E e) {
		Node<E> newNode = new Node<E>(e, node.previous, node);
		node.previous.next = node.previous = newNode;
		size++;
		return newNode;
	}

	/**
	 * 移除第一次出现的指定元素
	 */
	@Override
	public boolean remove(Object o) {
		for (Node<E> n = header.next; n != header; n = n.next) {
			if ((n.value == null && o == null) || n.value.equals(o)) { // 找到相等的元素，直接移除
				removeNode(n);
				return true;
			}
		}
		return false;
	}

	/**
	 * 移除一个节点
	 */
	private E removeNode(Node<E> node) {
		E oldValue = node.value;
		node.previous.next = node.next;
		node.next.previous = node.previous;
		size--;
		node = null;
		return oldValue;
	}

	// **************************************** 批量修改相关方法 ****************************************
	/**
	 * 判断是否包含指定集合的全部元素
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<?> it = c.iterator();
		while (it.hasNext())
			if (!contains(it.next()))
				return false;
		return true;
	}

	/**
	 * 添加指定集合中的全部元素
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<?> it = c.iterator();
		while (it.hasNext())
			add((E) it.next());
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 清空集合元素
	 * 
	 * 此处遍历了一遍集合，把所有value, next, previous域都置空，
	 * 是因为javaGC会根据“引用”来判断一个对象是否可以被回收，
	 * 若不这么做，在其他位置可能还有一些对这些对象的引用，就不会被回收。
	 */
	@Override
	public void clear() {
		Node<E> e = header.next;
		while (e != header) {
			Node<E> next = e.next;
			e.next = e.previous = null;
			e.value = null;
			e = next;
		}
		header.next = header.previous = header;
		size = 0;
	}

	// **************************************** 指定位置存取方法 ****************************************
	/**
	 * 获取指定索引上的元素
	 */
	@Override
	public E get(int index) {
		return node(index).value;
	}

	// 获取指定索引位置上的节点
	private Node<E> node(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> node;
		if (index < size >> 1) { // 在前半部分
			node = header.next; // 从第一个正序查找
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else { // 在后半部分
			node = header.previous; // 从最后一个逆序查找
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		return node;
	}

	/**
	 * 设置指定索引上的值
	 */
	@Override
	public E set(int index, E element) {
		Node<E> node = node(index);
		E oldValue = node.value;
		node.value = element;
		return oldValue;
	}

	/**
	 * 在指定索引位置添加
	 */
	@Override
	public void add(int index, E element) {
		Node<E> node = node(index);
		Node<E> newNode = new Node<E>(element, node.previous, node);
		node.previous.next = newNode;
		node.next.previous = newNode;
	}

	/**
	 * 移除指定索引的元素
	 */
	@Override
	public E remove(int index) {
		Node<E> node = node(index);
		E oldValue = node.value;
		node.previous.next = node.next;
		node.next.previous = node.previous;

		node.previous = node.next = null;
		node.value = null;
		return oldValue;
	}

	/**
	 * 栈操作，入栈
	 */
	@Override
	public void push(E element) {
		add(0, element);
	}

	/**
	 * 栈操作，出栈
	 */
	@Override
	public E pop() {
		return remove(0);
	}

	/**
	 * 栈操作，查看栈顶元素
	 */
	@Override
	public E peek() {
		return node(0).value;
	}

	// **************************************** 搜索方法 ****************************************
	/**
	 * 获取第一次出现指定元素的索引
	 */
	@Override
	public int indexOf(Object o) {
		int index = 0;
		for (Node<E> n = header.next; n != header; n = n.next, index++)
			if ((n.value == null && o == null) || n.value.equals(o)) // 找到相等的元素
				return index;
		return -1;
	}

	/**
	 * 获取最后一次出现指定元素的索引
	 */
	@Override
	public int lastIndexOf(Object o) {
		int index = size - 1;
		for (Node<E> n = header.previous; n != header; n = n.previous, index--)
			if ((n.value == null && o == null) || n.value.equals(o)) // 找到相等的元素
				return index;
		return -1;
	}

	// **************************************** 逆序迭代器 ****************************************

	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	// **************************************** 视图方法 ****************************************

	/**
	 * 获取一个子集（视图）
	 * 
	 * fromIndex和toIndex均包括
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> result = new LinkedList<E>();
		int count = 0;
		for (Node<E> n = header.next; n != header; n = n.next) {
			if (count >= fromIndex && count <= toIndex)
				result.add(n.value);
			count++;
		}
		return result;
	}

	/**
	 * 双向循环链表节点
	 * 
	 * @author fanzhoukai
	 */
	@SuppressWarnings("hiding")
	private class Node<E> {
		E value; // 当前节点存储的数据
		Node<E> previous; // 前一个节点
		Node<E> next; // 后一个节点

		public Node(E value, Node<E> previous, Node<E> next) {
			super();
			this.value = value;
			this.previous = previous;
			this.next = next;
		}
	}
}
