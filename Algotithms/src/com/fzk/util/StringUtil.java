package com.fzk.util;

/**
 * 字符串工具类
 * 
 * @author fanzhoukai
 * 
 */
public class StringUtil {

	/** ASC-II码表中的字符个数 */
	private static final int R = 256;

	/**
	 * 键索引计数法——以字符串第一个字符作为键
	 * 
	 * @param arr 要排序的数组
	 */
	public static void sortByFirstChar(String[] arr) {
		int[] count = new int[R + 1];
		for (String str : arr) // 计算每个字符出现的频率
			count[str.charAt(0) + 1]++;
		for (int i = 1; i < R; i++) // 将频率转化为索引
			count[i] += count[i - 1];
		String[] aux = new String[arr.length];
		for (int i = 0; i < arr.length; i++) // 在辅助数组中排序
			aux[count[arr[i].charAt(0)]++] = arr[i];
		for (int i = 0; i < arr.length; i++) // 回写到原数组中
			arr[i] = aux[i];
	}

	/**
	 * 低位优先的字符串排序
	 * 
	 * @param arr 要排序的数组
	 * @param w 根据前w个字符排序
	 */
	public static void sort_LSD(String[] arr, int w) {
		String[] aux = new String[arr.length];

		for (int d = w - 1; d >= 0; d--) {
			int[] count = new int[R + 1];
			for (String str : arr)
				count[str.charAt(d) + 1]++;
			for (int i = 1; i <= R; i++)
				count[i] += count[i - 1];
			for (String str : arr)
				aux[count[str.charAt(d)]++] = str;
			for (int i = 0; i < arr.length; i++)
				arr[i] = aux[i];
		}
	}

	/**
	 * 高位优先的字符串排序
	 * 
	 * @param arr 要排序的数组
	 */
	public static void sort_MSD(String[] arr) {
		int M = 15; // 小数组的切换阈值
		String[] aux = new String[arr.length]; // 辅助数组
		sort_MSD(arr, 0, arr.length - 1, 0, M, aux);

	}

	// 以第d个字符为键将arr[low]至arr[high]排序
	private static void sort_MSD(String[] arr, int lo, int hi, int d, int M, String[] aux) {
		if (hi <= lo + M) {
			SortUtil.insertionSort(arr, lo, hi);
			return;
		}
		int[] count = new int[R + 2];
		for (int i = lo; i <= hi; i++)
			count[charAt(arr[i], d) + 2]++;
		for (int r = 0; r < R + 1; r++)
			count[r + 1] += count[r];
		for (int i = lo; i <= hi; i++)
			aux[count[charAt(arr[i], d) + 1]++] = arr[i];
		for (int i = lo; i <= hi; i++)
			arr[i] = aux[i - lo];
		for (int r = 0; r < R; r++)
			sort_MSD(arr, lo + count[r], lo + count[r + 1]-1, d + 1, M, aux);
	}

	// 获取字符串指定位置的字符，越界返回-1
	private static int charAt(String str, int d) {
		if (d < str.length())
			return str.charAt(d);
		return -1;
	}
}
