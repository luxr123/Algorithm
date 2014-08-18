package bags;

public class ZeroOneKnapSack {
	static final int N = 3;
	static final int V = 10;
	static int[] weight = new int[] { 3, 4, 5 };
	static int[] value = new int[] { 4, 6, 7 };

	// 设 V <= 200 N <= 10
	static int[][] dp = new int[11][201]; /* 前i个物体面对容量j的最大价值，即子问题最优解 */

	public static void main(String[] args) {
		// pack1();
		// pack2();
		// pack3();
//		 pack4();
		 pack5();
	}

	/**
	 * 版本1
	 * 
	 * <pre>
	 * 问题：
	 * 有个容量为V大小的背包，有很多不同重量weight[i](i=1..n)不同价值value[i](i=1..n)的物品，每种物品只有一个，
	 * 想计算一下最多能放多少价值的货物。
	 * 分析:
	 * 每个物体i，对应着两种状态：放入&不放入背包.
	 * 0-1背包的状态转移方程为:
	 * f(i,v) = max{ f(i-1,v), f(i-1,v-c[i])+w[i] }
	 * 
	 * f(i,v)表示前i个物体面对容量为v时背包的最大价值，c[i]代表物体i的cost(即重量)，w[i]代表物体i的价值；如果第i个物体不放入背包，
	 * 则背包的最大价值等于前i-1个物体面对容量v的最大价值；如果第i个物体选择放入，则背包的最大价值等于前i-1个物体面对容量v-cost[i]的最大价值加上物体i的价值w[i]。
	 * </pre>
	 * 
	 * 参考:http://www.ahathinking.com/archives/95.html

	 * 实现1: 采用一个二维数组（状态转移矩阵）dp[i][j]来记录各个子问题的最优状态，其中dp[i][j]
	 * 表示前i个物体面对容量j背包的最大价值。
	 * 
	 * 时间复杂度为O(N*V)，空间复杂度也为O(N*V)
	 * 初始化的合法状态很重要，对于第一个物体即f[0][j]，如果容量j小于第一个物体（编号为0）的重量
	 * ，则背包的最大价值为0，如果容量j大于第一个物体的重量，则背包最大价值便为该物体的价值。
	 */
	public static void pack1() {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {/* 容量为V 等号 */
				if (i > 0) {
					dp[i][j] = dp[i - 1][j];
					if (j >= weight[i]) {/* 等号 */
						int tmp = dp[i - 1][j - weight[i]] + value[i];
						dp[i][j] = (tmp > dp[i][j]) ? tmp : dp[i][j];
					}
				} else {/* 数组第0行赋值 */
					if (j >= weight[0])
						dp[0][j] = value[0];
				}
			}
		}
		System.out.println(dp[N - 1][V]);
		
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				System.out.print(dp[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * 优化:版本2
	 * 
	 * <pre>
	 * 0-1背包使用滚动数组压缩空间
	 * </pre>
	 * 
	 * 所谓滚动数组，目的在于优化空间，从上面的解法我们可以看到，状态转移矩阵使用的是一个N*V的数组，在求解的过程中，我们可以发现，
	 * 当前状态只与前一状态的解有关
	 * ,那么之前存储的状态信息已经无用了，可以舍弃的，我们只需要空间存储当前的状态和前一状态，所以只需使用2*V的空间，循环滚动使用
	 * ,就可以达到跟N*V一样的效果。这是一个非常大的空间优化。 这种空间循环滚动使用的思想很有意思，类似的，大家熟悉的斐波那契数列，f(n) =
	 * f(n-1) + f(n-2)，如果要求解f(1000)，是不需要申请1000个大小的数组的，使用滚动数组只需申请3个空间f[3]就可以完成任务。
	 */
	public static void pack2() {
		int i, j, k = 0;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {/* 容量为V 等号 */
				if (i > 0) {
					k = i & 1; /* i%2 获得滚动数组当前索引 k */
					dp[k][j] = dp[k ^ 1][j];
					if (j >= weight[i]) {/* 等号 */
						int tmp = dp[k ^ 1][j - weight[i]] + value[i];
						dp[k][j] = (tmp > dp[k][j]) ? tmp : dp[k][j];
					}
				} else {/* 数组第0行赋值 */
					if (j >= weight[0])
						dp[0][j] = value[0];
				}
			}
		}
		System.out.println(dp[k][V]);
		for (i = 0; i <= 1; i++) {
			for (j = 0; j <= V; ++j) {
				System.out.print(dp[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	static int[] maxV = new int[201]; /* 记录前i个物品中容量v时的最大价值 */

	/**
	 * 版本3
	 * 
	 * <pre>
	 * 0-1背包使用一维数组
	 * 
	 * 状态转移方程：v = V...0; f(v) = max{ f(v), f(v-c[i])+w[i] }
	 * 我们可以与二维数组的状态转移方程对比一下
	 * f(i,v) = max{ f(i-1,v), f(i-1,v-c[i])+w[i] }
	 * </pre>
	 */

	public static void pack3() {
		int i, j;
		/*
		 * 对于第i轮循环 求出了前i个物品中面对容量为v的最大价值
		 */
		for (i = 0; i < N; i++) {
			/*
			 * 内循环实际上将maxV[0...v]滚动覆盖前一轮的maxV[0...V] 可输出对照使用二维数组时的情况
			 * j从V至0逆序是防止有的物品放入背包多次
			 */
			for (j = V; j >= weight[i]; j--) {
				/*
				 * weight > j 的物品不会影响状态f[0,weight[i-1]]
				 */
				int tmp = maxV[j - weight[i]] + value[i];
				maxV[j] = (maxV[j] > tmp) ? maxV[j] : tmp;

				System.out.print(maxV[j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println(maxV[V]);
	}

	/**
	 * <pre>
	 * 0-1背包恰好背满
	 * </pre>
	 * 
	 * 在01背包中，有时问到“恰好装满背包”时的最大价值，与不要求装满背包的区别就是在初始化的时候，其实对于没有要求必须装满背包的情况下，
	 * 初始化最大价值都为0
	 * ，是不存在非法状态的，所有的都是合法状态，因为可以什么都不装，这个解就是0，但是如果要求恰好装满，则必须区别初始化，除了f[0]
	 * =0,其他的f[1…v]均设为-∞或者一个比较大的负数来表示该状态是非法的。
	 * 
	 * 这样的初始化能够保证，如果子问题的状态是合法的（恰好装满），那么才能得到合法的状态；如果子问题状态是非法的，则当前问题的状态依然非法，
	 * 即不存在恰好装满的情况。
	 */
	public static void pack4() {
		int i, j;
		for (i = 1; i <= V; ++i) {/* 初始化非法状态 */
			maxV[i] = -100;
		}

		for (i = 0; i < N; i++) {
			for (j = V; j >= weight[i]; j--) {
				int tmp = maxV[j - weight[i]] + value[i];
				maxV[j] = (maxV[j] > tmp) ? maxV[j] : tmp;
				System.out.print(maxV[j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * <pre>
	 * 0-1背包 输出最优方案 2 直接根据状态数组算
	 * 状态转移方程：f(i,v) = max{ f(i-1,v), f(i-1,v-c[i])+w[i] }
	 * </pre>
	 * 
	 * 一般来讲，背包问题都是求一个最优值，但是如果要求输出得到这个最优值的方案，就可以根据状态转移方程往后推，由这一状态找到上一状态，依次向前推即可。
	 * 这样就可以有两种实现方式，一种是直接根据状态转移矩阵向前推，另一种就是使用额外一个状态矩阵记录最优方案的路径，道理都是一样的。
	 */
	public static void pack5() {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = 0; j <= V; j++) {
				if (i > 0) {
					dp[i][j] = dp[i - 1][j];
					if (j >= weight[i]) {
						int tmp = dp[i - 1][j - weight[i]] + value[i];
						dp[i][j] = (tmp > dp[i][j]) ? tmp : dp[i][j];
					}
				} else {
					if (j >= weight[0])
						dp[0][j] = value[0];
				}
			}
		}
		System.out.println(dp[N - 1][V]);

		i = N - 1;
		j = V;
		while (i >= 1) {
			if (dp[i][j] == dp[i - 1][j - weight[i]] + value[i]) {
				System.out.println(i);
				j = j - weight[i];
			}
			i--;
		}
	}

}
