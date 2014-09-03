package test;

/**
 * <pre>
 * 已知三个升序整数数组a[l],
 * b[m]和c[n]。请在三个数组中各找一个元素，使得组成的三元组距离最小。三元组的距离定义是：假设a[i]、b[j]和c[k]是一个三元组，那么距离为:
 * Distance = max(|a[ I ] – b[ j ]|, |a[ I ] – c[ k ]|, |b[ j ] – c[ k ]|)
 * 请设计一个求最小三元组距离的最优算法，并分析时间复杂度。
 * 这道题目有两个关键点：
 *
 * <strong>第一个关键点： max{|x1-x2|,|y1-y2|} =（|x1+y1-x2-y2|+|x1-y1-(x2-y2)|）/2 --公式（1）</strong>
 * 
 * 我们假设x1=a[ i ]，x2=b[ j ]，x3=c[ k ]，则
 * 
 * Distance = max(|x1 – x2|, |x1 – x3|, |x2 – x3|) = max( max(|x1 – x2|, |x1 – x3|) , |x2 – x3|) --公式（2）
 * 
 *　根据公式（1），max(|x1 – x2|, |x1 – x3|) = 1/2 ( |2x1 – x2– x3| + |x2 – x3|)，带入公式（2），得到
 * 
 * Distance = max( 1/2 ( |2x1 – x2– x3| + |x2 – x3|) , |x2 – x3| )
 * 
 *　　　　　=1/2 * max( |2x1 – x2– x3| , |x2 – x3| ) + 1/2*|x2 – x3| //把相同部分1/2*|x2 – x3|分离出来
 * 
 *　　　　　=1/2 * max( |2x1 – (x2 + x3)| , |x2 – x3| ) + 1/2*|x2 – x3| //把(x2 + x3)看成一个整体，使用公式（1）
 * 
 *　　　　　=1/2 * 1/2 *((|2x1 – 2x2| + |2x1 – 2x3|) + 1/2*|x2 – x3|
 * 
 *　　　　　=1/2 *|x1 – x2| + 1/2 * |x1 – x3| + 1/2*|x2 – x3|
 * 
 *　　　　　=1/2 *(|x1 – x2| + |x1 – x3| + |x2 – x3|) //求出来了等价公式，完毕！
 * 
 *　<strong>第二个关键点：如何找到(|x1 – x2| + |x1 – x3| + |x2 – x3|) 的最小值</strong>，x1，x2，x3，分别是三个数组中的任意一个数，这一题，我只是做到了上面的推导，后面的算法设计是由csdn上的两个朋友想出来的方法，
 * 	他们的CSDN的ID分别为 “云梦泽” 和 “ shuyechengying”.
 * 
 * 算法思想是：
 * 用三个指针分别指向a,b,c中最小的数，计算一次他们最大距离的Distance ，然后在移动三个数中较小的数组指针，再计算一次，每次移动一个，
 * 直到其中一个数组结束为止，最慢(l+ m + n)次，复杂度为O(l+ m + n)
 * 
 * <pre>
 */
public class MinDistanceTuple {

	private int l = 3;
	private int m = 4;
	private int n = 6;

	public int solvingViolence(int a[], int b[], int c[]) {
		// 暴力解法，大家都会，不用过多介绍了！
		int i = 0, j = 0, k = 0;
		int minSum = (Math.abs(a[i] - b[j]) + Math.abs(a[i] - c[k]) + Math.abs(b[j] - c[k])) / 2;
		int sum = 0;
		for (i = 0; i < l; i++) {
			for (j = 0; j < m; j++) {
				for (k = 0; k < n; k++) {
					sum = (Math.abs(a[i] - b[j]) + Math.abs(a[i] - c[k]) + Math.abs(b[j] - c[k])) / 2;
					if (minSum > sum)
						minSum = sum;
				}
			}
		}
		return minSum;
	}

	public int minDistance(int a[], int b[], int c[]) {
		int minSum = 0; // 最小的绝对值和
		int sum = 0; // 计算三个绝对值的和，与最小值做比较
		int minOFabc = 0; // a[i] , b[j] ,c[k]的最小值
		int cnt = 0; // 循环次数统计，最多是l + m + n次
		int i = 0, j = 0, k = 0; // a,b,c三个数组的下标索引
		minSum = (Math.abs(a[i] - b[j]) + Math.abs(a[i] - c[k]) + Math.abs(b[j] - c[k])) / 2;
		for (cnt = 0; cnt <= l + m + n; cnt++) {
			sum = (Math.abs(a[i] - b[j]) + Math.abs(a[i] - c[k]) + Math.abs(b[j] - c[k])) / 2;
			minSum = minSum < sum ? minSum : sum;
			minOFabc = myMin(a[i], b[j], c[k]);// 找到a[i] ,b[j] ,c[k]的最小值
			// 判断哪个是最小值，做相应的索引移动
			if (minOFabc == a[i]) {
				if (++i >= l)
					break;
			}// a[i]最小,移动i
			if (minOFabc == b[j]) {
				if (++j >= m)
					break;
			}// b[j]最小,移动j
			if (minOFabc == c[k]) {
				if (++k >= n)
					break;
			}// c[k]最小,移动k

		}
		return minSum;
	}

	private int myMin(int a, int b, int c) {
		int min = a < b ? a : b;
		min = min < c ? min : c;
		return min;
	}

	public static void main(String[] args) {
		MinDistanceTuple tuple = new MinDistanceTuple();
		int a[] = { 5, 6, 7 };
		int b[] = { 13, 14, 15, 17 };
		int c[] = { 19, 22, 24, 29, 32, 42 };

		System.out.format("\nBy violent solution ,the min is %d\n", tuple.solvingViolence(a, b, c));
		System.out.format("\nBy Optimal solution ,the min is %d\n", tuple.minDistance(a, b, c));
	}
}
