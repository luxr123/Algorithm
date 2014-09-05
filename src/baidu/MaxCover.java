package baidu;

/**
 * 坐标轴上从左到右依次的点为a[0]、a[1]、a[2]……a[n-1]，设一根木棒的长度为L，求L最多能覆盖坐标轴的几个点？
 */
public class MaxCover {
	/**
	 * 算法思想：
	 * 
	 * <pre>
	 * 实际上是求满足a[j]-a[i] <= L && a[j+1]-a[i] > L这两个条件的j与i中间的所有点个数中的最大值 ，即j-i+1最大，
	 * 这样题目就简单多了，方法也很简单：直接从左到右扫描，两个指针i和j，i从位置0开始，j从位置1开始，如果a[j] - a[i] <= L,则j++并记录中间经过的点个数，
	 * 如果a[j] - a[i] > L则j--回退，覆盖点个数-1回到刚好满足条件的时候，将满足条件的最大值与所求最大值比较，然后i++,j++直到求出最大的点个数。
	 * 有两点需要注意：
	 * （1）这里可能没有i和j使得a[j] - a[i]刚好等于L的，所以判断条件不能为a[j] - a[i] = L。
	 * （2）可能存在不同的覆盖点但覆盖的长度相同，此时只选第一次覆盖的点。
	 * </pre>
	 */

	public static int maxCover(int a[], int n, int L) {
		int count = 2, maxCount = 1, start = 0;
		int i = 0, j = 1;

		while (i < n && j < n) {

			while ((j < n) && (a[j] - a[i] <= L)) {
				j++;
				count++;
			}

			// 退回到满足条件的j
			j--;
			count--;

			if (maxCount < count) {
				start = i;
				maxCount = count;
			}
			i++;
			j++;
		}

		System.out.format("covered point: ");
		for (i = start; i < start + maxCount; i++) {
			System.out.format("%d ", a[i]);
		}
		System.out.format("\n");

		return maxCount;
	}

	public static void main(String[] args) {
		// test
		int a[] = { 1, 3, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 21 };

		System.out.format("max count: %d\n\n", maxCover(a, 13, 8));

		int b[] = { 1, 2, 3, 4, 5, 6, 1000 };

		System.out.format("max count: %d\n", maxCover(b, 7, 4));
	}
}
