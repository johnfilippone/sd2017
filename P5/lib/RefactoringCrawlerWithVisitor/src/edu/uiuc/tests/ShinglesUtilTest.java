package edu.uiuc.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import edu.uiuc.detectRefactorings.util.ShinglesUtil;

public class ShinglesUtilTest extends TestCase {
	public void testTokenizer() {
		String str = "int x = 3;\n int y = 5;\n return x + y;";
		ShinglesUtil su = new ShinglesUtil();
		List list = su.tokenizer(str);
		// System.out.println(list.toString());
	}

	public void testShing() {
		String str = "integer x = 3;\n integer y = 5;\n return x + y;";
		ShinglesUtil su = new ShinglesUtil();
		int[] list =   su.computeMethodShingles(str, 3, 8);

//		System.out.println("\n");
		String str2 = "int x = 3;\n int y = 5;\n return x + y;";
		ShinglesUtil su2 = new ShinglesUtil();
		int[] list2 =   su2.computeMethodShingles(str2, 3, 8);

		// System.out.println("list size: " + list.length + ", list2 size: "
		// + list2.length);
	}

	public void testDifferent() {
		String str = "integer x = 3;\n integer y = 5;\n return x + y;";
		ShinglesUtil su = new ShinglesUtil();
		int[] list =   su.computeMethodShingles(str, 3, 8);
		// printArray(list);

		String str2 = "int x = 3;\n int y = 5;\n return x + y;";
		int[] list2 =   su.computeMethodShingles(str2, 3, 8);
		// printArray(list2);
		// System.out.println("Are they similar: " + su.areSimilar(list,
		// list2));

		 assertFalse(su.howMuchAlike(list, list2) > 0.75);
	}

	public void testSame() {
		String str = "int x = 3;\n int y = 5;\n return x + y;";
		ShinglesUtil su = new ShinglesUtil();
		int[] list =   su.computeMethodShingles(str, 3, 8);

		String str2 = "int x = 3;\n int y = 5;\n return x + y;";
		int[] list2 =   su.computeMethodShingles(str2, 3, 8);
		// System.out.println("Are they similar: " + su.areSimilar(list,
		// list2));

		 assertTrue(su.howMuchAlike(list, list2) > 0.75);
	}

	public void testSimilar() {
		String str = "integer x = 3;\n int y = 5;\n return x + y;";
		ShinglesUtil su = new ShinglesUtil();
		int[] list =   su.computeMethodShingles(str, 3, 8);

		String str2 = "int x = 3;\n int y = 5;\n return x + y;";
		int[] list2 =   su.computeMethodShingles(str2, 3, 8);

		 assertTrue(su.howMuchAlike(list, list2) > 0.75);
	}

	public void testLarge() {
		String str = "String str = \"integer x = 3;\n int y = 5;\n return x + y;\"; ShinglesUtil su = new ShinglesUtil(); int[] list = su.computeShingles(str, 3, 8); String str2 = \"int x = 3;\n int y = 5;\n return x + y;\"; int[] list2 = su.computeShingles(str2, 3, 8); System.out.println(\"Are they similar: \" + su.areSimilar(list, list2)); assertTrue(su.areSimilar(list, list2));";
		String str2 = "String str = \"integer x = 3;\n int y = 5;\n return x + y;\"; ShinglesUtil shinglesUt = new ShinglesUtil(); integer[] list = su.computeShingles(str, 3, 8); String str2 = \"int x = 3;\n int y = 5;\n return x + y;\"; int[] list2 = su.computeShprintln(\"Something goes in here I guess \" + su.areSimilar(list, list2)); assertTrue(su.areSimilar(list, list2));";
		ShinglesUtil su = new ShinglesUtil();
		int[] arr1 =   su.computeMethodShingles(str, 3, 15);
		int[] arr2 =   su.computeMethodShingles(str2, 3, 15);
//		printArray(arr1);
//		printArray(arr2);

		 assertTrue(su.howMuchAlike(arr1, arr2) > 0.75);
	}

	 private void printArray(int[] arr) {
		System.out.print("[ ");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]);
			System.out.print(", ");
		}
		System.out.println("]");
	}
	
	public void testStupid() {
		int[] x = new int[3];
		x[0] = x[1] = x[2] = 2;
		int[] y = new int[3];
		y [0]= y[1] = y[2] = 2;
		 assertTrue(Arrays.equals(x, y));
		
		List l = new ArrayList();
		l.add(x);
		assertTrue(l.contains(x));
		assertFalse(l.contains(y));
		 assertTrue(ShinglesUtil.isThisArrayInTheList(l, y));
		
		String[] s = {"a", "b"};
		String[] k = {"a", "b"};
		List l2 = new ArrayList();
		l2.add(s);
		 assertTrue(Arrays.equals(s, k));
	}
	
	public void testShingForPaper() {
		String str = "int x = 3; string y = \"hello, world\"; if(x > 2) return x + y; else return x;";
		ShinglesUtil su = new ShinglesUtil();
		int[] list =   su.computeMethodShingles(str, 2, 10);

		String str2 = "int x = 3; string new = \"hello, world\"; if(x > 2) return x + new; else return x;";
		ShinglesUtil su2 = new ShinglesUtil();
		int[] list2 =   su2.computeMethodShingles(str2, 2, 10);
		
		printArray(list);
		printArray(list2);
	}
	
	
}