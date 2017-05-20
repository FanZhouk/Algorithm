package com.fzk.adt;

import java.util.Iterator;

/**
 * 队列结构，使用单链表实现
 * 
 * @author fanzhoukai
 * 
 */
public class LinkedQueue<E> implements Queue<E> {

	private Node first; // 队列首元素

	private Node last; // 队列尾元素

	private int size; // 数据长度

	/**
	 * 查看队列大小
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
	 * 查看队首元素
	 */
	@Override
	public E peek() {
		return first.element;
	}

	/**
	 * 入队操作，在bottom后添加
	 */
	@Override
	public void offer(E element) {
		if (isEmpty()) {
			first = last = new Node(element, null);
		} else {
			last.next = new Node(element, null);
			last = last.next;
		}
		size++;
	}

	/**
	 * 出队操作
	 */
	@Override
	public E poll() {
		E val = first.element;
		first = first.next;
		if (--size == 0)
			last = null;
		return val;
	}

	/**
	 * 队列翻转（单链表翻转）
	 */
	public void reverse() {
		if (size() <= 1)
			return;
		while (first != last) {
			Node tmp = first.next;
			first.next = last.next;
			last.next = first;
			first = tmp;
		}

		// 交换first与last
		Node tmp = first;
		first = last;
		last = tmp;
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

		Node curr = first;

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
