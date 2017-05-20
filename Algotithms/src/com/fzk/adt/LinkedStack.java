package com.fzk.adt;

import java.util.Iterator;

/**
 * 栈结构，使用单链表实现
 * 
 * @author fanzhoukai
 * 
 */
public class LinkedStack<E> implements Stack<E>, Iterable<E> {

	private Node top; // 栈顶元素

	private int size; // 数据长度

	/**
	 * 获取数据长度
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 判断是否为空
	 */
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
	 * 出栈操作，取出栈顶元素
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

	/**
	 * 栈元素翻转（单链表翻转）
	 */
	public void reverse() {
		if (size() <= 1)
			return;
		Node bottom = top;
		while (bottom.next != null)
			bottom = bottom.next;
		while (top != bottom) {
			Node tmp = top.next;
			top.next = bottom.next;
			bottom.next = top;
			top = tmp;
		}
	}

	/**
	 * 获取迭代器
	 */
	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	/**
	 * 迭代器类
	 */
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
