package com.fzk.adt;

import java.util.Iterator;

/**
 * 栈结构，使用单链表实现
 * 
 * @author fanzhoukai
 * 
 */
public class LinkedStack<E> implements Stack<E>, Iterable<E> {

	private Node top; // 链表首元素

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
	 * 入栈操作，在top前添加
	 */
	@Override
	public void push(E element) {
		top = new Node(element, top);
		size++;
	}

	/**
	 * 出栈操作，取出top元素
	 */
	@Override
	public E pop() {
		E val = top.element;
		top = top.next;
		size--;
		return val;
	}

	/**
	 * 查看栈顶元素
	 */
	@Override
	public E peek() {
		return top.element;
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
