package com.fzk.adt.graph;

import java.util.HashSet;

/**
 * 加权有向图数据结构
 * 
 * @author fanzhoukai
 *
 */
public class WeightedDirectedGraph {

	private final int vertexNum; // 顶点数量

	private int edgeNum; // 顶点数量

	private HashSet<DirectedEdge>[] adj; // 邻接表

	/**
	 * 构建一个指定顶点数的加权有向图
	 */
	@SuppressWarnings("unchecked")
	public WeightedDirectedGraph(int vertexNum) {
		this.vertexNum = vertexNum;
		this.edgeNum = 0;
		adj = (HashSet<DirectedEdge>[]) new HashSet[vertexNum];
		for (int v = 0; v < vertexNum; v++)
			adj[v] = new HashSet<DirectedEdge>();
	}

	/**
	 * 获取顶点数量
	 * 
	 * @return 图中顶点数量
	 */
	public int vertexNum() {
		return vertexNum;
	}

	/**
	 * 获取边的数量
	 * 
	 * @return 图中边的数量
	 */
	public int edgeNum() {
		return edgeNum;
	}

	/**
	 * 获取与指定顶点直接相连的所有顶点的可迭代对象
	 * 
	 * @param v 顶点
	 * @return 与指定顶点直接相连的所有顶点的可迭代对象
	 */
	public Iterable<DirectedEdge> adjacent(int v) {
		return adj[v] != null ? adj[v] : new HashSet<DirectedEdge>();
	}

	/**
	 * 添加一条有向边
	 * 
	 * @param v 起点
	 * @param w 终点
	 * @param weight 边的权重
	 */
	public void connect(int from, int to, double weight) {
		connect(new DirectedEdge(from, to, weight));
	}

	/**
	 * 添加一条有向边
	 * 
	 * @param edge 新添加的边，包括起点、终点和权重
	 */
	public void connect(DirectedEdge edge) {
		adj[edge.from].add(edge);
	}

	/**
	 * 获取图中所有的边
	 * 
	 * @return 图中所有边的可迭代对象
	 */
	public Iterable<DirectedEdge> edges() {
		HashSet<DirectedEdge> edges = new HashSet<DirectedEdge>();
		for (int v = 0; v < vertexNum; v++)
			for (DirectedEdge e : adj[v])
				edges.add(e);
		return edges;
	}

	/**
	 * 判断两个顶点是否连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两个顶点连通返回true，否则返回false
	 */
	public boolean hasPath(int v, int w) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 获取与指定结点相连的所有顶点的可迭代对象
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> iterator(int v) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 加权有向边结构
	 */
	public class DirectedEdge {
		private final int from; // 边的起点
		private final int to; // 边的终点
		private final double weight; // 边的权重

		// 构造一个加权有向边，包括边的起点、终点和权重
		public DirectedEdge(int from, int to, double weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		// 获取边的起点
		public int from() {
			return from;
		}

		// 获取边的终点
		public int to() {
			return to;
		}

		// 获取边的权重
		public double weight() {
			return weight;
		}

		// 序列化
		@Override
		public String toString() {
			return String.format("%d -> %d : $.2f", from, to, weight);
		}
	}
}
