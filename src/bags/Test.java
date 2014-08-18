package bags;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月18日 下午3:23:04]
 * @Version: [v1.0]
 * 
 */
public class Test {

	int N = 3;
	int V = 15;
	int[][] dp = new int[N][V + 1];

	int[] weight = new int[] { 3, 4, 5 };
	int[] value = new int[] { 4, 6, 7 };

	/**
	 * f(i,v) = max{f(i-1, v-k*c[i]) + k*w[i]}
	 */
	public void knap() {
		int i, j, k;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				if (i > 0) {
					dp[i][j] = dp[i - 1][j];
					if (j >= weight[i]) {
						int maxTmp = 0;
						for (k = 0; k <= j / weight[i]; k++) {
							int tmp = dp[i - 1][j - k * weight[i]] + k * value[i];
							maxTmp = tmp > maxTmp ? tmp : maxTmp;
						}
						dp[i][j] = maxTmp > dp[i][j] ? maxTmp : dp[i][j];
					}
				} else {
					if (j >= weight[0])
						dp[0][j] = j / weight[0] * value[0];
				}
			}
		}
		System.out.println(dp[N - 1][V]);
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.knapAdv2();
	}

	public void knapAdv() {
		int i, j, k, n = 0;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				if (i > 0) {
					n = i & 1;
					dp[n][j] = dp[n ^ 1][j];
					if (j >= weight[i]) {
						int maxTmp = 0;
						for (k = 0; k <= j / weight[i]; k++) {
							int tmp = dp[n ^ 1][j - k * weight[i]] + k * value[i];
							maxTmp = tmp > maxTmp ? tmp : maxTmp;
						}
						dp[n][j] = maxTmp > dp[n][j] ? maxTmp : dp[n][j];
					}
				} else {
					if (j >= weight[0])
						dp[0][j] = j / weight[0] * value[0];
				}
			}
		}
		System.out.println(dp[n][V]);
	}

	/*
	 * f(v) = max{f(v), f(v-c[i]) + w[i]}
	 */
	int[] maxV = new int[201];

	public void knapAdv2() {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = weight[i]; j <= V; j++) {
				int tmp = maxV[j - weight[i]] + value[i];
				maxV[j] = tmp > maxV[j] ? tmp : maxV[j];
			}
		}
		
		System.out.println(maxV[V]);
	}

}
