package com.fzk.adt;

import java.util.Iterator;

/**
 * 队列数据结构，先进先出（FIFO）
 * 
 * 不支持空值，若插入空值则抛出异常
 * 
 * @author fanzhoukai
 * 
 */
public class Queue<E> implements Iterable<E>{

	private Node<E> front; // 首节点（指向实际存在的节点）
	private Node<E> rear; // 尾节点（指向实际存在的节点）

	/**
	 * 构造函数
	 */
	public Queue() {
	}

	public boolean isEmpty() {
		return front == null;
	}

	/**
	 * 向队尾添加一个元素
	 */
	public boolean offer(E e) {
		Node<E> node = new Node<E>(e, null);
		if (isEmpty())
			front = rear = node;
		else
			rear = rear.next = node;
		return true;
	}

	/**
	 * 从队首取出并删除第一个元素
	 */
	public E poll() {
		if (isEmpty())
			return null;
		E oldValue = front.value;
		front = front.next;
		return oldValue;
	}

	/**
	 * 查看队首元素
	 */
	public E peek() {
		return isEmpty() ? null : front.value;
	}

	/**
	 * 转为字符串输出
	 */
	public void print() {
		StringBuffer sb = new StringBuffer();
		if (isEmpty()) {
			sb.append("null");
		} else {
			sb.append("[");
			for (Node<E> n = front; n != null; n = n.next) {
				sb.append(n.value);
				if (n != rear)
					sb.append(", ");
			}
			sb.append("]");
		}
		System.out.println(sb);
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
	 * @author fanzhoukai
	 *
	 */
	private class Itr implements Iterator<E>{

		Node<E> current = front;

		/**
		 * 判断是否有下一个元素
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * 获取下一个元素
		 */
		@Override
		public E next() {
			E currentValue = current.value;
			current = current.next;
			return currentValue;
		}

		/**
		 * 队列不实现移除方法
		 */
		@Override
		public void remove() {
		}
	}
	
	/**
	 * 队列节点类
	 * 
	 * @author fanzhoukai
	 */
	@SuppressWarnings("hiding")
	private class Node<E> {
		E value; // 当前节点存储的元素
		Node<E> next; // 下一个节点的地址

		Node(E e, Node<E> next) {
			this.value = e;
			this.next = next;
		}
	}

	public static void main(String[] args) {
		Queue<String> q = new Queue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.print();
		Iterator<String> it = q.iterator();
		while(it.hasNext())
			System.out.println(it.next());
	}
}
