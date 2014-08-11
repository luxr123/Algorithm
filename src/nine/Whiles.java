package nine;

import java.util.Arrays;
import java.util.Comparator;

public class Whiles {
	/**
	 * A和B是两个有序数组(假设为递增序列)，而且A的长度足以放下A和B中所有的元素， 写一个函数将数组B融入数组A，并使其有序
	 */
	// 我们从A和B的尾部元素开始对比，每次取大的元素放在数组A的尾部， 这样一来，要放入的位置就不会出现冲突问题。当对比结束时，
	// 即可得到一个排好序的数组A
	public static void merge(int[] a, int[] b, int m, int n) {
		int k = m + n - 1; // Index of last location of array b
		int i = m - 1; // Index of last element in array a
		int j = n - 1; // Index of last element in array b

		// Start comparing from the last element and merge a and b
		while (i >= 0 && j >= 0) {
			if (a[i] > b[j])
				a[k--] = a[i--];
			else
				a[k--] = b[j--];
		}
		while (j >= 0)
			a[k--] = b[j--];

	}

	/**
	 * 一个数组有n个整数，它们排好序(假设为升序)但被旋转了未知次， 即每次把最右边的数放到最左边。给出一个O(log n)的算法找到特定的某个元素
	 */
	// 如果a[mid]不等于x，就要分情况讨论了。我们把旋转后的数组分为前半段和后半段，
	// 两段分别都是有序的，且前半段的数都大于后半段的数。假如a[mid]落在前半段
	// (即a[mid]>=a[low])，这时如果x的值是位于a[low]和a[mid]之间， 则更新high = mid – 1；否则更新low =
	// mid + 1。假如a[mid]落在后半段 (即a[mid]<a[low])，这时如果x的值是位于a[mid]和a[high]之间， 则更新low
	// = mid + 1；否则更新high = mid – 1
	private static int search(int a[], int low, int high, int x) {
		while (low <= high) {
			int mid = (low + high) / 2;
			if (x == a[mid]) {
				return mid;
			} else if (a[low] <= a[mid]) {
				if (x >= a[low] && x < a[mid]) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			} else if (x > a[mid] && x <= a[high])
				low = mid + 1;
			else
				high = mid - 1;
		}
		return -1;
	}

	public static int search(int a[], int x) {
		return search(a, 0, a.length - 1, x);
	}

	/**
	 * 给你一个排好序的并且穿插有空字符串的字符串数组，写一个函数找到给定字符串的位置.
	 */
	public int search(String[] strings, String str) {
		if (strings == null || str == null)
			return -1;
		if (str == "") {
			for (int i = 0; i < strings.length; i++) {
				if (strings[i] == "")
					return i;
			}
			return -1;
		}
		return search(strings, str, 0, strings.length - 1);
	}

	public int search(String[] strings, String str, int first, int last) {
		while (first <= last) {
			// Ensure there is something at the end
			while (first <= last && strings[last] == "")
				--last;

			if (last < first)
				return -1; // this block was empty, so fail

			int mid = (last + first) >> 1;
			while (strings[mid] == "")
				++mid; // will always find one

			int r = strings[mid].compareTo(str);
			if (r == 0)
				return mid;
			if (r < 0)
				first = mid + 1;
			else
				last = mid - 1;
		}
		return -1;
	}

	/**
	 * 给出一个矩阵，其中每一行和每一列都是有序的，写一个函数在矩阵中找出指定的数。
	 * 我们假设这个矩阵每一行都是递增的，每一列也都是递增的，并把这些数据存入文件 9.6.in(如下)，其中开头的两个数5 5表示该矩阵是5*5的。
	 * 
	 * <pre>
	 * 1 2  3  4  5
	 * 3 7  8  9  11
	 * 5 9  10 17 18
	 * 7 12 15 19 23
	 * 9 13 16 20 25
	 * </pre>
	 */
	// 这个矩阵是有序的，因此我们要利用这一点，当矩阵中元素和要查找的元素对比后，
	// 如果相等，我们返回下标；如果不等，我们就排除掉一些元素，而不仅仅是对比的元素。 我们从矩阵的四个角的元素入手看看，有什么特点。左上角是最小的，
	// 因为矩阵向右是递增的，向下也是递增的。同理，右下角是最大的。让我们来看看右上角，
	// 设要查找的元素为x，比如x比右上角元素5大，那么它也会比第一行的其它元素都大。
	// 因此可以去掉第一行；如果它比右上角元素小，那么它也会比最后一列的元素都小，
	// 因此可以去掉最后一列；然后这样不断地比下去，只需要O(m+n)的时间就查找完。 对于左下角的元素，也有一样的特点。就不再分析了。
	boolean findElement(int[][] mat, int elem, int M, int N) {
		int row = 0;
		int col = N - 1;
		while (row < M && col >= 0) {
			if (mat[row][col] == elem) {
				return true;
			} else if (mat[row][col] > elem) {
				col--;
			} else {
				row++;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String[] array = { "live", "ilve", "ab", "ba", "cv", "vc" };
		// Now, just sort the arrays, using this compareTo method instead of the
		// usual one
		 Arrays.sort(array, new AnagramComparator());
		 for (String s : array)
		 System.out.println(s);

//		int a[] = { 15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };
		// 对于有重复元素的数组，上面的算法失效
//		int b[] = { 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
//		System.out.println(search(a, 3));
	}
}

/**
 * 写一个函数对字符串数组排序，使得所有的变位词都相邻。 (变位词就是组成的字母相同，但顺序不一样的单词。 比如说：live和evil就是一对变位词)
 */
class AnagramComparator implements Comparator<String> {
	public String sortChars(String s) {
		char[] content = s.toCharArray();
		Arrays.sort(content);
		return new String(content);
	}

	public int compare(String s1, String s2) {
		return sortChars(s1).compareTo(sortChars(s2));
	}
}
