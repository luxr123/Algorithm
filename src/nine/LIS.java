package nine;

public class LIS {

	/**
	 * 最长递增子序列 LIS 设数组长度不超过 30 DP
	 */
	/*
	 * 解法一: 这个方法也最容易想到也是最传统的解决方案，对于该方法和LIS，有以下两点说明：
	 * 
	 * 由LIS可以衍生出来最长非递减子序列，最长递减子序列，道理是一样的。
	 * 对于输出序列，也是可以再申请一数组pre[i]记录子序列中array[i]的前驱，道理跟本节的实现也是一样的
	 */
	Integer[] dp = new Integer[31];/* dp[i]记录到[0,i]数组的LIS */
	int lis = 0;/* LIS 长度 */

	int lis(int[] arr, int size) {
		for (int i = 0; i < size; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (arr[j] <= arr[i] && dp[i] < dp[j] + 1)
					dp[i] = dp[j] + 1;
				if (lis < dp[i])
					lis = dp[i];
			}
		}
		return lis;
	}

	/* 输出LIS */
	void outputLIS(int[] arr, int index) {
		System.out.println("index:" + index);
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

	public static void main(String[] args) {
		LIS lis = new LIS();
		int arr[] = { 2, 5, 1, 8, 3, 6, 7 };
		// int arr[] = { 1, -1, 2, -3, 4, -5, 6, -7 };
		System.out.println(lis.lis(arr, arr.length));

		lis.outputLIS(arr, arr.length - 1);
	}
}
