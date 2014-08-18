package bags;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月7日 下午7:52:12]
 * @Version: [v1.0]
 * 
 */
public class FullKnapSack {

	static final int N = 3;
	static final int V = 15;
	static int[] weight = new int[] { 3, 4, 5 };
	static int[] value = new int[] { 4, 6, 7 };

	static int[][] maxV = new int[11][201]; /* 记录子问题最优解，物品可重复 */

	/**
	 * <pre>
	 * 问题：有个容量为V大小的背包，有很多不同重量weight[i](i=1..n)不同价值value[i](i=1..n)的货物，
	 * 每种物品有无限件可用，想计算一下最多能放多少价值的货物。
	 * </pre>
	 * 
	 * 与01背包不同的是，完全背包每件物体可以放入无限件（只要能放的下），故对于每件物品i，相当于拆分成了v/c[i]件相同的物品，
	 * 拆分之后物品i就不是放入或不放入的两种情况了
	 * ，而是放入0件、放入1件、放入2件…等情况了，对于该件物品i，最大价值取放入k件的最大值，故状态转移方程为：
	 * 
	 * <pre>
	 * f(i,v) = max{ f(i-1,v-k*c[i]) + k*w[i] | 0<=k<=v/c[i] }
	 * 每件物品有v/c[i]种状态
	 * Time Complexity  大于O(N*V)
	 * Space Complexity O(N*V)
	 * </pre>
	 */
	public static void knapsack() {
		int i, j, k;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				if (i > 0) {
					maxV[i][j] = maxV[i - 1][j];
					if (j / weight[i] >= 1) {
						int maxTmp = 0;
						for (k = 1; k <= j / weight[i]; k++) {
							if (maxV[i - 1][j - k * weight[i]] + k * value[i] > maxTmp)
								maxTmp = maxV[i - 1][j - k * weight[i]] + k * value[i];
						}
						maxV[i][j] = maxV[i][j] > maxTmp ? maxV[i][j] : maxTmp;
					}
				} else {
					if (j / weight[0] >= 1)
						maxV[0][j] = j / weight[0] * value[0];
				}
			}
		}
		System.out.println(maxV[N - 1][V]);
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				System.out.print(maxV[i][j]);
				System.out.print(" ");
			}
		}
	}

	/**
	 * 完全背包 版本2
	 * 
	 * <pre>
	 * 完全背包二进制拆分思想
	 * 状态转移方程：f(i,v) = max{ f(i-1,v-2^k*c[i]) + 2^k*w[i] | 0<=k<=log(v/c[i])  }
	 * 每件物品降低为 log(v/c[i]) 种状态
	 * Time Complexity  大于O(N*V)
	 * Space Complexity O(N*V)
	 * </pre>
	 * 
	 * 这种实现方式是对完全背包的基本实现做了一个优化，叫“二进制拆分”。所谓“拆分物体”就是将一种无限件物品拆分成有效的几件物品，
	 * 拆分物体的目的是通过减少物品个数来降低复杂度。
	 * 
	 * 在完全背包中，每种物品i对于容量v来讲实际上相当于有v/c[i]件，故在上述的基本实现中，k就要循环测试v/c[i]次。
	 * 
	 * 这里的拆分是利用了一种二进制思想：即任何数字都可以表示成若干个2^k数字的和，例如7可以用1+2+2^2表示；这很好理解，
	 * 因为任何正数都可以转成二进制，二进制就是若干个“1”（2的幂数）之和。
	 * 
	 * 所以不管最优策略选择几件物品i，我们都可以将物品i拆成费用为c[i]*2^k，价值为w[i]*2^k的若干件物品。这样物品的状态数就降为了log(
	 * v/c[i])，是很大的改进。
	 */
	public static void knapsack2() {
		int[] weig = new int[50];
		int[] val = new int[50];
		int i, j, k;
		int count = 0;
		for (i = 0; i < N; i++) {
			int num = V/weight[i];
			// 二进制拆分
			for (j = 1; j <= num; j <<= 1) {
				weig[count] = j * weight[i];
				val[count++] = j * value[i];
				num -= j;
			}
			if(num > 0){
				weig[count] = num * weight[i];
				val[count++] = num * value[i];
			}
		}
		for (i = 0; i < count; i++) {
			for(j=V; j>=weig[i]; j--){
				int tmp = maxV2[j-weig[i]] + val[i];
				maxV2[j] = tmp>maxV2[j]?tmp:maxV2[j];
			}
		}
		System.out.println(maxV2[V]);
	}
/*	public static void knapsack2() {
		int i, j, k;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				if (i > 0) {
					maxV[i][j] = maxV[i - 1][j];
					if (j / weight[i] >= 1) {
						int maxTmp = 0;
						 优化之处start 
						for (k = 1; k <= j / weight[i]; k <<= 1) {
							if (maxV[i - 1][j - k * weight[i]] + k * value[i] > maxTmp)
								maxTmp = maxV[i - 1][j - k * weight[i]] + k * value[i];
						}
						 优化之处end 
						maxV[i][j] = maxV[i][j] > maxTmp ? maxV[i][j] : maxTmp;
					}
				} else {
					if (j / weight[0] >= 1)
						maxV[0][j] = j / weight[0] * value[0];
				}
			}
		}
		System.out.println(maxV[N - 1][V]);
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				System.out.print(maxV[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}*/

	static int[][] maxValue = new int[201][11]; /* 记录子问题的各状态 */
	static int[] dp = new int[201]; /* 记录子问题的最优解 */

	/**
	 * 完全背包中的逆向思维 版本3
	 * 
	 * <pre>
	 * 我们知道，在01背包和完全背包的实现中，都是针对每种物品进行讨论，即外循环都是for i=0…n，然后每种物品对于容量v的变化而求得最大价值；
	 * 在完全背包中，由于物品的件数无限，所以我们可以倒过来想，我们针对每个容量讨论，外循环为容量，对于每个容量j，我们求j对于所有物品能装载的最大价值，
	 * 这样一来我们就能将时间复杂度降为O(N*V)了。
	 * time Complexity  O(N*V)
	 * Space Complexity O(N*V)
	 * </pre>
	 */
	public static void knapsack3() {
		int i, j;
		for (i = 1; i <= V; i++) {
			int iMaxV = 0; /* 记录子问题i的最优解 */
			/* 每个容量求面对所有物体能装载的最大价值 */
			for (j = 0; j < N; j++) {
				if (i >= weight[j]) {
					int tmp = dp[i - weight[j]] + value[j];
					maxValue[i][j] = dp[i - 1] > tmp ? dp[i - 1] : tmp;
				} else {
					maxValue[i][j] = dp[i - 1];
				}
				if (maxValue[i][j] > iMaxV)
					iMaxV = maxValue[i][j];
			}
			dp[i] = iMaxV;
		}

		System.out.println(dp[V]);

		for (i = 0; i <= V; i++) {
			for (j = 0; j < N; j++) {
				System.out.print(maxValue[i][j]);
				System.out.print(" ");
			}
			System.out.println(dp[i]);
		}
	}

	static int[] maxV2 = new int[201]; /* 记录前i个物品中容量v时的最大价值, 物品可重复 */

	/**
	 * 完全背包使用一维数组 版本4
	 * 
	 * <pre>
	 * 对于01背包和完全背包，无论是空间复杂度还是时间复杂度，最优的方法还是使用一维数组进行实现。
	 * 基于01背包的分析，由于不必考虑物品的重复放入，故v的循环采用顺序即可。
	 * 状态转移方程：v =0...V; f(v) = max{ f(v), f(v-c[i])+w[i] }
	 * Time Complexity  O(N*V)
	 * Space Complexity O(V)
	 * </pre>
	 */
	public static void knapsack4() {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = weight[i]; j <= V; j++) {/* j<weight[i]的对前面的状态不会有影响 */
				int tmp = maxV2[j - weight[i]] + value[i];
				maxV2[j] = (maxV2[j] > tmp) ? maxV2[j] : tmp;
			}
		}
		System.out.println(maxV2[V]);
	}

	/**
	 * <pre>
	 * PS：值得一提的是，在01背包和完全背包中，我们用到了两种思想，个人认为还是很有用的，其他地方也会用到很多，我们有必要在此留心：
	 * . 滚动数组压缩空间的思想
	 * . 二进制拆分的思想
	 * </pre>
	 */

	public static void main(String[] args) {
		// knapsack();
		 knapsack2();
		// knapsack3();
		// knapsack4();
	}
}
