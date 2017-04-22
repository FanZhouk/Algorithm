package com.fzk.adt;

/**
 * 队列接口（FIFO）
 * 
 * @author fanzhoukai
 *
 */
public interface Queue<E> {

	public void offer(E element);

	public E poll();

	public E peek();

	public boolean isEmpty();

	public int size();
}
