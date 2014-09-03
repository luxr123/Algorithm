package recursion;

import java.util.Arrays;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月3日 下午2:16:49]
 * @Version: [v1.0]
 * 
 */
public class Rotate {

	/**
	 * 第二章“啊哈！算法”中作者给出三个“小题目”，其中第二个是：将一个具有n个元素的一维向量x向左旋转i个位置。例如，将 ABC123DEF456
	 * 向左旋转 3 个位置之后得到 123DEF456ABC。要求是花费与n成比例的时间来完成这个操作。
	 */
	// 原语操作-反转数组 亦称,手摇法,见图 手摇法.jpg
	private void reverse(char[] arr, int start, int end) {
		int mid = (start + end) / 2;
		for (int s = start, i = 0; s < mid; s++, i++) {
			int swapIndex = end - 1 - i;
			char swap = arr[swapIndex];
			arr[swapIndex] = arr[s];
			arr[s] = swap;
		}
	}

	public void rotate(char[] arr, int len, int m) {
		reverse(arr, 0, m);// 翻左手
		reverse(arr, m, len);// 翻右手
		reverse(arr, 0, len);// 翻左右手
	}

	boolean isRotation(String s1, String s2) {
		return (s1.length() == s2.length()) && ((s1 + s1).indexOf(s2) != -1);
	}

	public static void main(String[] args) {
		int m = 3;
		String s1 = "ABC123DEF456";
		char[] arr = s1.toCharArray();
		System.out.println(Arrays.toString(arr));
		Rotate rotate = new Rotate();
		rotate.rotate(arr, arr.length, m);// 123DEF456ABC
		System.out.println(Arrays.toString(arr));

		String s2 = "123DEF456ABC";
		System.out.println(rotate.isRotation(s1, s2));
	}
}
