package nine;

import sort.QuikSortAdv;

public class LIS {

	/**
	 * 最长递增子序列 LIS 设数组长度不超过 30 DP 可输出最长序列值
	 */
	/**
	 * 解法1: DP 这个方法也最容易想到也是最传统的解决方案，对于该方法和LIS，有以下两点说明：
	 * 
	 * 像LCS一样，从后向前分析，很容易想到，第i个元素之前的最长递增子序列的长度要么是1（单独成一个序列），要么就是第i-1
	 * 个元素之前的最长递增子序列加1，可以有状态方程：
	 * 
	 * <pre>
	 * LIS[i] = max{1,LIS[k]+1}，其中，对于任意的k<=i-1，arr[i] > arr[k]，
	 * 这样arr[i]才能在arr[k]的基础上构成一个新的递增子序列。
	 * </pre>
	 * 
	 * 由LIS可以衍生出来最长非递减子序列，最长递减子序列，道理是一样的。
	 * 对于输出序列，也是可以再申请一数组pre[i]记录子序列中array[i]的前驱，道理跟本节的实现也是一样的
	 * 
	 */
	Integer[] dp = new Integer[31];/* dp[i]记录到[0,i]数组的LIS */
	int lis = 0;/* LIS 长度 */

	int lis(int[] arr, int size) {
		for (int i = 0; i < size; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] <= arr[i] && dp[i] < dp[j] + 1)
					dp[i] = dp[j] + 1;
			}
			if (lis < dp[i])
				lis = dp[i];
		}
		return lis;
	}

	/* 输出LIS */
	void outputLIS(int[] arr, int index) {
		boolean isLIS = false;
		if (lis == 0 || 0 > index || null == arr)
			return;
		if (dp[index] == lis) {
			lis--;
			isLIS = true;
		}
		outputLIS(arr, index - 1);
		if (isLIS) {
			System.out.println(arr[index]);
		}

	}

	/**
	 * 解法2：排序+LCS
	 * 这个方法是在Felix’blog（见参考资料）中看到的，因为简单，他在博文中只是提了一句，不过为了练手，虽然懒，还是硬着头皮写一遍吧
	 * ，正好再写一遍快排，用quicksort +
	 * LCS，这个思路还是很巧妙的，因为LIS是单调递增的性质，所以任意一个LIS一定跟排序后的序列有LCS，并且就是LIS本身
	 */
	int LCS_dp2(int[] X, int[] CopyX, int len) {
		int[][] dp2 = new int[31][31];/* 最长公共子串 DP */
		for (int i = 1; i <= len; ++i) {
			for (int j = 1; j <= len; ++j) {
				if (X[i - 1] == CopyX[j - 1]) {
					dp2[i][j] = dp2[i - 1][j - 1] + 1;
				} else if (dp2[i - 1][j] > dp2[i][j - 1]) {
					dp2[i][j] = dp2[i - 1][j];
				} else {
					dp2[i][j] = dp2[i][j - 1];
				}
			}
		}
		return dp2[len][len];
	}

	/**
	 * 解法3: 最长递增子序列 LIS 设数组长度不超过 30 DP + BinarySearch (只是求出长度)
	 */
	int[] MaxV = new int[30]; /* 存储长度i+1（len）的子序列最大元素的最小值 */
	int len; /* 存储子序列的最大长度 即MaxV当前的下标 */

	int lis2(int[] arr) {
		MaxV[0] = arr[0]; /* 初始化 */
		len = 1;
		for (int x : arr) { /* 寻找arr[i]属于哪个长度LIS的最大元素 */
			if (x > MaxV[len - 1]) { /* 大于最大的,自然无需查找，否则二分查其位置 */
				MaxV[len++] = x;
			} else {
				int pos = BinSearch(MaxV, len, x);
				MaxV[pos] = x;
			}
		}
		return len;
	}

	/* 返回MaxV[i]中刚刚大于x的那个元素的下标 */
	int BinSearch(int[] MaxV, int size, int x) {
		int left = 0, right = size - 1;
		while (left <= right) {
			int mid = (left + right) / 2;
			if (MaxV[mid] <= x)
				left = mid + 1;
			else
				right = mid - 1;

		}
		return left;
	}

	public static void main(String[] args) {
		LIS lis = new LIS();
		int arr[] = { 2, 5, 1, 8, 3, 6, 7 };
		int arr2[] = { 2, 5, 1, 8, 3, 6, 7 };
		QuikSortAdv.quickSort(arr2, 0, arr.length - 1);
		// int arr[] = { 2, 5, 8, 9, 1, 3, 5, 4 };
		// int arr[] = { 1, -1, 2, -3, 4, -5, 6, -7 };
		// System.out.println(lis.lis(arr, arr.length));
		// lis.outputLIS(arr, arr.length - 1);
		// lis.outputLIS(arr, arr.length - 1);
		// System.out.println(lis.lis2(arr));

		System.out.println(lis.LCS_dp2(arr, arr2, arr.length - 1));
	}
}
