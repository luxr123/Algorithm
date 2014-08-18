package zipper;

/**
 * 题意：就是给定三个字符串A，B，C；判断C能否由AB中的字符组成，同时这个组合后的字符顺序必须是A，B中原来的顺序，不能逆序；例如：A：mnl，B：xyz
 * ；如果C为mnxylz，就符合题意；如果C为mxnzly，就不符合题意，原因是z与y顺序不是B中顺序。
 * 
 * @author luxury
 * 
 */
public class Zipper {

	char[] A = { 'a', 'b', 'c' };
	char[] B = { '1', '2', '3' };
	char[] C = { 'a', 'b', '1', '2', '3', 'c' };

	int N = 1; /* 测试次数 */

	/*
	 * dp[i][j] 表示A前i个字符与B前j个字符 是否能构成C前i+j个字符
	 */
	int[][] dp = new int[201][201];

	/**
	 * 解法1: DP求解：定义dp[i][j]表示A中前i个字符与B中前j个字符是否能组成C中的前 (i+j)
	 * 个字符，如果能标记true，如果不能标记false；
	 * 
	 * 有了这个定义，我们就可以找出状态转移方程了，初始状态dp[0][0] = 1：
	 * 
	 * <pre>
	 * dp[i][j] = 1 <== 如果 dp[i-1][j] == 1 && C[i+j-1] == A[i-1] 
	 * dp[i][j] = 1 <== 如果 dp[i][j-1] == 1 && C[i+j-1] == B[j-1]
	 * </pre>
	 */
	public void zip() {
		for (int k = 1; k <= N; ++k) { /* N个测试用例 */
			int lenA = A.length;
			int lenB = B.length;

			dp[0][0] = 1;

			for (int i = 0; i <= lenA; i++) {
				for (int j = 0; j <= lenB; j++) {
					if (i > 0 && (dp[i - 1][j] == 1) && (C[i + j - 1] == A[i - 1])) {// 表示C那个位置的字符来自于A
						dp[i][j] = 1;
					}
					if (j > 0 && (dp[i][j - 1] == 1) && (C[i + j - 1] == B[j - 1])) {// 表示C那个位置的字符来自于B
						dp[i][j] = 1;
					}
				}
			}

			System.out.format("Data set %d: %s\n", k, dp[lenA][lenB] == 1 ? "yes" : "no");
		}
	}

	/**
	 * 解法2: 用DFS实现这个题目
	 */
	int DFS(int x, int y) {
		if (x == -1 && y == -1)
			return 1;
		if (x >= 0 && A[x] == C[x + y + 1]) {
			if (DFS(x - 1, y) == 1)
				return 1;
		}
		if (y >= 0 && B[y] == C[x + y + 1]) {
			if (DFS(x, y - 1) == 1)
				return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
		Zipper zipper = new Zipper();
		zipper.zip();
	}
}
