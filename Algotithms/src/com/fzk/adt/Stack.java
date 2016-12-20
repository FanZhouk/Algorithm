package com.fzk.adt;

import java.util.Iterator;

/**
 * 堆栈数据结构，后进先出（LIFO）
 * 
 * @author fanzhoukai
 * 
 */
public class Stack<E> implements Iterable<E>{

	private Node<E> top; // 栈顶元素

	/**
	 * 构造函数
	 */
	public Stack() {
	}

	/**
	 * 判空
	 */
	public boolean isEmpty() {
		return top == null;
	}

	/**
	 * 入栈
	 */
	public void push(E e) {
		top = new Node<E>(e, top);
	}

	/**
	 * 出栈
	 */
	public E pop() {
		if (isEmpty())
			return null;
		E oldValue = top.value;
		top = top.next;
		return oldValue;
	}

	/**
	 * 查看栈顶元素
	 */
	public E peek() {
		return isEmpty() ? null : top.value;
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
			for (Node<E> n = top; n != null; n = n.next) {
				sb.append(n.value);
				if (n.next != null)
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

		Node<E> current = top;

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
		 * 不实现移除方法
		 */
		@Override
		public void remove() {
		}

	}
	
	/**
	 * 堆栈节点类
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
		Stack<String> stack = new Stack<String>();
		stack.push("1");
		stack.push("2");
		stack.push("3");
		stack.print();
		Iterator<String> it = stack.iterator();
		while(it.hasNext())
			System.out.println(it.next());
	}

	
}
