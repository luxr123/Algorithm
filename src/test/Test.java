package test;

public class Test {

	static long fibo(long n) {
		if (n == 1 | n == 2)
			return 1;
		long res = 1;
		long a = 1, b = 1;
		while (n > 2) {
			a = b;
			b = res;
			res = a + b;
			n--;
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(fibo(6));
		
		System.out.println(0 | (1<<8));
	}
}
