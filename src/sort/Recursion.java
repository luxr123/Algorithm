package sort;
import java.util.LinkedList;

public class Recursion {

	/**
	 * 递归方法DecimalToBinary，把一个十进制数转换成二进制数
	 */

	public static String decimalToBinary(int num) {
		String res = "";
		if (num == 0)
			return "";
		res = decimalToBinary(num / 2); // 调用递归方法
		// System.out.print(num % 2);
		return res + num % 2;
	}

	/**
	 * 递归方法sum，求1+2+...+100 的求和
	 */
	public static int sum(int num) {
		if (num > 0)
			return num + sum(num - 1); // 调用递归方法
		else
			return 0;// 当num=0时，循环结束
	}

	/**
	 * 递归方法yueshu,求两个数的最大公约数 ,用两个数的绝对值与这两个数较小的那个一直比较，直到相等为止。
	 */
	public static void yueshu(int num1, int num2) {
		if (num1 == num2)
			System.out.println(num1); // num1=num2时，结束
		else
			yueshu(abs(num1 - num2), min(num1, num2));
	}

	// 求两个数绝对值
	public static int abs(int num) {
		return num > 0 ? num : -num;
	}

	// 求两个数较小者
	public static int min(int num1, int num2) {
		return num1 > num2 ? num2 : num1;
	}

	/**
	 * 汉诺塔问题
	 */
	public static void hanon(int n, char a, char b, char c) {
		if (n == 1) {
			move(1, a, c);
			return;
		}
		hanon(n - 1, a, c, b);// 递归，把n-1个盘子从A盘上借助C盘移到B盘上
		move(n, a, c);
		hanon(n - 1, b, a, c);// 递归，n-1个移动过来之后B变开始盘，B通过A移动到C，这边很难理解
	}

	public static void move(int n, char a, char c) {
		System.out.println(n + ":" + a + "-->" + c);// 打印移动盘子情况
	}

	public static int josephus(int n, int m) {
		LinkedList<Integer> people = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) {
			people.add(i);
		}
		m -= 1;
		int k = m % people.size();
		while (people.size() > 1) {
			people.remove(k);
			k = (k + m) % people.size();
		}
		return people.pop();
	}

	public static void josephus2(int n, int m) {
		int i, s = 0;
		for (i = 2; i <= n; i++)
			s = (s + m) % i;
		System.out.println(String.format("The winner is %d", s));
	}

	/**
	 * 递归josephus
	 */
	public static int josephus3(int n, int m) {
		if (n == 1)
			return 0;
		return (m + josephus3(n - 1, m)) % n;
	}

	/**
	 * m的n次方
	 */
	public static double pow(double m, int n) {
		double result;
		if (n == 0)
			return 1;
		if (n > 0) {
			if (n % 2 == 0) {
				result = pow(m, n / 2);
				return result * result;
			} else {
				result = m * pow(m, (n - 1));
				return result;
			}

		} else {
			return 1 / pow(m, -n);
		}
	}

	public static void main(String[] args) {
		System.out.println(decimalToBinary(3));
		// yueshu(3, 9);
		// hanon(4, 'A', 'B', 'C');

		System.out.println(josephus(18, 3));
		System.out.println(josephus3(18, 3));
		josephus2(5, 3);

		System.out.println(pow(2, -3));
	}
}
