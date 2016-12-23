package com.fzk.util;

import java.util.Arrays;

/**
 * 数组工具类
 * 
 * @author fanzhoukai
 * 
 */
public class ArrayUtil {

	/**
	 * 二分查找，默认数组为升序排序，且无重复数字（递归实现）
	 * 
	 * 来源：1.1.10节
	 * 复杂度：lgN
	 * @param arr 要查询的数组
	 * @param target 要查找的目标数字
	 * @return 找到的索引值，若没有返回-1
	 */
	public static int binarySearch(int[] arr, int target) {
		if (arr == null)
			return -1;
		return binarySearch(arr, target, 0, arr.length - 1);
	}

	/**
	 * 指定起始和结束位置的二分查找（起始和结束位置均包括）
	 */
	private static int binarySearch(int[] arr, int target, int low, int high) {
		if (low > high)
			return -1;
		int mid = (low + high) >> 1;
		if (arr[mid] == target) // 在mid位置上
			return mid;
		if (arr[mid] > target) // 在左侧
			return binarySearch(arr, target, low, mid - 1);
		return binarySearch(arr, target, mid + 1, high); // 在右侧
	}

	/**
	 * 2-Sum问题
	 * 默认数组中没有重复数字
	 * 
	 * 来源：1.4.5.1节
	 * 复杂度：NlgN
	 * @param arr 查询的数组
	 * @return 数组中两个数字和为0的组数
	 */
	public static int twoSum(int[] arr) {
		Arrays.sort(arr); // 归并排序，NlgN
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (binarySearch(arr, -arr[i]) > i) { // 二分查找，lgN
				count++;
			}
		}
		return count;
	}

	/**
	 * 3-Sum问题
	 * 
	 * 来源：1.4.5.2节
	 * 复杂度：N^2lgN
	 * @param arr 查询的数组
	 * @return 数组中三个数字和为0的组数
	 */
	public static int threeSum(int[] arr) {
		Arrays.sort(arr);
		int count = 0;
		for (int i = 0; i < arr.length; i++)
			for (int j = i + 1; j < arr.length; j++)
				if (binarySearch(arr, -arr[i] - arr[j]) > j)
					count++;
		return count;
	}

	public static void main(String[] args) {
		 int arr[] = { -4, 6, 5, 1, 2, 8, 0, 3, -10, 4, 10, -2 };
		//int arr[] = { 0, 2, 4, 6, 9, 11, 17, 30, 31, 50 };
		System.out.println(threeSum(arr));
	}
}
