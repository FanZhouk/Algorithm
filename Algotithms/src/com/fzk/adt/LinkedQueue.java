package com.fzk.adt;

import java.util.Iterator;

/**
 * 单链表数据结构，包含栈、队列的全部功能
 * 
 * @author fanzhoukai
 * 
 */
public class LinkedQueue<E> implements Queue<E>, Iterable<E> {

	private Node top; // 链表首元素

	private Node bottom; // 链表尾元素

	private int size; // 数据长度

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 查看队首元素
	 */
	@Override
	public E peek() {
		return top.element;
	}

	/**
	 * 入队操作，在bottom后添加
	 */
	@Override
	public void offer(E element) {
		if (isEmpty()) {
			top = bottom = new Node(element, null);
		} else {
			bottom.next = new Node(element, null);
			bottom = bottom.next;
		}
		size++;
	}

	/**
	 * 出队操作
	 */
	@Override
	public E poll() {
		E val = top.element;
		top = top.next;
		if (--size == 0)
			bottom = null;
		return val;
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {

		Node curr = top;

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public E next() {
			E val = curr.element;
			curr = curr.next;
			return val;
		}
	}

	/**
	 * 单链表节点类
	 */
	private class Node {
		E element;
		Node next;

		Node(E element, Node next) {
			this.element = element;
			this.next = next;
		}
	}
}
