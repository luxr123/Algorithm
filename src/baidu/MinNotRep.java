package baidu;

import java.util.Scanner;

/**
 * 给定N是一个正整数，求比N大的最小“不重复数”，这里的不重复是指没有两个相等的相邻位，如1102中的11是相等的两个相邻位故不是不重复数，
 * 而12301是不重复数。
 */
public class MinNotRep {

	/**
	 * 算法思想：当然最直接的方法是采用暴力法，从N+1开始逐步加1判断是否是不重复数，是就退出循环输出，这种方法一般是不可取的，例如N=11000000
	 * ，你要一个个的加1要加到12010101，一共循环百万次，每次都要重复判断是否是不重复数，效率极其低下，因此是不可取的。
	 * 
	 * <pre>
	 * 这里我采用的方法是：从N+1的最高位往右开始判断与其次高位是否相等，如果发现相等的（即为重复数）则将次高位加1，注意这里可能进位，如8921—>9021，
	 * 后面的直接置为010101...形式，如1121—>1201，此时便完成“不重复数”的初步构造，但此时的“不重复数”不一定是真正的不重复的数，
	 * 因为可能进位后的次高位变为0或进位后变成00 ，如9921—>10001，此时需要再次循环判断重新构造直至满足条件即可，这种方法循环的次数比较少，可以接受。
	 * </pre>
	 */
	public static void minNotRep(int n) {
		// 需要多次判断
		while (true) {
			int[] a = new int[20];
			int len = 0, i, b = 0;
			// flag为true表示是“重复数”，为false表示表示是“不重复数”
			boolean flag = false;

			// 将n的各位上数字存到数组a中
			while (n > 0) {
				a[len++] = n % 10;
				n = n / 10;
			}

			// 从高位开始遍历是否有重复位
			for (i = len - 1; i > 0; i--) {
				// 有重复位则次高位加1（最高位有可能进位但这里不需要额外处理）
				if (a[i] == a[i - 1] && !flag) {
					a[i - 1]++;
					flag = true;
				} else if (flag) {
					// 将重复位后面的位置为0101...形式
					a[i - 1] = b;
					b = (b == 0) ? 1 : 0;
				}
			}

			// 重组各位数字为n，如果是“不重复数”则输出退出否则继续判断
			for (i = len - 1; i >= 0; i--) {
				n = n * 10 + a[i];
			}

			if (!flag) {
				System.out.format("%d\n", n);
				break;
			}
		}
	}

	public static void main(String[] args) {
		int N;
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextInt()) {
			N = scan.nextInt();
			minNotRep(N + 1);
		}
	}
}
