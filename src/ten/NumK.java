package ten;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 设计算法，找到质因数只有3，5或7的第k个数
 * 
 */
public class NumK {
	int mini(int a, int b) {
		return a < b ? a : b;
	}

	int mini(int a, int b, int c) {
		return mini(mini(a, b), c);
	}

	int getNum(int k) {
		if (k <= 0)
			return 0;
		int res = 1, cnt = 1;
		Queue<Integer> q3 = new LinkedList<Integer>();
		Queue<Integer> q5 = new LinkedList<Integer>();
		Queue<Integer> q7 = new LinkedList<Integer>();
		q3.add(3);
		q5.add(5);
		q7.add(7);
		for (; cnt < k; ++cnt) {
			int v3 = q3.peek();
			int v5 = q5.peek();
			int v7 = q7.peek();
			res = mini(v3, v5, v7);
			if (res == v7) {
				q7.poll();
			} else {
				if (res == v5) {
					q5.poll();
				} else {
					q3.poll();
					q3.add(3 * res);
				}
				q5.add(5 * res);
			}
			q7.add(7 * res);
		}
		return res;
	}

	public static void main(String[] args) {
		NumK last = new NumK();
		// for (int i = 1; i < 20; ++i)
		// System.out.println(last.getNum(i));
		System.out.println(last.getNum(9));

	}
}
