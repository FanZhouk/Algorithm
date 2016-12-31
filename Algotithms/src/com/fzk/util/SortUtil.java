package com.fzk.util;

/**
 * 排序工具类
 * 
 * @author fanzhoukai
 * 
 */
public class SortUtil {
	/**
	 * 选择排序
	 * 
	 * 复杂度：~N^2/2
	 * 主要思想：循环数组，找出最小的，与第一个元素交换；找出第二小的，与第二个元素交换...
	 * 
	 * @param arr  待排序的数组
	 */
	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			int minIndex = i;
			int minValue = arr[i];
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < minValue) {
					minIndex = j;
					minValue = arr[j];
				}
			}
			// 交换arr[i]和arr[minIndex]
			int tmp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = tmp;
		}
	}

	/**
	 * 插入排序
	 * 
	 * 复杂度（平均）：~N^2/4
	 * 主要思想：把数组分为两个区域：左边为有序区，右边为无序区。
	 * 循环数组，每次取无序区的第一个，插入到有序区中的相应位置上去，比它大的元素各后移一位。
	 * 直到整个数组都变为有序区为止。
	 * 
	 * @param arr 要排序的数组
	 */
	public static void insertionSort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] >= arr[i - 1])
				continue;
			int val = arr[i]; // 要插入的数字
			int ind = i; // 要插入的位置
			while (ind > 0 && arr[ind - 1] > val) {
				arr[ind] = arr[ind - 1];
				ind--;
			}
			arr[ind] = val;
		}
	}

	/**
	 * 希尔排序（较直观的写法）
	 * 
	 * 主要思想：分组，组内多次插入排序。
	 * 如原数组长度为10，设定步长为10/2=5，则对索引为(0,5),(1,6),(2,7),(3,8),(4,9)的元素进行插入排序；
	 * 接着缩短步长为5/2=2，则对索引为(0,2,4,6,8),(1,3,5,7,9)的元素进行插入排序；
	 * 直至步长缩短为1，对整个数组进行插入排序，则希尔排序完成。
	 * 
	 * @param arr 要排序的数组
	 */
	public static void shellSort(int[] arr) {
		int gap = arr.length >> 1; // 步长
		for (; gap > 0; gap >>= 1) { // 缩短步长
			for (int count = 0; count < gap; count++) { // 分组，count为距离第一组的长度
				for (int i = gap + count; i < arr.length; i += gap) { // 组内插入排序
					if (arr[i] < arr[i - gap]) {
						int ind = i - gap;
						int val = arr[i];
						while (ind >= 0 && arr[ind] > val) {
							arr[ind + gap] = arr[ind];
							ind -= gap;
						}
						arr[ind + gap] = val;
					}
				}
			}
		}
	}

	/**
	 * 希尔排序（优化步长策略）
	 * 
	 * 初始步长变为3的倍数+1，缩短步长策略变为缩短为原来的1/3。
	 * 实验证明这种步长策略的效率要比第一种好。
	 * 
	 * @param arr 要排序的数组
	 */
	public static void shellSort2(int[] arr) {
		int gap = 1; // 步长
		while (gap < arr.length / 3) // 设定初始步长(1,4,13,40,121,364,1093...)
			gap = 3 * gap + 1;
		for (; gap > 0; gap /= 3) { // 缩短步长，策略为缩短为原来的1/3
			for (int count = 0; count < gap; count++) { // 分组，count为距离第一组的长度
				for (int i = gap + count; i < arr.length; i += gap) { // 组内插入排序
					if (arr[i] < arr[i - gap]) {
						int ind = i - gap;
						int val = arr[i];
						while (ind >= 0 && arr[ind] > val) {
							arr[ind + gap] = arr[ind];
							ind -= gap;
						}
						arr[ind + gap] = val;
					}
				}
			}
		}
	}

	/**
	 * 希尔排序（改变比较的顺序，减少循环次数）
	 * 
	 * 来源：http://blog.csdn.net/morewindows/article/details/6668714
	 * 
	 * @param arr
	 */
	public static void shellSort3(int[] arr) {
		int gap = arr.length >> 1;
		for (; gap > 0; gap >>= 1) {
			for (int j = gap; j < arr.length; j++) {
				if (arr[j] < arr[j - gap]) {
					int tmp = arr[j];
					int k = j - gap;
					while (k >= 0 && arr[k] > tmp) {
						arr[k + gap] = arr[k];
						k -= gap;
					}
					arr[k + gap] = tmp;
				}
			}
		}
	}

	/**
	 * 归并排序（自顶向下）
	 * 
	 * 主要思想：递归，合并。
	 * 若原数组长度为n，第一步归并排序 0 ~ n/2，第二步排序 n/2+1 ~ n-1，最后合并两个有序子数组。
	 * 
	 * @param arr
	 */
	public static void mergeSort(int[] arr) {
		int[] tmp = new int[arr.length];
		mergeSort(arr, 0, arr.length - 1, tmp);
	}

	private static void mergeSort(int[] arr, int low, int high, int[] tmp) {
		if (low >= high)
			return;
		int mid = (low + high) >> 1;
		mergeSort(arr, low, mid, tmp); // 递归左半部分
		mergeSort(arr, mid + 1, high, tmp); // 递归右半部分
		merge(arr, low, mid, high, tmp); // 合并
	}

	// 融合一个数组的两个有序子数组（low ~ mid 和 mid+1 ~ high 分别为两个有序子数组）
	// tmp为临时数组，不传递任何信息，只是为了防止重复创建，造成空间浪费
	public static void merge(int[] arr, int low, int mid, int high, int[] tmp) {
		int l = low, m = mid + 1; // 对两个子数组的索引
		int ind = low; // 对临时数组的索引

		for (; l <= mid && m <= high; ind++) {
			if (arr[l] < arr[m])
				tmp[ind] = arr[l++];
			else
				tmp[ind] = arr[m++];
		}
		while (l <= mid)
			tmp[ind++] = arr[l++];
		while (m <= high)
			tmp[ind++] = arr[m++];

		// 此时临时数组中已经是融合好的元素，把它们放回原数组即可
		for (int i = low; i <= high; i++)
			arr[i] = tmp[i];
	}

	public static void main(String[] args) {
		int[] arr = { 9, 3, 2, 4, 0, 8, 1, 5, 7, 6 };
		ArrayUtil.print(arr);
		//SortUtil.selectionSort(arr);
		//SortUtil.insertionSort(arr);
		//SortUtil.shellSort(arr);
		SortUtil.mergeSort(arr);
		ArrayUtil.print(arr);
	}
}
