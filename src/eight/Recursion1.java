package eight;

import java.util.Stack;

public class Recursion1 {

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// 写一个函数来产生第n个斐波那契数。
	// ////////////////////////////////////////////////////////////////////////////////////////////////
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

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// 在一个N*N矩阵的左上角坐着一个机器人，它只能向右运动或向下运动。那么， 机器人运动到右下角一共有多少种可能的路径？
	// /////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 解法一 递归
	 * 
	 * <pre>
	 * path(i, j) = path(i - 1, j) + path(i, j - 1)
	 * </pre>
	 */
	public static long path(long m, long n) {
		if (m == 1 || n == 1)
			return 1;
		else
			return path(m - 1, n) + path(m, n - 1);
	}

	/**
	 * 解法二 纯数学的方法
	 * 
	 * <pre>
	 * C(m-1+n-1, m-1)=(m-1+n-1)! / ( (m-1)! * (n-1)! )
	 * </pre>
	 */
	/*
	 * 机器人从(1, 1)走到(m, n)一定要向下走m-1次，向右走n-1次，不管这过程中是怎么走的。
	 * 因此，一共可能的路径数量就是从总的步数(m-1+n-1)里取出(m-1)步，作为向下走的步子， 剩余的(n-1)步作为向右走的步子。
	 */
	public static int path1(int m, int n) {
		int d = fact(m - 1 + n - 1);
		int d1 = fact(m - 1) * fact(n - 1);
		return d / d1;
	}

	private static int fact(int n) {
		if (n == 0)
			return 1;
		return fact(n - 1) * n;
	}

	/**
	 * 如果有一些格子，机器人是不能踏上去的(比如说放了地雷XD); 如果我们只要输出它其中一条可行的路径即可， 那么我们可以从终点
	 * (m,n)开始回溯，遇到可走的格子就入栈， 如果没有格子能到达当前格子，当前格子则出栈。最后到达(1, 1)时， 栈中正好保存了一条可行路径
	 */

	static Stack<Point> sp = new Stack<Point>();
	final static int MAXN = 20;
	static int[][] g = new int[MAXN][MAXN];

	static boolean getPath(int m, int n) {
		Point p = new Point();
		p.setX(m);
		p.setY(n);
		sp.push(p);
		if (n == 0 && m == 0)// current_path
			return true;
		boolean suc = false;
		if (m > 0 && g[m - 1][n] == 1)// Try up
			suc = getPath(m - 1, n);// Free! Go up
		if (!suc && n > 0 && g[m][n - 1] == 1)// Try left
			suc = getPath(m, n - 1);// Free! Go left
		if (!suc)
			sp.pop(); // Wrong way!
		return suc;
	}

	/**
	 * 我们从(1, 1)开始，如果某个格子可以走， 我们就将它保存到路径数组中；如果不能走，则回溯到上一个格子，
	 * 继续选择向右或者向下走。当机器人走到右下角的格子(M, N)时，即可输出一条路径。 然后程序会退出递归，回到上一个格子，找寻下一条可行路径。
	 */
	static Point[] points = new Point[MAXN + MAXN];

	static void printPaths(int m, int n, int M, int N, int len) {
		if (g[m][n] == 0)
			return;
		Point p = new Point();
		p.setX(m);
		p.setY(n);
		points[len++] = p;
		if (m == M && n == N) {
			for (int i = 0; i < len; ++i)
				System.out.print(points[i] + ",");
			System.out.println();
		} else {
			printPaths(m, n + 1, M, N, len);
			printPaths(m + 1, n, M, N, len);
		}
	}

	public static void main(String[] args) {
		int m = 3, n = 4;
		// System.out.println(path(m, m));
		System.out.println(path1(m, n));

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// g[i][j] = (i*j) & 1;
				g[i][j] = 1;
			}
		}
		// g[1][2] = 0;
		// g[2][2] = 0;
		System.out.println(getPath(m - 1, n - 1));

		while (!sp.empty()) {
			Point p = sp.pop();
			System.out.print(p + ",");
		}
		System.out.println();
		System.out.println("xxxxxxxxxxxxxx");

		printPaths(0, 0, m - 1, n - 1, 0);
	}
}

class Point {
	private int x;
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}
