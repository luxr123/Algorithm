package recursion;

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

	public static String decimalToBinary2(int num) {
		String res = "";
		if (num == 0)
			return "";
		res = decimalToBinary2(num >> 1); // 调用递归方法
		// System.out.print(num & 1);
		res = res + (num & 1);
		return res;
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
	 * 递归josephus 约瑟夫斯
	 */
	public static int josephus3(int n, int m) {
		if (n == 1)
			return 0;
		return (m + josephus3(n - 1, m)) % n;
	}

	/**
	 * m的n次方 (递归版本)
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

	/**
	 * m的n次方 (非递归版本)
	 * 
	 * <pre>
	 * 时间复杂度O(n)。现在让我们来考虑一种更快的方法，假设我们要计算m13 ， 然后我们把指数13写成二进制形式13=1101，一开始结果res=1.我们要计算的幂可以写成：
	 * 例如: m^13 = m^1 * m^4 * m^8
	 * 时间复杂度O(logn)
	 * </pre>
	 */
	public static double pow2(double m, int n) {
		double result = 1;
		if (n == 0)
			return 1;
		if (n > 0) {
			while (n > 0) {
				if ((n & 1) == 1)
					result *= m;
				m *= m;
				n >>= 1;
			}
			return result;
		} else {
			return 1 / pow2(m, -n);
		}
	}

	/**
	 * 写一个函数来产生第n个斐波那契数。 定义:
	 * 
	 * <pre>
	 * f(1) = f(2) = 1;
	 * f(n) = f(n - 1) + f(n - 2);
	 * </pre>
	 * 
	 * 递归版本(不好使.n为100跑不动)
	 */
	public static long fibo(long n) {
		if (n == 0) {
			return 0; // f(0) = 0
		} else if (n == 1) {
			return 1; // f(1) = 1
		} else if (n > 1) {
			return fibo(n - 1) + fibo(n - 2); // f(n) = f(n—1) + f(n-2)
		} else {
			return -1; // Error condition
		}
	}

	/**
	 * 非递归版本(迭代版本)
	 */
	public static long fibo1(long n) {
		if (n < 0)
			return -1;
		if (n == 0)
			return 0;
		long a = 1, b = 1;
		for (int i = 3; i <= n; i++) {
			long tmp = a + b;
			a = b;
			b = tmp;
		}
		return b;
	}

	/**
	 * 函数法: 见图fibo.png
	 */
	public static long fibo2(long n) {
		if (n < 1)
			return -1;
		if (n == 1 || n == 2)
			return 1;

		long a[][] = { { 1, 1 }, { 1, 0 } };
		long s[][] = { { 1, 0 }, { 0, 1 } };
		pow(s, a, n - 2);
		return s[0][0] + s[0][1];
	}

	/**
	 *  
	 */
	private static void pow(long s[][], long a[][], long n) {
		while (n > 0) {
			if ((n & 1) == 1)
				mul(s, s, a);
			mul(a, a, a);
			n >>= 1;
		}
	}

	private static void mul(long c[][], long a[][], long b[][]) {
		Long[] t = new Long[4];
		t[0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
		t[1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
		t[2] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
		t[3] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
		c[0][0] = t[0];
		c[0][1] = t[1];
		c[1][0] = t[2];
		c[1][1] = t[3];
	}

	public static void main(String[] args) {
		System.out.println(decimalToBinary(31));
		System.out.println(decimalToBinary2(14));

		// yueshu(3, 9);
		// hanon(4, 'A', 'B', 'C');

		// System.out.println(josephus(18, 3));
		// System.out.println(josephus3(18, 3));
		// josephus2(5, 3);
		//
		// System.out.println(pow(2, -3));
		// System.out.println(pow2(2, -3));

		System.out.println("xx");
		System.out.println(fibo(30));
		
		long start = System.currentTimeMillis();
		System.out.println(fibo1(100000000));
		long end = System.currentTimeMillis();
		System.out.println(end -start);
		
		start = System.currentTimeMillis();
		System.out.println(fibo2(100000000));
		end = System.currentTimeMillis();
		System.out.println(end -start);
	}
}
