package com.fzk.adt;

/**
 * 队列接口（FIFO）
 * 
 * @see com.fzk.adt.LinkedQueue 单链表实现队列结构
 * @author fanzhoukai
 *
 */
public interface Queue<E> extends Iterable<E> {

	/**
	 * 入队操作，在队尾添加元素
	 */
	public void offer(E element);

	/**
	 * 出队操作
	 * 
	 * @return 队首元素
	 */
	public E poll();

	/**
	 * 查看队首元素
	 * 
	 * @return 队首元素
	 */
	public E peek();

	/**
	 * 判断是否为空
	 * 
	 * @return 为空返回true，否则返回false
	 */
	public boolean isEmpty();

	/**
	 * 查看队列大小
	 * 
	 * @return 队列中元素个数
	 */
	public int size();
}
