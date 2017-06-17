package com.fzk.adt.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.fzk.adt.LinkedList;
import com.fzk.adt.LinkedQueue;
import com.fzk.adt.LinkedStack;
import com.fzk.adt.List;
import com.fzk.adt.Queue;
import com.fzk.adt.Stack;

/**
 * 有向图数据结构
 * 不支持增删顶点，不支持平行边，支持自环
 * 
 * @author fanzhoukai
 *
 */
public class DirectedGraph implements Graph {

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
	public DirectedGraph(int vertexNum) {
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
	 * 获取与指定顶点直接相连的所有顶点的可迭代对象
	 * 
	 * @param v 顶点
	 * @return 与指定顶点直接相连的所有顶点的可迭代对象
	 */
	@Override
	public Iterable<Integer> adjacent(int v) {
		return adj[v] != null ? adj[v] : new HashSet<Integer>();
	}

	/**
	 * 在两个顶点之间创建一条有向连接v->w
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 */
	@Override
	public void connect(int v, int w) {
		checkIndex(v, w);
		if (adj[v] == null)
			adj[v] = new HashSet<Integer>();

		if (adj[v].add(w))
			edgeNum++;
	}

	/**
	 * 判断顶点1到顶点2是否存在一条有向路径
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 存在有向路径返回true，否则返回false
	 */
	@Override
	public boolean hasPath(int v, int w) {
		return hasPath_bfs(v, w);
	}

	/**
	 * 利用BFS算法判断顶点1到顶点2是否存在一条有向路径
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 存在有向路径返回true，否则返回false
	 */
	public boolean hasPath_bfs(int v, int w) {
		checkIndex(v, w);
		if (v == w)
			return true;
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		Queue<Integer> queue = new LinkedQueue<Integer>();
		queue.offer(v);
		while (!queue.isEmpty()) {
			HashSet<Integer> children = adj[queue.poll()];
			if (children == null)
				continue;
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
	 * 利用DFS算法判断顶点1到顶点2是否存在一条有向路径
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 存在有向路径返回true，否则返回false
	 */
	public boolean hasPath_dfs(int v, int w) {
		checkIndex(v, w);
		if (v == w)
			return true;
		boolean[] marked = new boolean[adj.length];
		marked[v] = true;
		Stack<Integer> stack = new LinkedStack<Integer>();
		stack.push(v);
		while (!stack.isEmpty()) {
			HashSet<Integer> children = adj[stack.pop()];
			if (children == null)
				continue;
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
	 * 判断两顶点是否强连通
	 * 
	 * @param v 顶点1
	 * @param w 顶点2
	 * @return 两顶点强连通返回true，否则返回false
	 */
	public boolean stronglyConnected(int v, int w) {
		return hasPath_bfs(v, w) && hasPath_bfs(w, v);
	}

	/**
	 * 返回有向图中强连通分量的数量
	 * TODO:此处使用最原始的O(n^2)方式，需要改成Kosaraju算法
	 * 
	 * @return 强连通分量的数量
	 */
	public int sccNum() {
		Set<HashSet<Integer>> scc = new HashSet<HashSet<Integer>>();
		boolean[] marked = new boolean[adj.length];
		for (int v = 0; v < adj.length; v++) {
			if (marked[v])
				continue;
			for (int w = 0; w < adj.length; w++) {
				if (marked[w])
					continue;
				if (stronglyConnected(v, w)) {
					boolean find = false;
					for (HashSet<Integer> set : scc) {
						if (set.contains(v)) {
							set.add(w);
							find = true;
							break;
						}
						if (set.contains(w)) {
							set.add(v);
							find = true;
							break;
						}
					}
					if (!find) {
						HashSet<Integer> newSet = new HashSet<Integer>();
						newSet.add(v);
						newSet.add(w);
						scc.add(newSet);
					}
				}
			}
		}
		return scc.size();
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
	 * 获取指定结点可达的所有顶点的可迭代对象
	 * 
	 * @param v 指定顶点
	 * @return 指定顶点可达的所有顶点的可迭代对象
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
	 * 获取当前图的反向图
	 * 
	 * @return 反向图
	 */
	public DirectedGraph reverse() {
		DirectedGraph re = new DirectedGraph(adj.length);
		for (int v = 0; v < adj.length; v++)
			for (int w : adj[v])
				re.connect(w, v);
		return re;
	}

	/**
	 * 判断该有向图是否存在环——DFS实现
	 * 
	 * @return 若图存在环返回true，否则返回false
	 */
	public boolean hasCycle() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 若该有向图存在环，则返回第一个查找到的环中所有顶点的可迭代对象
	 * 
	 * @return 有环则返回环中顶点，无环则返回null
	 */
	public Iterator<Integer> cycle() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO:给三个排序方法添加有向无环图的验证
	/**
	 * 拓扑排序——前序
	 * 
	 * @return 前序排序结果
	 */
	public Iterable<Integer> preOrder() {
		return new DepthFirstOrder(this).preOrder();
	}

	/**
	 * 拓扑排序——后序
	 * 
	 * @return 后序排序结果
	 */
	public Iterable<Integer> postOrder() {
		return new DepthFirstOrder(this).postOrder();
	}

	/**
	 * 拓扑排序——逆后序
	 * 
	 * @return 逆后序排序结果
	 */
	public Iterable<Integer> reversePostOrder() {
		return new DepthFirstOrder(this).reversePostOrder();
	}

	/**
	 * 基于深度优先搜索的顶点排序
	 * 
	 * 包括前序、后序、逆后序
	 */
	private class DepthFirstOrder {

		List<Integer> preOrder; // 前序排列结果
		List<Integer> postOrder; // 后序排列结果
		List<Integer> reversePostOrder; // 逆后序排列结果
		boolean[] marked;

		// 根据给定图计算排列结果
		DepthFirstOrder(DirectedGraph graph) {
			preOrder = new LinkedList<Integer>();
			postOrder = new LinkedList<Integer>();
			reversePostOrder = new LinkedList<Integer>();
			marked = new boolean[graph.vertexNum()];

			// 对所有未访问过的顶点进行DFS遍历，并记录结果
			for (int v = 0; v < marked.length; v++)
				if (!marked[v])
					dfsOrder(graph, v);
		}

		// DFS搜索，并构造排序结果
		// 由于后序排列的非递归形式较复杂，因此采用递归
		public void dfsOrder(DirectedGraph graph, int v) {
			preOrder.add(v);				// 构造前序排列
			marked[v] = true;
			for (int w : graph.adjacent(v))
				if (!marked[w])
					dfsOrder(graph, w);
			postOrder.add(v);				// 构造后序排列
			reversePostOrder.addFirst(v);	// 构造逆后序排列
		}

		// 获取前序排列结果
		Iterable<Integer> preOrder() {
			return preOrder;
		}

		// 获取后序排列结果
		Iterable<Integer> postOrder() {
			return postOrder;
		}

		// 获取逆后序排列结果
		Iterable<Integer> reversePostOrder() {
			return reversePostOrder;
		}
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
