package nine;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月2日 上午9:42:45]
 * @Version: [v1.0]
 * 
 *           题：从一个字符串中找到一个连续子串，该子串中任何两个字符不能相同，求子串的最大长度并输出一条最长不重复子串。
 * 
 */
public class LNRS {
	/*
	 * 最长不重复子串 设串不超过30 我们记为 LNRS
	 */
	int maxlen;
	int maxindex;

	/* LNRS 基本算法 hash */
	/**
	 * 解法1: 基本算法 使用Hash
	 * 
	 * 要求子串中的字符不能重复，判重问题首先想到的就是hash，寻找满足要求的子串，最直接的方法就是遍历每个字符起始的子串，辅助hash，
	 * 寻求最长的不重复子串，由于要遍历每个子串故复杂度为O(n^2)，n为字符串的长度，辅助的空间为常数hash[256]。
	 */

	void LNRS_hash(String arr, int size) {
		int i, j;
		for (i = 0; i < size; i++) {
			byte[] visit = new byte[256];
			visit[arr.charAt(i)] = 1;
			for (j = i + 1; j < size; j++) {
				if (visit[arr.charAt(j)] == 0) {
					visit[arr.charAt(j)] = 1;
				} else {
					if (j - i > maxlen) {
						maxlen = j - i;
						maxindex = i;
					}
					break;
				}
			}
			if ((j == size) && (j - i > maxlen)) {
				maxlen = j - i;
				maxindex = i;
			}
		}
		output(arr);
	}

	/**
	 * 解法2: DP方案:
	 * 
	 * <pre>
	 * 新的子串的长度是与第一个重复的字符的下标有关的，如果该下标在上一个最长子串起始位置之前，
	 * 则dp[i] = dp[i-1] +  1，即上一个最长子串的起始位置也是当前最长子串的起始位置；如果该下标在上一个最长子串起始位置之后，则新的子串是从该下标之后开始的。
	 * O(N^2)的DP方案
	 * </pre>
	 */
	/* LNRS dp */
	int[] dp = new int[30];

	void LNRS_dp(String arr, int size) {
		int i, j;
		int last_start = 0; // 上一次最长子串的起始位置
		maxlen = maxindex = 0;

		dp[0] = 1;
		for (i = 1; i < size; i++) {
			for (j = i - 1; j >= last_start; j--) { // 遍历到上一次最长子串起始位置
				if (arr.charAt(j) == arr.charAt(i)) {
					dp[i] = i - j;
					last_start = j + 1; // 更新last_start
					break;
				} else if (j == last_start) { // 无重复
					dp[i] = dp[i - 1] + 1;
				}
			}
			if (dp[i] > maxlen) {
				maxlen = dp[i];
				maxindex = i + 1 - maxlen;
			}
		}
		output(arr);
	}

	/**
	 * 解法3: DP + Hash 方案
	 * 
	 * 上面的DP方案是O(n^2)的，之所以是n^2，是因为“回头”去寻找重复元素的位置了，受启发于最初的Hash思路，
	 * 我们可以用hash记录元素是否出现过
	 * ，我们当然也可以用hash记录元素出现过的下标，既然这样，在DP方案中，我们何不hash记录重复元素的位置，这样就不必
	 * “回头”了，而时间复杂度必然降为O(N)，只不过需要一个辅助的常数空间visit[256]，典型的空间换时间。
	 */
	/* LNRS dp + hash 记录下标 */
	void LNRS_dp_hash(String arr, int size) {
		maxlen = maxindex = 0;
		dp[0] = 1;
		byte[] visit = new byte[256];
		visit[arr.charAt(0)] = 0;
		int last_start = 0;

		for (int i = 1; i < size; i++) {
			if (visit[arr.charAt(i)] == 0) {
				dp[i] = dp[i - 1] + 1;
				visit[arr.charAt(i)] = (byte) i; /* 记录字符下标 */
			} else {
				if (last_start <= visit[arr.charAt(i)]) {
					dp[i] = i - visit[arr.charAt(i)];
					last_start = visit[arr.charAt(i)] + 1;
				} else {
					dp[i] = dp[i - 1] + 1;
				}
				visit[arr.charAt(i)] = (byte) i; /* 更新最近重复位置 */
			}
			if (dp[i] > maxlen) {
				maxlen = dp[i];
				maxindex = i + 1 - maxlen;
			}
		}
		output(arr);
	}

	/**
	 * DP + Hash 优化方案
	 * 
	 * 写到这里，还是有些别扭，因为辅助的空间多了，是不是还能优化，仔细看DP最优子问题解的更新方程：
	 * 
	 * <pre>
	 * dp[i] = dp[i-1] + 1;
	 * dp[i-1]不就是更新dp[i]当前的最优解么？这与最大子数组和问题的优化几乎同出一辙，我们不需要O(n)的辅助空间去存储子问题的最优解，
	 * 而只需O(1)的空间就可以了，至此，我们找到了时间复杂度O(N)，辅助空间为O(1)（一个额外变量与256大小的散列表）的算法
	 * 
	 * <pre>
	 * 注意：当前最长子串的构成是与上一次最长子串相关的，故要记录上一次最长子串的起始位置！
	 */
	/* LNRS dp + hash 优化 */
	void LNRS_dp_hash_impro(String arr, int size) {
		maxlen = maxindex = 0;
		byte[] visit = new byte[256];
		visit[arr.charAt(0)] = 0;
		int curlen = 1;
		int last_start = 0;

		for (int i = 1; i < size; i++) {
			if (visit[arr.charAt(i)] == 0) {
				curlen++;
				visit[arr.charAt(i)] = (byte) i; /* 记录字符下标 */
			} else {
				if (last_start <= visit[arr.charAt(i)]) {
					curlen = i - visit[arr.charAt(i)];
					last_start = visit[arr.charAt(i)] + 1;
				} else {
					curlen++;
				}
				visit[arr.charAt(i)] = (byte) i; /* 更新最近重复位置 */
			}
			if (curlen > maxlen) {
				maxlen = curlen;
				maxindex = i + 1 - maxlen;
			}
		}
		output(arr);
	}

	/**
	 * 
	 */
	void output(String arr) {
		if (maxlen == 0) {
			System.out.format("NULL LNRS\n");
			return;
		}
		System.out.format("The len of LNRS is %d\n", maxlen);

		int i = maxindex;
		while (maxlen-- > 0) {
			System.out.format("%c", arr.charAt(i++));
		}
	}

	public static void main(String[] args) {
		LNRS lnrs = new LNRS();
		String arr = "anbmancvqana";
		// String arr = "banana";
		int size = arr.length();
		// lnrs.LNRS_hash(X, size);
		// lnrs.LNRS_dp(arr, size);
		// lnrs.LNRS_dp_hash(arr, size);
		lnrs.LNRS_dp_hash_impro(arr, size);
	}
}
