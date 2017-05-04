package com.fzk.adt.unionFind;

/**
 * 并查集数据结构
 * 
 * @author fanzhoukai
 *
 */
public interface UnionFind {
	/**
	 * 在p和q之间创建一条连接
	 */
	public void union(int p, int q);

	/**
	 * 获取p所在分量的标识符
	 */
	public int find(int p);

	/**
	 * 判断p和q是否相连
	 */
	public boolean connected(int p, int q);

	/**
	 * 获取连通分量的数量
	 */
	public int count();
}
