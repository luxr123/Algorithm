package nine;

/**
 * 最大子数组和 问题:
 * 一个有N个元素的整型数组arr，有正有负，数组中连续一个或多个元素组成一个子数组，这个数组当然有很多子数组，求子数组之和的最大值。例如：[0，-2，3，5
 * ，-1，2]应返回9，[-9，-2，-3，-5，-3]应返回-2。
 * 
 * 参考:http://www.ahathinking.com/archives/120.html
 * 
 */
public class LISArray {

	/* 最大子数组和 设数组长度不超过30 */
	static final int INF = 1000;

	/**
	 * 解法1: 最直接的方法就是找出所有的子数组，然后求其和，取最大。如果每个子数组都遍历求和，该方法的复杂度为O(N^3)，仔细考虑，在遍历过程中，
	 * 这些子数组的和是有重复计算的
	 * ：下标i与j之间的区间和Sum[i,j]=Sum[i,j-1]+arr[j]。于是子数组和的求法不必每次都遍历，算法复杂度可以降为O(N^2)。
	 */
	public int Maxsum_base(int[] arr, int size) {
		int maxSum = -INF;
		for (int i = 0; i < size; i++) { /* for each i, got a sum[i,j] */
			int sum = 0;
			for (int j = i; j < size; j++) {
				sum += arr[j];
				if (sum > maxSum)
					maxSum = sum;
			}
		}
		return maxSum;
	}

	/* DP base version */
	public int max(int a, int b) {
		return a > b ? a : b;
	}

	public void init(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			arr[i] = -INF;
	}

	/**
	 * 解法2: 状态方程： All[i] = max{ arr[i]，End[i-1]+arr[i]，All[i-1] }
	 */
	int Maxsum_dp(int[] arr, int size) {
		int[] End = new int[30];
		int[] All = new int[30];
		init(End);
		init(All);
		End[0] = All[0] = arr[0];

		for (int i = 1; i < size; i++) {
			End[i] = max(End[i - 1] + arr[i], arr[i]);
			All[i] = max(End[i], All[i - 1]);
		}
		return All[size - 1];
	}

	/**
	 * 解法3: 上述方法启示我们：我们只需从头遍历数组元素，并累加求和，如果和小于0了就自当前元素从新开始，否则就一直累加，取其中的最大值便求得解。
	 */
	/* DP ultimate version */
	public int Maxsum_ultimate(int[] arr, int size) {
		int maxSum = -INF;
		int sum = 0;
		for (int i = 0; i < size; i++) {
			if (sum < 0) {
				sum = arr[i];
			} else {
				sum += arr[i];
			}
			if (sum > maxSum) {
				maxSum = sum;
			}
		}
		return maxSum;
	}

	/**
	 * 返回最大子数组始末位置
	 */
	/* 最大子数组 返回起始位置 */
	public void Maxsum_location(int[] arr, int size, int start, int end) {
		int maxSum = -INF;
		int sum = 0;
		int curstart = start = 0; /* curstart记录每次当前起始位置 */
		for (int i = 0; i < size; i++) {
			if (sum < 0) {
				sum = arr[i];
				curstart = i; /* 记录当前的起始位置 */
			} else {
				sum += arr[i];
			}
			if (sum > maxSum) {
				maxSum = sum;
				start = curstart; /* 记录并更新最大子数组起始位置 */
				end = i;
			}
		}
		System.out.println("start:" + start);
		System.out.println("end:" + end);
	}

	/**
	 * 数组首尾相连 Maxsum=max{ 原问题的最大子数组和；数组所有元素总值-最小子数组和 }
	 */
	/* 如数组首尾相邻 */
	int Maxsum_endtoend(int[] arr, int size) {
		int maxSum_notadj = Maxsum_ultimate(arr, size); /* 不跨界的最大子数组和 */
		if (maxSum_notadj < 0) {
			return maxSum_notadj; /* 全是负数 */
		}
		int maxSum_adj = -INF; /* 跨界的最大子数组和 */
		int totalsum = 0;
		int minsum = INF;
		int tmpmin = 0;
		for (int i = 0; i < size; i++) { /* 最小子数组和 道理跟最大是一样的 */
			if (tmpmin > 0)
				tmpmin = arr[i];
			else
				tmpmin += arr[i];
			if (tmpmin < minsum)
				minsum = tmpmin;
			totalsum += arr[i];
		}
		maxSum_adj = totalsum - minsum;
		return maxSum_notadj > maxSum_adj ? maxSum_notadj : maxSum_adj;
	}

	public static void main(String[] args) {
		LISArray lisArray = new LISArray();
		// int[] arr = { 0, -2, 3, 5, -1, 2 };
		int[] arr = { 8, -10, 60, 3, -1, -6 };
		// System.out.println(lisArray.Maxsum_base(arr, arr.length));
		// System.out.println(lisArray.Maxsum_dp(arr, arr.length));
		System.out.println(lisArray.Maxsum_ultimate(arr, arr.length));

		lisArray.Maxsum_location(arr, arr.length, 0, arr.length - 1);

		System.out.println(lisArray.Maxsum_endtoend(arr, arr.length));

	}
}
