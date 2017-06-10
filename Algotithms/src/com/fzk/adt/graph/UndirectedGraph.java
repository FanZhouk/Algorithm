package com.fzk.adt.graph;

import java.util.HashSet;

import com.fzk.adt.LinkedList;
import com.fzk.adt.LinkedStack;
import com.fzk.adt.List;
import com.fzk.adt.Stack;

/**
 * 无向图数据结构
 * 不支持新增、删除顶点，不支持平行边
 * 
 * @author fanzhoukai
 *
 */
public class UndirectedGraph implements Graph {

	// 使用邻接表数组存储无向图信息
	private HashSet<Integer>[] adj;

	// 顶点数量
	private final int vertexNum;

	// 边的数量
	private int edgeNum;

	/**
	 * 构建一个指定顶点数量的无向图
	 * 
	 * @param vertexNum 顶点数量
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph(int vertexNum) {
		if (vertexNum < 0)
			throw new IndexOutOfBoundsException(String.valueOf(vertexNum));
		this.vertexNum = vertexNum;
		adj = (HashSet<Integer>[]) new HashSet[vertexNum]; // 暂不初始化，在connect方法中才进行初始化
	}

	/**
	 * 获取顶点数量
	 * 
	 * @return 图中顶点数量
	 */
	@Override
	public int vertexNum() {
		return vertexNum;
	}

	/**
	 * 获取边的数量
	 * 
	 * @return 图中边的数量
	 */
	@Override
	public int edgeNum() {
		return edgeNum;
	}

	/**
	 * 在两个顶点之间创建一条连接
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 */
	@Override
	public void connect(int v, int w) {
		checkIndex(v, w);
		if (adj[v] == null)
			adj[v] = new HashSet<Integer>();
		adj[v].add(w);

		if (adj[w] == null)
			adj[w] = new HashSet<Integer>();
		if (adj[w].add(v))
			edgeNum++;
	}

	/**
	 * 判断两个顶点是否连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两个顶点连通返回true，否则返回false
	 */
	public boolean connected(int v, int w) {
		checkIndex(v, w);
		if(v == w) // 顶点与自身相连通
			return true;
		Iterable<Integer> it = bfs(v);
		for (Integer i : it)
			if (w == i)
				return true;
		return false;
	}

	/**
	 * 返回两个顶点之间的一条路径
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两个顶点之间的一条路径
	 */
	public Iterable<Integer> path(int v, int w) {
		//TODO
		return null;
	}

	/**
	 * 获取与指定结点相连的所有顶点的可迭代对象
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	@Override
	public Iterable<Integer> iterator(int v) {
		return bfs(v);
	}

	/**
	 * 广度优先搜索——非递归实现
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> bfs(int v) {
		checkIndex(v);
		List<Integer> list = new LinkedList<Integer>();
		list.add(v);
		boolean[] mark = new boolean[vertexNum]; // 记录是否已经被访问过，默认全部为false
		mark[v] = true;
		int cursor = 0;
		while (cursor < list.size()) {
			HashSet<Integer> children = adj[list.get(cursor)]; // 当前结点下的下级节点
			cursor++;
			if (children == null)
				continue;
			for (Integer ch : children) {
				if (mark[ch])
					continue;
				list.add(ch);
				mark[ch] = true;
			}
		}
		list.remove(0); // 剔除原始顶点
		return list;
	}

	/**
	 * 深度优先搜索——非递归实现
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> dfs(int v) {
		checkIndex(v);
		List<Integer> result = new LinkedList<Integer>();
		Stack<Integer> stack = new LinkedStack<Integer>();
		boolean[] mark = new boolean[adj.length];
		mark[v] = true;
		stack.push(v);
		while (!stack.isEmpty()) {
			HashSet<Integer> children = adj[stack.pop()];
			for (Integer ch : children) {
				if (mark[ch])
					continue;
				result.add(ch);
				stack.push(ch);
				mark[ch] = true;
			}
		}
		return result;
	}

	/**
	 * 深度优先搜索——递归实现
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> dfs_r(int v) {
		boolean[] mark = new boolean[adj.length];
		mark[v] = true;
		List<Integer> list = new LinkedList<Integer>();
		list.add(v);
		return dfs_r(v, mark, list);
	}

	// dfs的递归实现
	private Iterable<Integer> dfs_r(int v, boolean[] mark, List<Integer> list) {
		HashSet<Integer> children = adj[v];
		for (Integer ch : children) {
			if (mark[ch])
				continue;
			mark[ch] = true;
			list.add(ch);
			dfs_r(ch, mark, list);
		}
		return list;
	}

	// 确保索引不超过顶点的数量
	private void checkIndex(int... index) {
		for (int i : index)
			if (i < 0 || i >= adj.length)
				throw new IndexOutOfBoundsException(String.valueOf(i));
	}
}
