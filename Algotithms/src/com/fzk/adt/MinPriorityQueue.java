package com.fzk.adt;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 最小优先队列
 * 
 * @author fanzhoukai
 * 
 */
public class MinPriorityQueue<E extends Comparable<E>> {

	// 二叉堆，存储集合元素
	// heap[0]不存储元素，从索引1开始
	private Object[] heap;

	// 集合初始长度为0
	private int size = 0;

	// 数组初始化容量为16
	private static final int INITIAL_CAPACITY = 1 << 4;

	/**
	 * 创建一个默认长度的空队列
	 */
	public MinPriorityQueue() {
		this(INITIAL_CAPACITY);
	}

	/**
	 * 创建一个指定初始长度的空队列
	 * 
	 * @param initCapacity 二叉堆的初始容量
	 */
	public MinPriorityQueue(int initCapacity) {
		heap = new Object[initCapacity + 1]; // 加1是因为索引为0的位置不存储
	}

	/**
	 * 创建一个指定数组的二叉堆
	 * 
	 * @param array
	 */
	public MinPriorityQueue(E[] array) {
		heap = new Object[size + 1];
		for (E val : array)
			add(val);
	}
	
	/**
	 * 获取优先队列的长度
	 */
	public int size() {
		return size;
	}

	/**
	 * 判断是否为空
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 添加一个元素
	 * 
	 * @param ele 添加的元素
	 */
	public void add(E ele) {
		if (ele == null)
			throw new NullPointerException();
		ensureCapacity(++size + 1);
		heap[size] = ele;
		swim(size);
	}

	/**
	 * 删除并返回集合中的最小元素
	 */
	@SuppressWarnings("unchecked")
	public E deleteMin() {
		if (isEmpty())
			throw new NoSuchElementException("It's already empty!");
		Object oldMin = heap[1];
		heap[1] = heap[size--];
		sink(1);
		heap[size + 1] = null;
		return (E) oldMin;
	}

	// 给数组扩容
	// size是数组要保持的最小容量
	private void ensureCapacity(int size) {
		if (size <= heap.length)
			return;

		// 扩容后的容量：若<64，扩为2倍；否则扩为1.5倍
		int newCapacity = heap.length;
		do {
			if (newCapacity < 64) newCapacity <<= 1;
			else newCapacity = newCapacity / 2 * 3;
		} while (newCapacity < size);

		heap = Arrays.copyOf(heap, newCapacity);
	}

	// 将指定索引的元素上浮
	private void swim(int k) {
		Object tmp = heap[k];
		int cursor = k;
		while (cursor > 1 && less(tmp, heap[cursor / 2])) {
			heap[cursor] = heap[cursor / 2];
			cursor >>= 1;
		}
		heap[cursor] = tmp;
	}

	// 将指定索引的元素下沉
	// 挖坑填数法，而不使用交换的方法，减少赋值次数
	private void sink(int k) {
		int cursor = k;
		Object tmp = heap[cursor];
		while (cursor * 2 <= size) { // 存在左节点
			int next = cursor * 2; // 记录左右节点中最小的一个
			if (next < size && less(heap[next + 1], heap[next]))
				next++;
			if (less(tmp, heap[next])) // 比较当前节点和左右中最小的谁更小
				break;
			heap[cursor] = heap[next];
			cursor = next;
		}
		heap[cursor] = tmp;
	}

	// 判断第一个元素是否小于第二个元素
	@SuppressWarnings("unchecked")
	private boolean less(Object o1, Object o2) {
		Comparable<E> c = (Comparable<E>) o1;
		return c.compareTo((E) o2) < 0;
	}
	
	/**
	 * 顺序打印数组
	 */
	public void print() {
		StringBuffer sb = new StringBuffer("[");
		for (int i = 1; i <= size; i++) {
			sb.append(heap[i]);
			if (i != size)
				sb.append(", ");
		}
		sb.append("]");
		System.out.print(sb);
	}

	/**
	 * 获取迭代器
	 * 
	 * 迭代器是按照从小到大的顺序，而不是直接按照数组的顺序
	 */
	public Iterator<E> iterator() {
		return new Itr();
	}
	
	/**
	 * 迭代器类
	 * 
	 * @author fanzhoukai
	 */
	private class Itr implements Iterator<E> {

		MinPriorityQueue<E> copy;

		// 从小到大遍历，复制二叉堆，每次弹出最大元素
		@SuppressWarnings("unchecked")
		public Itr() {
			copy = new MinPriorityQueue<E>(size);
			copy.heap = new Object[size];
			for (int i = 1; i <= size; i++)
				copy.add((E) heap[i]);
			copy.size = size;
		}

		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public E next() {
			return copy.deleteMin();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("No no no, U can't do this!");
		}
	}
}
