package com.fzk.adt.graph;

/**
 * 无权图结构接口
 * 
 * @author fanzhoukai
 *
 */
public interface Graph {
	/**
	 * 获取顶点数量
	 * 
	 * @return 图中顶点数量
	 */
	public int vertexNum();

	/**
	 * 获取边的数量
	 * 
	 * @return 图中边的数量
	 */
	public int edgeNum();

	/**
	 * 获取与指定顶点直接相连的所有顶点的可迭代对象
	 * 
	 * @param v 顶点
	 * @return 与指定顶点直接相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> adjacent(int v);

	/**
	 * 在两个顶点之间创建一条连接
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 */
	public void connect(int v, int w);

	/**
	 * 判断两个顶点是否连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两个顶点连通返回true，否则返回false
	 */
	public boolean hasPath(int v, int w);

	/**
	 * 获取与指定结点相连的所有顶点的可迭代对象
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> iterator(int v);

}
