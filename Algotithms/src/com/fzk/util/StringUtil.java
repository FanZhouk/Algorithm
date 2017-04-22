package com.fzk.util;

import com.fzk.adt.LinkedStack;
import com.fzk.adt.Stack;

/**
 * 字符串工具类
 * 
 * @author fanzhoukai
 * 
 */
public class StringUtil {

	/**
	 * 计算输入的四则运算（未区分运算符的优先级，乘除与加减混用时必须带括号）。 暂时只支持输入个位数字。
	 * 如输入：1+2-((3*4)/(8/4))
	 * 
	 * @param str 要计算的表达式
	 * @return 计算结果
	 */
	public static double calculate(String str) {
		if (str == null || str.length() == 0)
			throw new IllegalStateException();

		Stack<Double> vals = new LinkedStack<Double>(); // 数字栈
		Stack<Character> opts = new LinkedStack<Character>(); // 操作符栈
		double result = 0;
		for (int count = 0; count < str.length(); count++) {
			char val = str.charAt(count);
			if (val == '(') // 左括号，不做任何操作
				;
			else if (val == '+' || val == '-' || val == '*' || val == '/') // 四则运算符号，压入操作符栈中
				opts.push(val);
			else if (String.valueOf(val).matches("\\d")) { // 数字，压入数字栈中
				double realVal = Integer.parseInt(String.valueOf(val));
				// TODO 处理多位数字的情况
				vals.push(realVal);
			} else if (val == ')') { // 右括号，进行计算
				result = calculateOnce(vals, opts);
			} else
				throw new IllegalStateException();// 否则输入错误
		}

		// 以下步骤为防止连加(1+2+3+4)情况出现，计算两栈中剩余的数字
		while (!vals.isEmpty() && !opts.isEmpty()) {
			result = calculateOnce(vals, opts);
		}
		return result;
	}

	private static double calculateOnce(Stack<Double> vals, Stack<Character> opts) {
		double result;
		double second = vals.pop(); // 后一个操作数
		double first = vals.pop(); // 前一个操作数
		char optor = opts.pop(); // 操作符
		switch (optor) {
		case '+': result = first + second; break;
		case '-': result = first - second; break;
		case '*': result = first * second; break;
		case '/': result = first / second; break;
		default: result = 0; break;
		}
		vals.push(result);
		return result;
	}

	public static void main(String[] args) {
		double a = StringUtil.calculate("1+1+1+2-((9*8)/(8/4))");
		System.out.println(a);
	}
}
