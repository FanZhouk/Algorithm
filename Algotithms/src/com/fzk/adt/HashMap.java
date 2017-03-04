package com.fzk.adt;

/**
 * 拉链法散列表的实现（仿HashMap）
 */
public class HashMap<K, V> {
	private Entry[] table;
	private static final int INITIAL_SIZE = 1 << 4;
	private static final double LOAD_FACTOR = 0.75;
	private int size;

	public HashMap() {
		table = new Entry[INITIAL_SIZE];
	}

	private class Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;

		Entry(K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public String toString() {
			return key + "-" + value;
		}
	}

	// 获取集合容量
	public int size() {
		return size;
	}

	// 判断集合是否为空
	public boolean isEmpty() {
		return size == 0;
	}

	// 判断是否包含指定键值
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	// 根据键获取值
	public V get(Object key) {
		Entry<K, V> entry = getEntry(key);
		return entry == null ? null : entry.value;
	}

	// 根据键获取节点
	private Entry<K, V> getEntry(Object key) {
		int index = index(key);
		if (key == null) {
			for (Entry<K, V> e = table[0]; e != null; e = e.next)
				if (e.key == null)
					return e;
		} else {
			for (Entry<K, V> e = table[index]; e != null; e = e.next) {
				if (key.equals(e.key))
					return e;
			}
		}
		return null;
	}

	// 计算键的哈希值映射在数组中的位置
	private int index(Object key) {
		return key == null ? 0 : key.hashCode() % table.length;
	}

	// 增加/修改键值对
	public void put(K key, V value) {
		int index = index(key);

		if (key == null) {
			for (Entry<K, V> e = table[0]; e != null; e = e.next) {
				if (e.key == null) {
					e.value = value;
					return;
				}
			}
		} else {
			for (Entry<K, V> e = table[index]; e != null; e = e.next) {
				if (key.equals(e.key)) {
					e.value = value;
					return;
				}
			}
		}
		Entry<K, V> newEntry = new Entry<K, V>(key, value, table[index]);
		table[index] = newEntry;
		size++;
		resizeIfNecessary();
	}

	// 根据需要扩容
	private void resizeIfNecessary() {
		boolean needResize = size * LOAD_FACTOR > table.length;
		if (!needResize)
			return;
		System.out.println("resizing...  origin size:" + size);
		int newSize = table.length << 1;
		Entry[] newTable = new Entry[newSize];
		for (Entry<K, V> entry : table) {
			for (Entry<K, V> e = entry; e != null; e = e.next) {
				int index = e.key == null ? 0 : e.key.hashCode() % newTable.length;
				Entry<K,V> newEntry = new Entry<K, V>(e.key, e.value, newTable[index]);
				newTable[index] = newEntry;
				e.key = null;
				e.value = null;
			}
		}
		table = newTable;
	}

	// 移除键值对
	public boolean remove(Object key) {
		int index = index(key);
		if (table[index] == null)
			return false;
		
		Entry<K,V> prev = table[index];
		Entry<K, V> curr = prev;
		while (curr != null) {
			if (key == null ? curr.key == null : key.equals(curr.key)) {
				if (prev == curr)
					table[index] = curr.next;
				else
					prev.next = curr.next;
				size--;
				return true;
			}
			prev = curr;
			curr = curr.next;
		}
		return false;
	}

	// 输出
	public void print() {
		StringBuffer sb = new StringBuffer();
		sb.append("******\r\n");
		for (Entry<K, V> entry : table) {
			if (entry == null) {
				sb.append("null\r\n");
			} else {
				for (Entry<K, V> e = entry; e != null; e = e.next) {
					sb.append(e.toString() + "\t");
				}
				sb.append("\r\n");
			}
		}
		System.out.println(sb.append("******"));
	}
}
