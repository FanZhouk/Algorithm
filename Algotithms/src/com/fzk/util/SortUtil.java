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
	 * 希尔排序
	 * 
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		for (int h = arr.length >> 1; h > 0; h/=3) { // h为增量，构成一个h有序数组
			for (int i = h; i < arr.length; i += h) {
				if (arr[i] > arr[i - h])
					continue;
				int ind = i;
				int val = arr[i];
				while (ind > 0 && arr[ind - h] > val) {
					arr[ind] = arr[ind - h];
					ind -= h;
				}
				arr[ind] = val;
			}
		}
	}

	public static void shellSort3(int[] arr) {
		for (int h = arr.length >> 1; h > 0; h >>= 1) { // h为步长（增量），构成一个h有序数组
			for (int k = 0; k < h; k++) { // k为h内递增的量
				for (int i = h + k; i < arr.length; i += h) {
					if (arr[i] > arr[i - h])
						continue;
					int ind = i - h;
					int val = arr[i];
					while (ind > 0 && arr[ind] > val) {
						arr[ind + h] = arr[ind];
						ind -= h;
					}
					arr[ind + h] = val;
				}
			}
		}
	}
	
	/**
	 * 希尔排序（书中）
	 */
	public static void shellSort2(int[] arr) {
		int i,pointer;
		int index = arr.length;
		int h = index / 2;
		while (h != 0) {
			for (i = h; i < index; i++) {
				int tmp = arr[i];
				pointer = i - h;
				while (tmp < arr[pointer] && pointer >= 0 && pointer <= index) {
					arr[pointer + h] = arr[pointer];
					pointer = pointer - h;
					if (pointer < 0 || pointer > index) {
						break;
					}
					arr[pointer + h] = tmp;
					
				}
				h = h / 2;
			}
		}
	}
	
	
	public static void main(String[] args) {
		int[] arr = { 9, 3, 2, 4, 0, 8, 1, 5, 7, 6 };
		ArrayUtil.print(arr);
		SortUtil.selectionSort(arr);
		//SortUtil.insertionSort(arr);
		ArrayUtil.print(arr);
	}
}
