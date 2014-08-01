package eight;

public class Recursion3 {

	/**
	 * 题目：20个桶，每个桶中有10条鱼，用网从每个桶中抓鱼，每次可以抓住的条数随机，每个桶只能抓一次，问一共抓到180条的排列有多少种 （也可求概率）
	 * 
	 * <pre>
	 * 题目等价于 ~ 捞鱼：将20条鱼放在20个桶中，每个桶最多可以放10条, 求得所有的排列方法
	 * DP自顶向下递归 备忘录
	 * 参考:http://www.ahathinking.com/archives/112.html
	 * </pre>
	 */
	static int[][] dp = new int[21][21]; /* 备忘录，存储子问题的解; 表示前i个桶放j条鱼的方法数 */

	public static int allocate(int bucketN, int fishN) {
		if (bucketN == 0 && fishN == 0) {
			return 1;
		}
		if (bucketN == 0 || fishN < 0) {
			return 0;
		}
		/* 如果子问题没有计算,则计算，否则直接返回即可 */
		if (dp[bucketN][fishN] == 0) {
			for (int i = 0; i <= 10; i++) {
				dp[bucketN][fishN] += allocate(bucketN - 1, fishN - i);
			}
		}
		return dp[bucketN][fishN];
	}

	/**
	 * 捞鱼：将20条鱼放在20个桶中，每个桶最多可以放10条 求得所有的排列方法 自底向上
	 * 
	 * <pre>
	 * DP: 
	 * f(i,j) = sum{ f(i-1,j-k),0<=k<=10}
	 * </pre>
	 * 
	 * 该方法中测试 20个桶 180条鱼，与递归速度做对比
	 */
	/* 实现1 */

	static int[][] dp2 = new int[21][200]; /* 前i个桶放j条鱼的方法数 */

	public static int allocate2(int bucketN, int fishN) {

		/* 初始化合法状态 */
		for (int i = 0; i <= 10; i++)
			dp2[1][i] = 1;
		for (int i = 2; i <= bucketN; i++) { /* 从第二个桶开始 */
			for (int j = 0; j <= fishN; j++) {
				for (int k = 0; k <= 10 && j - k >= 0; k++) {
					dp2[i][j] += dp2[i - 1][j - k];
				}
			}
		}
		return dp2[bucketN][fishN];
	}

	/* 实现2 (精简) */

	static int[][] dp3 = new int[21][200];

	public static int allocate3(int bucketN, int fishN) {
		dp3[0][0] = 1; /* 初始化合法状态 */

		for (int i = 1; i <= bucketN; i++) { /* 从第一个桶开始 */
			for (int j = 0; j <= fishN; j++) {
				for (int k = 0; k <= 10 && j - k >= 0; k++) {
					dp3[i][j] += dp3[i - 1][j - k];
				}
			}
		}
		return dp3[bucketN][fishN];
	}

	public static void main(String[] args) {
		int bucketN = 1, fishN = 10;
//		System.out.println(allocate(bucketN, fishN));
//		System.out.println(allocate2(bucketN, fishN));
		System.out.println(allocate3(bucketN, fishN));
	}

}
