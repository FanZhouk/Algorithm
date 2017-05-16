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
	 */
	public static void selectionSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
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

	// 带索引的插入排序，对小数组尤其适用
	// 可用于归并、快排等算法内部，排序小子数组的时候，以缩减递归深度
	public static void insertionSort(int[] arr, int low, int high) {
		for (int i = low + 1; i <= high; i++) {
			if (arr[i] >= arr[i - 1])
				continue;
			int val = arr[i]; // 要插入的数字
			int ind = i; // 要插入的位置
			for (; ind > low && arr[ind - 1] > val; ind--) {
				arr[ind] = arr[ind - 1];
			}
			arr[ind] = val;
		}
	}

	/**
	 * 希尔排序
	 * 
	 * 主要思想：分组，组内多次插入排序。
	 */
	public static void shellSort(int[] arr) {
		int N = arr.length;
		int h = 1;
		while (h < N / 3)
			h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, 1093...
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
					int tmp = arr[j];
					arr[j] = arr[j - h];
					arr[j - h] = tmp;
				}
			}
			h = h / 3;
		}
	}

	// 希尔排序（改变比较的顺序，减少循环次数）
	// 来源：http://blog.csdn.net/morewindows/article/details/6668714
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
	 * 复杂度：~NlgN
	 * 主要思想：递归，合并。
	 * 若原数组长度为n，第一步归并排序 0 ~ n/2，第二步排序 n/2+1 ~ n-1，最后合并两个有序子数组。
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

	/**
	 * 融合一个数组的两个有序子数组（low ~ mid 和 mid+1 ~ high 分别为两个有序子数组）
	 * 
	 * @param arr 原数组
	 * @param low 起始位置
	 * @param mid 第一段待归并数组结束位置
	 * @param high 终止位置
	 * @param tmp 临时数组，不传递任何信息，只是为了防止重复创建，造成空间浪费
	 */
	public static void merge(int[] arr, int low, int mid, int high, int[] tmp) {
		int l = low, m = mid + 1; // 对两个子数组的索引
		int ind = low; // 对临时数组的索引

		while (l <= mid && m <= high)
			tmp[ind++] = arr[l] > arr[m] ? arr[m++] : arr[l++];
		while (l <= mid)
			tmp[ind++] = arr[l++];
		while (m <= high)
			tmp[ind++] = arr[m++];

		// 此时临时数组中已经是融合好的元素，把它们放回原数组即可
		for (int i = low; i <= high; i++)
			arr[i] = tmp[i];
	}

	/**
	 * 归并排序（自底向上）
	 * 第一轮将数组两两归并，即保证arr[0]<arr[1],arr[2]<arr[3]...
	 * 第二轮将数组四四归并，即保证数组索引为0~3,4~7,8~11分别为有序。直至整个数组变为有序。
	 */
	public static void mergeSort2(int[] arr) {
		int len = arr.length;
		int[] tmp = new int[len];
		for (int h = 1; h < len; h <<= 1) // 首次归并大小
			for (int low = 0; low < len - h; low += h + h)
				merge(arr, low, low + h - 1, Math.min(low + h + h - 1, len - 1), tmp);
	}

	/**
	 * 快速排序
	 * 
	 * 复杂度：NlgN
	 * 主要思想：分治策略。
	 * 选择一个基准值（pivot），第一趟循环让所有小于pivot的值放在左边，大于pivot的值放在右边，而pivot放在最终位置。
	 * 接着递归左边和右边即可。
	 */
	public static void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int low, int high) {
		if (low >= high)
			return;
		int pivotIndex = partition(arr, low, high);
		quickSort(arr, low, pivotIndex - 1);
		quickSort(arr, pivotIndex + 1, high);
	}

	// 分割数组
	// 使low位置的元素处在正确位置，且左边都比它小，右边都比它大
	// 此处使用default权限，因为在com.fzk.util.ArrayUtil.findMedian(int[])中也有使用
	static int partition(int[] arr, int low, int high) {
		int pivotValue = arr[low];
		while (low < high) {
			while (low < high && arr[high] > pivotValue)
				high--;
			if (low < high)
				arr[low++] = arr[high];
			while (low < high && arr[low] < pivotValue)
				low++;
			if (low < high)
				arr[high--] = arr[low];
		}
		arr[low] = pivotValue;
		return low;
	}

	public static void main(String[] args) {
		int[] arr = { 9, 3, 2, 4, 0, 8, 1, 5, 7, 6 };
		ArrayUtil.print(arr);
		// SortUtil.selectionSort(arr);
		// SortUtil.insertionSort(arr);
		// SortUtil.shellSort(arr);
		// SortUtil.mergeSort(arr);
		SortUtil.quickSort(arr);
		ArrayUtil.print(arr);
	}
}
