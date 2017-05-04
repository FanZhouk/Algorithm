package com.fzk.adt.unionFind;

/**
 * 并查集（Union-Find）数据结构 —— quick-find算法
 * 
 * @author fanzhoukai
 *
 */
public class QuickFindUF implements UnionFind {

	public int[] id; // 分量ID
	private int count; // 分量数量

	/**
	 * 构造一个并查集，初始化n个触点
	 */
	public QuickFindUF(int n) {
		id = new int[n];
		for (int i = 0; i < n; i++)
			id[i] = i;
		count = n;
	}

	/**
	 * 在p和q之间创建一条连接
	 */
	@Override
	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);
		if (pId == qId)
			return;
		for (int i = 0; i < id.length; i++) {
			if (id[i] == qId)
				id[i] = pId;
		}
		count--;
	}

	/**
	 * 获取p所在分量的标识符
	 */
	@Override
	public int find(int p) {
		return id[p];
	}

	/**
	 * 判断p和q是否相连
	 */
	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	 * 获取连通分量的数量
	 */
	@Override
	public int count() {
		return count;
	}
}
