package com.fzk.adt;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * 单例集合接口
 * 
 * @author fanzhoukai
 * 
 */
public interface List<E> extends Collection<E> {
	/**
	 * 查询相关方法
	 */

	// 获取集合实际长度
	int size();

	// 判断集合是否为空
	boolean isEmpty();

	// 判断集合是否包含指定元素
	boolean contains(Object o);

	// 获取迭代器
	Iterator<E> iterator();

	// 获取Object数组
	Object[] toArray();

	// 获取指定类型数组
	<T> T[] toArray(T[] a);

	/**
	 * 修改相关方法
	 */

	// 增加一个元素
	boolean add(E e);

	// 移除一个元素
	boolean remove(Object o);

	/**
	 * 批量修改相关方法
	 */

	// 判断是否包含全部
	boolean containsAll(Collection<?> c);

	// 增加全部元素
	boolean addAll(Collection<? extends E> c);

	// 指定位置增加全部元素
	boolean addAll(int index, Collection<? extends E> c);

	// 移除全部元素
	boolean removeAll(Collection<?> c);

	// 只保留指定元素
	boolean retainAll(Collection<?> c);

	// 清空集合
	void clear();

	/**
	 * 比较、哈希值方法
	 */

	// 判断是否等于指定集合
	boolean equals(Object o);

	// 计算哈希值
	int hashCode();

	/**
	 * 指定位置存取方法
	 */

	// 获取指定位置的元素
	E get(int index);

	// 设置指定位置的元素
	E set(int index, E element);

	// 在首位置添加元素
	void addFirst(E element);

	// 在指定位置增加元素
	void add(int index, E element);

	// 移除指定位置的元素
	E remove(int index);

	/**
	 * 搜索方法
	 */

	// 获取第一个出现指定元素的索引
	int indexOf(Object o);

	// 获取最后一个出现指定元素的索引
	int lastIndexOf(Object o);

	/**
	 * 逆序迭代器
	 */

	// 获取逆序迭代器
	ListIterator<E> listIterator();

	// 获取指定开始位置的逆序迭代器
	ListIterator<E> listIterator(int index);

	/**
	 * 视图方法
	 */

	// 截取子集
	List<E> subList(int fromIndex, int toIndex);
}
