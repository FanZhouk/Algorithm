package com.fzk.adt.graph;

import java.util.HashSet;

import com.fzk.adt.LinkedList;
import com.fzk.adt.LinkedQueue;
import com.fzk.adt.LinkedStack;
import com.fzk.adt.List;
import com.fzk.adt.Queue;
import com.fzk.adt.Stack;

/**
 * 邻接表实现的无向图数据结构
 * 不支持增删顶点，不支持平行边
 * 
 * @author fanzhoukai
 *
 */
public class UndirectedGraph implements Graph {

	// 邻接表，存储无向图信息
	private HashSet<Integer>[] adj;

	// 边数量
	private int edgeNum;

	/**
	 * 构建一个顶点数为edgeNum的图结构
	 * 
	 * @param vertexNum 顶点数
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph(int vertexNum) {
		if (vertexNum < 0)
			throw new IndexOutOfBoundsException(String.valueOf(vertexNum));
		adj = (HashSet<Integer>[]) new HashSet[vertexNum]; // 延迟初始化，在connect方法中创建HashMap
	}

	/**
	 * 获取顶点数量
	 * 
	 * @return 图中顶点数量
	 */
	@Override
	public int vertexNum() {
		return adj.length;
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
		if (adj[w] == null)
			adj[w] = new HashSet<Integer>();

		boolean isNewEdge = false;
		isNewEdge = adj[v].add(w);
		adj[w].add(v);
		if (isNewEdge)
			edgeNum++;
	}

	/**
	 * 判断两个顶点是否连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两个顶点连通返回true，否则返回false
	 */
	@Override
	public boolean connected(int v, int w) {
		return connected_bfs(v, w);
	}

	/**
	 * 利用BFS算法判断两顶点是否相连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两顶点连通，返回true，否则返回false
	 */
	public boolean connected_bfs(int v, int w) {
		checkIndex(v, w);
		if (v == w)
			return true;
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		Queue<Integer> queue = new LinkedQueue<Integer>();
		queue.offer(v);
		while (!queue.isEmpty()) {
			HashSet<Integer> children = adj[queue.poll()];
			for (Integer ch : children) {
				if (ch == w)
					return true;
				if (marked[ch])
					continue;
				marked[ch] = true;
				queue.offer(ch);
			}
		}
		return false;
	}

	/**
	 * 利用DFS算法判断两顶点是否相连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两顶点连通，返回true，否则返回false
	 */
	public boolean connected_dfs(int v, int w) {
		checkIndex(v, w);
		if (v == w)
			return true;
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		Stack<Integer> stack = new LinkedStack<Integer>();
		stack.push(v);
		while (!stack.isEmpty()) {
			HashSet<Integer> children = adj[stack.pop()];
			for (Integer ch : children) {
				if (ch == w)
					return true;
				if (marked[ch])
					continue;
				marked[ch] = true;
				stack.push(ch);
			}
		}
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
		// TODO Auto-generated method stub
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
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		List<Integer> list = new LinkedList<Integer>();
		list.add(v);
		// 使用链表+指针来模拟一个队列
		for (int cursor = 0; cursor < list.size(); cursor++) {
			HashSet<Integer> children = adj[list.get(cursor)];
			if (children == null)
				continue;
			for (Integer ch : children) {
				if (marked[ch])
					continue;
				marked[ch] = true;
				list.add(ch);
			}
		}
		list.remove(0); // 删除初始顶点
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
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		// 栈无法像队列一样使用链表+指针来模拟，因此将结果记录在新的list中
		List<Integer> result = new LinkedList<Integer>();
		Stack<Integer> stack = new LinkedStack<Integer>();
		stack.push(v);
		while (!stack.isEmpty()) {
			int current = stack.pop();
			result.add(current);
			HashSet<Integer> children = adj[current];
			if (children == null)
				continue;
			for (Integer ch : children) {
				if (marked[ch])
					continue;
				marked[ch] = true;
				stack.push(ch);
			}
		}
		result.remove(0);
		return result;
	}

	/**
	 * 深度优先搜索——递归实现
	 * 
	 * @param v 指定顶点
	 * @return 与指定顶点相连的所有顶点的可迭代对象
	 */
	public Iterable<Integer> dfs_r(int v) {
		checkIndex(v);
		List<Integer> result = new LinkedList<Integer>();
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		return dfs_r(v, marked, result);
	}

	// DFS的递归实现
	private Iterable<Integer> dfs_r(int v, boolean[] marked, List<Integer> result) {
		marked[v] = true;
		result.add(v);
		HashSet<Integer> children = adj[v];
		if (children == null)
			return result;
		for (Integer ch : children) {
			if (!marked[ch]) {
				dfs_r(ch, marked, result);
			}
		}
		return result;
	}

	/**
	 * 序列化方法
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(vertexNum() + " vertices, " + edgeNum() + " edges\r\n");
		for (int i = 0; i < adj.length; i++)
			sb.append(i + ":" + adj[i].toString() + "\r\n");
		return sb.toString();
	}

	// 检查索引是否越界
	private void checkIndex(int... indexes) {
		for (int i : indexes)
			if (i < 0 || i >= adj.length)
				throw new IndexOutOfBoundsException(String.valueOf(i));
	}
}