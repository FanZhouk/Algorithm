package com.fzk;


import java.util.Iterator;

import com.fzk.adt.LinkedList;
import com.fzk.adt.List;

public class Test {
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("5");
		list.add("6");

		/*LinkedList<String> list2 = new LinkedList<String>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		
		list.addAll(list2);
		*/
		List<String> list2 = list.subList(0, 11);
		
		
		Iterator<String> it = list2.iterator();
		while(it.hasNext())
			System.out.println(it.next());
		
	}

}
