package com.fzk.adt;

/**
 * 最大优先队列（利用二叉堆实现）
 * 
 * @author fanzhoukai
 * 
 * @param <K> 队列内存储的对象类型，须继承Comparable
 */
public class MaxPriorityQueue<K extends Comparable<K>> {

	// 二叉堆，存储优先队列
	// 不使用pq[0]，堆元素索引从1开始
	private Object[] pq;

	private int INIT_CAPACITY = 1 << 4; // 初始长度为16

	private int size = 0; // 实际存储的元素数量

	/**
	 * 创建一个空的最大优先队列
	 */
	public MaxPriorityQueue() {
		pq = new Object[INIT_CAPACITY];
	}

	/**
	 * 创建一个最大容量为max的最大优先队列
	 */
	public MaxPriorityQueue(int max) {
		pq = new Object[max];
	}

	/**
	 * 用数组a中的元素创建一个最大优先队列
	 */
	public MaxPriorityQueue(Comparable<K>[] a){
		// TODO Auto-generated method stub
	}

	/**
	 * 向优先队列中插入一个元素
	 * 
	 * @param k 插入的元素
	 */
	public void insert(K k) {
		pq[++size] = k;
		swim(size);
	}

	/**
	 * 查看最大元素
	 */
	public K max() {
		return size == 0 ? null : (K) pq[1];
	}

	/**
	 * 删除并返回最大元素
	 */
	public K delMax() {
		if(size == 0)
			return null;
		K max = (K)pq[1];
		exch(1,size--);
		sink(1);
		return max;
	}

	/**
	 * 判断队列是否为空
	 * 
	 * @return true if this queue is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * 返回队列中元素个数
	 */
	public int size() {
		return size;
	}

	/**
	 * 打印出二叉堆
	 */
	public void print() {
		StringBuffer sb = new StringBuffer("[");
		for (int i = 1; i <= size; i++) {	// 此处包括size，是因为数组索引为0的元素为空，实际存储的索引就是 1 ~ size
			sb.append(pq[i]);
			if (i != size)
				sb.append(", ");
		}
		sb.append("]");
		System.out.println(sb);
	}
	
	// 将索引为k的元素上浮（由下至上的堆有序化）
	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k /= 2;
		}
	}

	// 将索引为k的元素下沉（由上至下的堆有序化）
	private void sink(int k) {
		while (k * 2 <= size) {
			int ind = k * 2;
			if (ind < size && less(ind, ind + 1))
				ind++;
			if (!less(k, ind))
				break;
			exch(k, ind);
			k = ind;
		}
	}

	// 若索引为i的元素小于索引为j的元素，返回true
	@SuppressWarnings("unchecked")
	private boolean less(int i, int j) {
		Comparable<K> o1 = (Comparable<K>) pq[i];
		return o1.compareTo((K) pq[j]) < 0;
	}

	// 交换索引为i和j的元素
	private void exch(int i, int j) {
		Object tmp = pq[i];
		pq[i] = pq[j];
		pq[j] = tmp;
	}
}
