package com.fzk.adt;

import java.util.Iterator;

/**
 * 栈数据结构，使用数组实现
 * 
 * @author fanzhoukai
 * 
 */
public class ArrayStack<E> implements Stack<E>, Iterable<E> {

	private E[] data; // 存储数据

	private int size = 0; // 数据长度

	private static final int INITIAL_LENGTH = 1 << 4; // 初始数组容量为16

	public ArrayStack() {
		data = (E[]) new Object[INITIAL_LENGTH];
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * 入栈操作
	 */
	@Override
	public void push(E element) {
		if (size == data.length) // 仅当数组满了时才会扩容
			resize(size << 1);
		data[size++] = element;
	}

	/**
	 * 调整数组大小
	 */
	private void resize(int newCapacity) {
		E[] tmp = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			tmp[i] = data[i];
		}
		data = tmp;
	}

	/**
	 * 出栈操作
	 */
	@Override
	public E pop() {
		E val = data[--size];
		data[size] = null;
		// 当数据个数是数组长度的1/4时，缩减数组容量
		// 这保证了在缩减容量之后，数组仍为半满状态，可以进行多次push操作
		if (size > 0 && size == data.length >> 2)
			resize(data.length >> 1);
		return val;
	}

	/**
	 * 查看栈顶元素
	 */
	@Override
	public E peek() {
		return data[size];
	}

	/**
	 * 获取迭代器
	 */
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {

		int cursor = size - 1;

		@Override
		public boolean hasNext() {
			return cursor >= 0;
		}

		@Override
		public E next() {
			return data[cursor--];
		}
	}

}
