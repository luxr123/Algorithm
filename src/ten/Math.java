package ten;

public class Math {

	/**
	 * 写一个函数实现*, – , /操作，你能使用的操作只有加法+。(只针对整数来讨论)
	 */
	/*
	 * 对于乘法，比如a*b(a>0, b>0)，可以视为a个b相加，或是b个a相加。
	 * 如果a，b中有负数呢？我们可以先将它们取绝对值相乘，然后再根据同号相乘为正， 异号相乘为负给结果加上符号。其中，我们还可以做一点小优化，
	 * 即如果a<b，就求a个b相加；如果a>b，就求b个a相加。这样可以加法运算次数。
	 * 
	 * 对于减法，a-b，我们只需要转变为a+(-b)即可。
	 * 
	 * 对于除法，a/b，首先我们要考虑的是b不能为0.当b等于0时，抛出异常或返回无穷大(程序
	 * 中定义的一个值)。与乘法相同，我们都先对a和b取绝对值，然后不断地从a中减去b，
	 * 相应的商数加1。直到a已经不够给b减了，再根据a和b的符号决定是否给商数加上负号即可。
	 */
	static int flipsign(int a) {
		int d = a < 0 ? 1 : -1;
		int opa = 0;
		while (a != 0) {
			a += d;
			opa += d;
		}
		return opa;
	}

	static int abs(int a) {
		if (a < 0)
			a = flipsign(a);
		return a;
	}

	static boolean opsign(int a, int b) {
		return (a > 0 && b < 0) || (a < 0 && b > 0);
	}

	static int minus(int a, int b) {
		return a + flipsign(b);
	}

	static int times(int a, int b) {
		int aa = abs(a), bb = abs(b);
		if (aa < bb) {
			aa = aa + bb;
			bb = aa - bb;
			aa = aa - bb;
		}
		int res = 0;
		for (int i = 0; i < bb; i++, res += aa)
			;
		if (opsign(a, b))
			res = flipsign(res);
		return res;
	}

	static int divide(int a, int b) {
		if (b == 0)
			throw new ArithmeticException("/ by zero");
		int res = 0;
		int aa = abs(a), bb = abs(b);
		for (; (aa -= bb) >= 0; res++)
			;
		if (opsign(a, b))
			res = flipsign(res);
		return res;
	}

	public static void main(String[] args) {
		System.out.println(flipsign(10));
		System.out.println(abs(-10));

		System.out.println(minus(10, -5));
		System.out.println(times(10, -5));
		System.out.println(divide(5, -5));
	}
}
