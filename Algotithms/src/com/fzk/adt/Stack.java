package com.fzk.adt;

/**
 * 栈接口（LIFO）
 * 
 * @see com.fzk.adt.LinkedStack 链表实现的栈结构
 * @see com.fzk.adt.ArrayStack 数组实现的栈结构
 * @author fanzhoukai
 *
 */
public interface Stack<E> {

	/**
	 * 入栈操作，在top前添加
	 */
	public void push(E element);

	/**
	 * 出栈操作，取出栈顶元素
	 * 
	 * @return 栈顶元素
	 */
	public E pop();

	/**
	 * 查看栈顶元素
	 * 
	 * @return 栈顶元素
	 */
	public E peek();

	/**
	 * 判断是否为空
	 * 
	 * @return 为空返回true，否则返回false
	 */
	public boolean isEmpty();

	/**
	 * 获取数据长度
	 * 
	 * @return 栈内元素个数
	 */
	public int size();
}
