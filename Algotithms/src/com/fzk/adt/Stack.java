package com.fzk.adt;

/**
 * 栈接口（LIFO）
 * 
 * @author fanzhoukai
 *
 */
public interface Stack<E> {

	public void push(E element);

	public E pop();

	public E peek();

	public boolean isEmpty();

	public int size();
}
