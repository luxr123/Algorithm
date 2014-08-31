package nine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 最长公共子串（Longest-Common-Substring，LCS）
 * 
 * @author luxury
 *         题：给定两个字符串X，Y，求二者最长的公共子串，例如X=[aaaba]，Y=[abaa]。二者的最长公共子串为[aba]，长度为3。
 * 
 */
public class LCS {

	/**
	 * 解法1:DP方案
	 * 
	 * <pre>
	 * X[i] == Y[j]，dp[i][j] = dp[i-1][j-1] + 1
	 * X[i] != Y[j]，dp[i][j] = 0
	 * 对于初始化，i==0或者j==0，如果X[i] == Y[j]，dp[i][j] = 1；否则dp[i][j] = 0。
	 * </pre>
	 */
	/* 最长公共子串 DP */
	int[][] dp = new int[30][30];

	int maxlen, maxindex;

	void LCS_dp(String X, int xlen, String Y, int ylen) {
		maxlen = maxindex = 0;
		for (int i = 0; i < xlen; i++) {
			for (int j = 0; j < ylen; j++) {
				if (X.charAt(i) == Y.charAt(j)) {
					if ((i > 0) && (j > 0))
						dp[i][j] = dp[i - 1][j - 1] + 1;
					if (i == 0 || j == 0)
						dp[i][j] = 1;
					if (dp[i][j] > maxlen) {
						maxlen = dp[i][j];
						maxindex = i + 1 - maxlen;
					}
				}
			}
		}
		outputLCS(X);
	}

	void outputLCS(String arr) {
		if (maxlen == 0) {
			System.out.format("NULL LCS\n");
			return;
		}
		System.out.format("The len of LCS is %d\n", maxlen);

		int i = maxindex;
		while (maxlen-- > 0) {
			System.out.format("%c", arr.charAt(i++));
		}
	}

	/* 最长重复子串 后缀数组 */
	Set<String> suff = new TreeSet<String>();// 存储后缀数组，并排序
	String FLAG = "#";
	int startIndex;// 记录公共字符串截取的起始位置

	/**
	 * 后缀数组
	 */
	public String LCS_suffix(String str1, String str2) {
		String arr = str1 + FLAG + str2;
		maxlen = maxindex = 0;
		for (int i = 0; i < arr.length(); i++) { /* 初始化后缀数组 */
			suff.add(arr.substring(i));
		}
		List<String> suffList = new ArrayList<String>(suff);// set转换成list方便调用
		for (int i = 0; i < suffList.size() - 1; i++) {// 循环，找出最长的重复子序列下标，最长的重复子序列截取位置
			Integer[] res = comlen(suffList.get(i), suffList.get(i + 1));// 获取后缀数组两两比较的相同子序列长度
			int len = res[0];
			if (len > maxlen) {
				maxlen = len;
				maxindex = i;
				startIndex = res[1];
			}
		}

		String result = suffList.get(maxindex).substring(0, maxlen);// 根据最长的重复子序列下标和截取位置，获得最长的重复子序列

		return result;
	}

	Integer[] comlen(String next, String next2) {
		if ((next.contains(FLAG) && next2.contains(FLAG)) || (!next.contains(FLAG) && !next2.contains(FLAG)))
			return new Integer[]{0,0};
		char[] c1 = next.toCharArray();
		char[] c2 = next2.toCharArray();
		int maxlen = 0, startIndex = 0;
		int tmp = 0;
		for (int i = 0; i < (c1.length > c2.length ? c2.length : c1.length); i++) {
			if (c1[i] == c2[i]) {
				tmp++;
			} else {
				startIndex = i + 1;
				tmp = 0;
			}
			maxlen = maxlen < tmp ? tmp : maxlen;
		}
		return new Integer[]{maxlen, startIndex};
	}

	public static void main(String[] args) {
		String str1 = "acaccbabb";
		String str2 = "acbac";
		LCS lcs = new LCS();
		// lcs.LCS_dp(str1, str1.length(), str2, str2.length());
		String res = lcs.LCS_suffix(str1, str2);
		System.out.println(res);
	}
}
