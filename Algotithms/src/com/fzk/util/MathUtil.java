package com.fzk.util;

/**
 * 数学工具类
 * 
 * @author fanzhoukai
 * 
 */
public class MathUtil {
	/**
	 * 转换为二进制字符串，仅支持非负数
	 * 来源：《算法第四版》习题1.1.9
	 */
	public static String toBinaryString(int value) {
		if (value == 0)
			return "0";
		StringBuffer sb = new StringBuffer();
		for (; value > 0; value >>= 1)
			sb.insert(0, value % 2);
		return sb.toString();
	}

	/**
	 * 二维数组转置（交换行和列）
	 * 来源：《算法第四版》习题1.1.13
	 */
	public static int[][] transfer(int[][] arr) {
		if (arr == null)
			return null;
		int height = arr.length;
		int width = arr[0].length;
		int[][] result = new int[width][height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result[j][i] = arr[i][j];
			}
		}
		return result;
	}
}
