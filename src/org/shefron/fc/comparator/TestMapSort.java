package org.shefron.fc.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * HashTable,HashMap,LinkedHashMap,TreeMap
 * 
 * @author Administrator
 * 
 */
public final class TestMapSort {

	public static void testTreeMap() {
		// 默认按升序排列
		Map map = new TreeMap<String, String>();
		map.put("eee", "555");
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ddd", "444");
		map.put("ccc", "333");

		testPrintMap(map);

	}

	public static void testLinkedHashMap() {
		// 保持默认的插入次序
		Map map = new LinkedHashMap<String, String>();
		map.put("eee", "555");
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ddd", "444");
		map.put("ccc", "333");

		testPrintMap(map);
	}

	public static void testHashMap() {
		// hash无序排列
		Map map = new HashMap<String, String>();
		map.put("eee", "555");
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ddd", "444");
		map.put("ccc", "333");

		testPrintMap(map);
	}

	public static void testHashMapDesc() {
		// 按key降序排列,按value排序类似
		Map map = new HashMap<String, String>();
		map.put("eee", "555");
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ddd", "444");
		map.put("ccc", "333");

		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(
				entrySet);
		Collections.sort(entryList,
				new Comparator<Map.Entry<String, String>>() {

					public int compare(Entry<String, String> o1,
							Entry<String, String> o2) {
						// 按key降序
						return o2.getKey().compareToIgnoreCase(o1.getKey());
						// 按key升序
						// return o1.getKey().compareToIgnoreCase(o2.getKey());

					}

				});

		testPrintEntryList(entryList);

	}

	public static void testTreeMapDesc() {
		// 此方式只能为key值进行排序
		Map<String, String> map = new TreeMap<String, String>(
				new Comparator<String>() {

					public int compare(String o1, String o2) {
						// 降序
						return o2.compareToIgnoreCase(o1);
						// 升序
						// return o1.compareToIgnoreCase(o2);
					}

				});

		map.put("eee", "555");
		map.put("aaa", "111");
		map.put("bbb", "222");
		map.put("ddd", "444");
		map.put("ccc", "333");

		testPrintMap(map);
	}

	public static void testPrintMap(Map map) {
		Set<Map.Entry<String, String>> entrySet = map.entrySet();
		int seq = 1;
		for (Map.Entry<String, String> entry : entrySet) {
			System.out.println("seq=" + (seq++) + "\nkey=" + entry.getKey()
					+ ",value=" + entry.getValue());
		}
	}

	public static void testPrintEntryList(
			List<Map.Entry<String, String>> entryList) {
		int seq = 1;
		for (Map.Entry<String, String> entry : entryList) {
			System.out.println("seq=" + (seq++) + "\nkey=" + entry.getKey()
					+ ",value=" + entry.getValue());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// testTreeMap();

		// testLinkedHashMap();

		// testHashMap();

		// testHashMapDesc();

		testTreeMapDesc();

	}

}
