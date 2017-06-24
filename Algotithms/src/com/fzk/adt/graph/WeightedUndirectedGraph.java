package com.fzk.adt.graph;

import java.util.HashSet;

import com.fzk.adt.LinkedQueue;
import com.fzk.adt.MinPriorityQueue;
import com.fzk.adt.Queue;
import com.fzk.adt.unionFind.QuickUnionUF;
import com.fzk.adt.unionFind.UnionFind;

/**
 * 加权无向图数据结构
 * 
 * @author fanzhoukai
 *
 */
public class WeightedUndirectedGraph {

	private final int vertexNum; // 顶点总数

	private int edgeNum; // 边的总数

	private HashSet<Edge>[] adj; // 存储加权无向图的信息

	/**
	 * 构造一个指定顶点数的加权无向图
	 * 
	 * @param vertexNum 顶点数
	 */
	@SuppressWarnings("unchecked")
	public WeightedUndirectedGraph(int vertexNum) {
		this.vertexNum = vertexNum;
		this.edgeNum = 0;
		adj = (HashSet<Edge>[]) new HashSet[vertexNum];
		for (int i = 0; i < vertexNum; i++)
			adj[i] = new HashSet<Edge>();
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
	public Iterable<Edge> adjacent(int v) {
		return adj[v] != null ? adj[v] : new HashSet<Edge>();
	}

	/**
	 * 添加一条边
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @param weight 边的权重
	 */
	public void connect(int v, int w, double weight) {
		connect(new Edge(v, w, weight));
	}

	/**
	 * 添加一条边
	 * 
	 * @param edge 新添加的边，包括两个顶点以及权重
	 */
	public void connect(Edge edge) {
		int v = edge.either();
		int w = edge.other(v);
		adj[v].add(edge);
		adj[w].add(edge);
		edgeNum++;
	}

	/**
	 * 获取图中所有的边
	 * 
	 * @return 图中所有边的可迭代对象
	 */
	public Iterable<Edge> edges() {
		HashSet<Edge> edges = new HashSet<Edge>();
		for (int v = 0; v < vertexNum; v++)
			for (Edge e : adj[v])
				if (e.other(v) > v)
					edges.add(e);
		return edges;
	}

	/**
	 * 获取最小生成树——延时Prim算法
	 * 
	 * @return 最小生成树的所有边的可迭代对象
	 */
	public Iterable<Edge> mst_lazyPrim() {
		boolean[] marked = new boolean[vertexNum]; // 记录顶点是否被访问过
		MinPriorityQueue<Edge> pq = new MinPriorityQueue<Edge>(); // 记录横切边
		Queue<Edge> mst = new LinkedQueue<Edge>(); // 最小生成树

		visit_lazyPrim(0, marked, pq);
		while (!pq.isEmpty()) {
			Edge e = pq.deleteMin();
			int v = e.either(), w = e.other(v);
			if (marked[v] && marked[w])
				continue;
			mst.offer(e);
			if (!marked[v])
				visit_lazyPrim(v, marked, pq);
			if (!marked[w])
				visit_lazyPrim(w, marked, pq);
		}
		return mst;
	}

	// 将v顶点标记，并将所有与v相连且没有访问过的顶点形成的边加入最小优先队列
	private void visit_lazyPrim(int v, boolean[] marked, MinPriorityQueue<Edge> pq) {
		marked[v] = true;
		for(Edge e : adj[v])
			if(!marked[e.other(v)])
				pq.add(e);
	}

	/**
	 * 获取最小生成树——即时Prim算法
	 * 
	 * @return 最小生成树的所有边的可迭代对象
	 */
	public Iterable<Edge> mst_eagerPrim() {
		// TODO 没看懂...
		return null;
	}

	/**
	 * 获取最小生成树——Kruskal算法
	 * 
	 * @return 最小生成树的所有边的可迭代对象
	 */
	public Iterable<Edge> mst_kruskal() {
		Queue<Edge> mst = new LinkedQueue<Edge>();
		MinPriorityQueue<Edge> pq = new MinPriorityQueue<Edge>();
		for (Edge e : edges())
			pq.add(e);
		UnionFind uf = new QuickUnionUF(vertexNum);

		while (!pq.isEmpty() && mst.size() < vertexNum - 1) {
			Edge e = pq.deleteMin();
			int v = e.either(), w = e.other(v);
			if (uf.connected(v, w))
				continue;
			uf.union(v, w);
			mst.offer(e);
		}
		return mst;
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
	 * 加权边结构
	 */
	public class Edge implements Comparable<Edge> {

		private final int v; // 顶点1
		private final int w; // 顶点2
		private double weight; // 权重

		// 构造一条边，传入两个顶点以及边的权重
		public Edge(int v, int w, double weight) {
			this.v = v;
			this.w = w;
			this.weight = weight;
		}

		// 获取边的权重
		public double weight() {
			return weight;
		}

		// 返回任意一个顶点
		public int either() {
			return v;
		}

		// 返回另一个顶点
		public int other(int vertex) {
			if (vertex == v)
				return w;
			if (vertex == w)
				return v;
			throw new IllegalArgumentException(String.valueOf(vertex));
		}

		// 比较两条边权重的大小，若当前边权重更大，返回+1，相等返回0，否则返回-1
		@Override
		public int compareTo(Edge that) {
			if (this.weight < that.weight)
				return -1;
			if (this.weight > that.weight)
				return +1;
			return 0;
		}
		
		// 序列化
		@Override
		public String toString() {
			return String.format("%d - %d : %.2f", v, w, weight);
		}

		// 获取哈希值
		@Override
		public int hashCode() {
			return v + w + Double.hashCode(weight);
		}

		// 判断两条边是否是同一个边
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Edge))
				return false;
			Edge that = (Edge) o;
			return this.v == that.v && this.w == that.w && this.weight == that.weight;
		}
	}
}
