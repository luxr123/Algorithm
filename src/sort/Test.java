package sort;
import java.util.LinkedList;

public class Test {

	/**
	 * 递归方法DecimalToBinary，把一个十进制数转换成二进制数
	 */

	public static String decimalToBinary(int num) {
		if (num == 0)
			return "0";
		return decimalToBinary(num / 2) + num % 2;
	}

	/**
	 * 递归方法sum，求1+2+...+100 的求和
	 */
	public static int sum(int num) {
		if (num == 0)
			return 0;
		return num + sum(num - 1);
	}

	/**
	 * 递归方法yueshu,求两个数的最大公约数 ,用两个数的绝对值与这两个数较小的那个一直比较，直到相等为止。
	 */
	public static void yueshu(int num1, int num2) {
		if (num1 == num2)
			System.out.println(num1);
		else
			yueshu(abs(num1 - num2), min(num1, num2));
	}

	private static int min(int num1, int num2) {
		return num1 > num2 ? num2 : num1;
	}

	private static int abs(int i) {
		return i > 0 ? i : -i;
	}

	/**
	 * 汉诺塔问题
	 */
	public static void hanon(int n, char a, char b, char c) {
		if (n == 1)
			move(1, a, c);
		else {
			hanon(n - 1, a, c, b);
			move(n, a, c);
			hanon(n - 1, b, a, c);
		}

	}

	public static int josephus(int n, int m) {
		if (n == 1)
			return 0;
		return (m + josephus(n - 1, m)) % n;
	}

	public static int josephus2(int n, int m) {
		LinkedList<Integer> people = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) {
			people.add(i);
		}
		m -= 1;
		int k = m % n;
		while (people.size() > 1) {
			System.out.println(people.remove(k));
			k = (k + m) % people.size();
		}
		return people.pop();
	}

	/**
	 * m的n次方
	 */
	public static double pow(double m, int n) {
		double res;
		if (n == 0)
			return 1;
		if (n > 0) {
			if (n % 2 == 0) {
				res = pow(m, n / 2);
				return res * res;
			} else {
				return m * pow(m, n - 1);
			}
		} else
			return 1 / pow(m, -n);

	}

	private static void move(int i, char a, char c) {
		System.out.println(i + ":" + a + "-->" + c);// 打印移动盘子情况
	}

	/**
	 * 快排 分区
	 */

	public static int partiton(int[] arr, int start, int end) {
		int pivot = arr[end];
		int i = start - 1;
		int tmp;
		for (int j = start; j < end; j++) {
			if (arr[j] < pivot) {
				tmp = arr[++i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}
		tmp = arr[++i];
		arr[i] = arr[end];
		arr[end] = tmp;
		return i;
	}

	public static void quickSort(int[] arr, int start, int end) {
		if (start < end) {
			int mid = partiton(arr, start, end);
			quickSort(arr, start, mid - 1);
			quickSort(arr, mid + 1, end);
		}
	}

	public static void main(String[] args) {
		// System.out.println(sum(5));
		// yueshu(15, 225);
//		System.out.println(josephus2(5, 3));

		// System.out.println(pow(2, -3));
		
		int[] arr = { 2, 1, 8, 4, 5, 10, 17, 19, 18, 6 };
		// System.out.println(partition(arr, 0, 9));
		quickSort(arr, 0, 9);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
