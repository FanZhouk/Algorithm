package com.fzk.util;

import com.fzk.adt.ArrayStack;
import com.fzk.adt.LinkedStack;
import com.fzk.adt.Stack;

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
	 * 中序表达式求值——双栈求值法
	 * 只支持输入个位数字，未区分运算符的优先级，任意计算结果都需要加括号。
	 * 如："(1+((2+3)*(4*5)))"
	 * 
	 * @param str 要计算的表达式
	 * @return 计算结果
	 */
	public static double evaluate(String str) {
		if (str == null || str.length() == 0)
			throw new IllegalStateException();
	
		Stack<Character> operators = new LinkedStack<Character>(); // 操作符栈
		Stack<Double> numbers = new LinkedStack<Double>(); // 数字栈
	
		double result = 0;
		for (int i = 0; i < str.length(); i++) {
			String curr = str.substring(i, i + 1);
			if (curr.matches("\\d")) { // 数字，压入数字栈
				numbers.push(Double.valueOf(curr));
			} else if (curr.matches("[\\+\\-\\*\\/]")) { // 操作符，压入操作符栈
				operators.push(curr.charAt(0));
			} else if (curr.equals(")")) { // 右括号，进行计算
				double secondNum = numbers.pop();
				double firstNum = numbers.pop();
				char operator = operators.pop();
				switch (operator) {
					case '+': result = firstNum + secondNum; break;
					case '-': result = firstNum - secondNum; break;
					case '*': result = firstNum * secondNum; break;
					case '/': result = firstNum / secondNum; break;
					default: break;
				}
				numbers.push(result);
			}
		}
		return numbers.pop();
	}

	/**
	 * 后序表达式求值
	 * 支持多位数，数字中间以空格分隔
	 * 如："9 3 1-3*+10 2/+"
	 * 
	 * @param str 要计算的表达式
	 * @return 计算结果
	 */
	public static double evaluatePostfix(String postfix) {
		if (postfix == null || postfix.length() == 0)
			return 0;

		Stack<Double> numbers = new ArrayStack<Double>(); // 数字栈

		int cursor = 0;
		while (cursor < postfix.length()) {
			String character = postfix.substring(cursor, cursor + 1); // 当前字符
			if (character.matches("\\s")) { // 是空格
				cursor++;
			} else if (character.matches("\\d")) { // 是数字
				StringBuffer sb = new StringBuffer(character);
				String next;
				// 处理多位数的情况
				while (++cursor < postfix.length() && (next = postfix.substring(cursor, cursor + 1)).matches("\\d"))
					sb.append(next);
				numbers.push(Double.valueOf(sb.toString()));
			} else if (character.matches("[\\+\\-\\*\\/]")) { // 是操作符
				double secondNum = numbers.pop();
				double firstNum = numbers.pop();
				double result;
				switch (character) {
					case "+": result = firstNum + secondNum; break;
					case "-": result = firstNum - secondNum; break;
					case "*": result = firstNum * secondNum; break;
					case "/": result = firstNum / secondNum; break;
					default: result = 0; break;
				}
				numbers.push(result);
				cursor++;
			} else { // 其他字符
				throw new IllegalArgumentException(character);
			}
		}
		return numbers.pop();
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
