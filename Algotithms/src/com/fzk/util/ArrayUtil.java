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
	 * 2-Sum问题（二分查找法）
	 * 默认数组中没有重复数字
	 * 
	 * 来源：1.4.5.1节
	 * 复杂度：NlgN
	 * @param arr 查询的数组
	 * @param target 指定的和
	 * @return 数组中两个数字和为target的组数
	 */
	public static int twoSum(int[] arr, int target) {
		Arrays.sort(arr); // 归并排序，NlgN
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (binarySearch(arr, target - arr[i], i, arr.length - 1) > i) { // 二分查找，lgN
				count++;
			}
		}
		return count;
	}

	/**
	 * 2-sum问题（前后游标法）
	 * 
	 * @param arr
	 * @param target
	 * @return
	 */
	public static int twoSum2(int[] arr, int target) {
		Arrays.sort(arr); // NlgN
		int count = 0;
		int low = 0; // 定义前后游标
		int high = arr.length - 1;
		while (low < high) {
			int result = arr[low] + arr[high];
			if (result == target) {
				count++;
				// 确保前后游标不会重复
				while (low < high && arr[low] == arr[low + 1]) low++;
				while (low < high && arr[high] == arr[high - 1]) high--;
				low++;
				high--;
			} else if (result < target) {
				low++;
			} else {
				high--;
			}
		}
		return count;
	}
	
	/**
	 * 3-Sum问题
	 * 
	 * 来源：练习1.4.15
	 * 复杂度：N^2
	 * @param arr 查询的数组
	 * @param target 指定的和
	 * @return 数组中三个数字和为target的组数
	 */
	// 思路：3-sum问题可以转化为2-sum问题。
	// 首先，取数组中第一个数字，作为基准数m，下一步就是在后面的数字中的2-sum问题：找到两个数字和为target-m。
	// 接着基数遍历整个数组即可。
	// 由于要确保没有重复结果，因此：1)基数不能重复，2)首尾游标不能重复。要在每次这3个数字改变的时候进行判断。
	private static int threeSum(int arr[], int target) {
		if (arr == null || arr.length < 3)
			return 0;
		Arrays.sort(arr);
		int count = 0;
		for (int i = 0; i < arr.length - 2; i++) { // i是基准数
			if (i > 0 && arr[i] == arr[i - 1]) // 确保基准数不会重复
				continue;

			int low = i + 1; // 从基准数开始到数组结束，首尾开始扫描
			int high = arr.length - 1;
			while (low < high) {
				int result = arr[i] + arr[low] + arr[high];
				if (result == target) { // 找到了
					System.out.println(arr[i] + "+" + arr[low] + "+" + arr[high]);
					count++;

					while (low < high && arr[low] == arr[low + 1]) low++;	// 确保low游标不会重复
					while (low < high && arr[high] == arr[high - 1]) high--;	// 确保high游标不会重复

					low++;
					high--;
				} else if (result < target) { // 计算结果较小，low向后移一位
					low++;
				} else { // 计算结果较大，high向前移一位
					high--;
				}
			}
		}
		return count;
	}

	/**
	 * 转换为标准格式
	 * 
	 * @param arr 要转换的数组
	 * @return 转换后的字符串
	 */
	public static String toString(int[] arr) {
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i != arr.length - 1)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 标准格式打印
	 * 
	 * @param arr 要打印的数组
	 */
	public static void print(int[] arr) {
		System.out.println(toString(arr));
	}

	public static void main(String[] args) {
		 int arr[] = { -4, 6, 5, 1, 2, 8, 0, 3, -10, 4, 10, -2 };
		//int arr[] = { 0, 2, 4, 6, 9, 11, 17, 30, 31, 50 };
		System.out.println(threeSum(arr,0));
	}
}
