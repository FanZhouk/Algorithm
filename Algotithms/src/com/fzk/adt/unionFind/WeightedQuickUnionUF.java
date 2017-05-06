package com.fzk.adt.unionFind;

/**
 * 并查集（Union-Find）数据结构 —— 加权quick-union算法
 * 
 * @author fanzhoukai
 *
 */
public class WeightedQuickUnionUF implements UnionFind {

	private int[] id; // 分量ID
	private int[] sz; // 所在根节点的分量树的大小（节点数）
	private int count; // 分量数量

	/**
	 * 构造一个并查集，初始化n个触点
	 */
	public WeightedQuickUnionUF(int n) {
		id = new int[n];
		sz = new int[n];
		for (int i = 0; i < n; i++) {
			id[i] = i;
			sz[i] = 1; // 初始化时，每个节点互不连通，因此树的大小都是1
		}
		count = n;
	}

	/**
	 * 在p和q之间创建一条连接
	 */
	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)
			return;
		if (sz[pRoot] > sz[qRoot]) {
			id[qRoot] = pRoot;
			sz[pRoot] += sz[qRoot];
		} else {
			id[pRoot] = qRoot;
			sz[qRoot] += sz[pRoot];
		}
		count--;
	}

	/**
	 * 获取p所在分量的标识符
	 */
	@Override
	public int find(int p) {
		while (id[p] != p)
			p = id[p];
		return p;
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
