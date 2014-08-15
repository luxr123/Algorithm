/**
 * 问题：有个容量为V大小的背包，有很多不同重量weight[i](i=1..n)不同价值value[i](i=1..n)的货物，第i种物品最多有n[
 * i]件可用，计算一下最多能放多少价值的货物。
 * 对于多重背包的基本实现，与完全背包是基本一样的，不同就在于物品的个数上界不再是v/c[i]而是n[i]与v/c[i]中较小的那个。状态转移方程如下
 * 
 *  f(i,v) = max{ f(i-1,v-k*c[i]) + k*w[i] | 0<=k<=n[i] }
 * 代码与完全背包的区别仅在内部循环上由
 * 
 *  for(k = 1; k <= j/weight[i]; k++) 变为
 * 
 *  for(k = 1; k <=n[i] && k<=j/weight[i]; k++) 当然，输入上的区别就不说了。
 */
package bags;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月13日 上午9:34:01]
 * @Version: [v1.0]
 * 
 */
public class MulKnapSack {

	static final int N = 3, V = 15;;
	static int[] weight = new int[50];
	static int[] value = new int[50];
	static int[] maxV = new int[201];/* 记录前i个物品中容量v时的最大价值 */

	/**
	 * 多重背包 二进制拆分
	 * 
	 * <pre>
	 * Time Complexity  大于O(N*V)
	 * Space Complexity O(N*V)
	 * 设 V <= 200 N <= 10 ，拆分后 物品总数 < 50
	 * 每件物品有 log n[i]种状态
	 * </pre>
	 * 
	 * 跟完全背包一样的道理，利用二进制的思想将n[i]件物品i拆分成若干件物品，目的是在0-n[i]中的任何数字都能用这若干件物品代换，另外，超过n[i
	 * ]件的策略是不允许的。
	 * 
	 * 方法是将物品i分成若干件，其中每一件物品都有一个系数，这件物品的费用和价值都是原来的费用和价值乘以这个系数，使得这些系数分别为1,2,4,…,2^
	 * (k-1),n[i]-2^k+1，且k满足n[i]-2^k+1>0的最大整数。例如，n[i]=13，就将该物品拆成系数为1、2、4、6的四件物品。
	 * 分成的这几件物品的系数和为n
	 * [i]，表明不可能取多于n[i]件的第i种物品。另外这种方法也能保证对于0..n[i]间的每一个整数，均可以用若干个系数的和表示。
	 */
	public static void mulKnapsack() {
		int i, j;
		int[] weig = new int[] { 3, 4, 5 };
		int[] val = new int[] { 4, 6, 7 };
		int count = 0;
		for (i = 0; i < N; i++) {
			int num = i + 2;
			for (j = 1; j <= num; j <<= 1) {// 二进制拆分
				weight[count] = j * weig[i];
				value[count++] = j * val[i];
				num -= j;
			}
			if (num > 0) {
				weight[count] = num * weig[i];
				value[count++] = num * val[i];
			}
		}
		for (i = 0; i < count; i++) {// 使用01背包
			for (j = V; j >= weight[i]; j--) {
				int tmp = maxV[j - weight[i]] + value[i];
				maxV[j] = maxV[j] > tmp ? maxV[j] : tmp;
			}
		}
		System.out.println(maxV[V]);
	}

	public static void main(String[] args) {
		mulKnapsack();
	}
}
