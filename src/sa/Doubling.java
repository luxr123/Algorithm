package sa;

import java.util.Arrays;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月9日 下午1:24:11]
 * @Version: [v1.0]
 * 
 */
public class Doubling {
	static final int MAXN = 1000;
	int[] wa = new int[MAXN];
	int[] wb = new int[MAXN];
	int[] wv = new int[MAXN];
	int[] ws = new int[MAXN];

	private boolean cmp(int[] r, int a, int b, int l) {
		return (r[a] == r[b]) && (r[a + l] == r[b + l]);
	}

	public void da(int[] r, int[] sa, int n, int m) {
		int i, j, p;
		int[] x = wa, y = wb;
		int[] t = new int[MAXN];

		// 以下4句是简单的计数排序
		for (i = 0; i < m; i++) ws[i] = 0;
		for (i = 0; i < n; i++) ws[x[i] = r[i]]++;
		for (i = 1; i < m; i++) ws[i] += ws[i - 1];
		for (i = n - 1; i >= 0; i--) sa[--ws[x[i]]] = i;
		
		/* 每做一次循环,可以得到排名的数量p,由p的值优化m的值,节省空间,这是m=p的理由 */
		for (j = 1, p = 1; p < n; j *= 2, m = p) {
			for (p = 0, i = n - j; i < n; i++) y[p++] = i;
			for (i = 0; i < n; i++) if (sa[i] >= j) y[p++] = sa[i] - j;
			/*以上步骤,已经将第二关键排好序了.接着将2j长度的字符串的第一关键字的排名序号提取到wv中.*/
			for (i = 0; i < n; i++) wv[i] = x[y[i]];
			/*以下几行,在第二关键字排好序的基础上,对第一关键字排序*/
			for (i = 0; i < m; i++) ws[i] = 0;
			for (i = 0; i < n; i++) ws[wv[i]]++;
			for (i = 1; i < m; i++) ws[i] += ws[i - 1];
			for (i = n - 1; i >= 0; i--) sa[--ws[wv[i]]] = y[i];
			/*
			 * 通过以上步骤,已经将长度为2j的字符串排序完毕,存放在sa数组中.
			 * 接着由sa数组求得排名数组rank.p表示排名,当两个字符串排名一致时,
			 * 他们的p值相等,否则p向上递增.当p==n时,表示2j长度的字符串的排名
			 * 已经能够互相区分,没有了相同排名的字符串.那么2j+2j长度的字符串的 排名也能根据此进行区分了,因此当p>=n时循环可以结束了.
			 */
			for (t = x, x = y, y = t, p = 1, x[sa[0]] = 0, i = 1; i < n; i++)
				x[sa[i]] = cmp(y, sa[i - 1], sa[i], j) ? p - 1 : p++;
		}
	}
	
	int[] rank = new int[MAXN];
	static int[] height = new int[MAXN];
	  
	// 定义height[i]=suffix(sa[i-1])和suffix(sa[i])的最长公
	// 共前缀，也就是排名相邻的两个后缀的最长公共前缀
	// 任意两个起始位置为i,j(假设rank[i]<rank[j])的后缀的最长公共前缀
	// 为height[rank[i]+1]、height[rank[i]+2]…height[rank[j]]的最小值
	/* h[i]>=h[i-1]-1 其中, h[i]=height[rank[i]], 且i>1&&rank[i]>1 */
	void calheight(int[] r, int[] sa, int n) {
		int i, j, k = 0;
		for (i = 1; i <= n; i++) rank[sa[i]] = i; //计算每个字符串的字典序排名
		for (i = 0; i < n; height[rank[i++]] = k)//将计算出来的height[rank[i]]的值，也就是k，赋给height[rank[i]]。i是由0循环到n-1，
			//但实际上height[]计算的顺序是由height[rank[0]]计算到height[rank[n-1]]。
			for (k = k > 0 ? k-1 : 0, j = sa[rank[i] - 1]; r[i + k] == r[j + k]; k++);//上一次的计算结果是k，首先判断一下如果k是0的话，那么k就不用动了，
			//从首字符开始看第i个字符串和第j个字符串前面有多少是相同的，如果k不为0，按我们前面证明的，最长公共前缀的长度至少是k-1，于是从首字符后面k-1个字符开始检查起即可。  
	}

	public static void main(String[] args) {
		String s = "aabaaaab";
		int len = s.length();
		Doubling doubling = new Doubling();
		int[] r = new int[len + 1];
		int[] sa = new int[len + 1];
		/*sa[0] = n, rank[n] = 0*/
		for (int i = 0; i < len; i++) {
//			r[i] = (int)s.charAt(i);
			r[i] = s.charAt(i) - 'a' + 1;
		}
		r[len] = 0;
		doubling.da(r, sa, len + 1, 128);
		System.out.println("sa:" + Arrays.toString(sa));
		
		doubling.calheight(r, sa, len);
		System.out.println("height:" + Arrays.toString(height));
		// 最后再说明一点，就是关于da和calheight的调用问题，实际上在“小罗”写的源程序里面是如下调用的，这样我们也能清晰的看到da和calheight中的int
		// n不是一个概念，同时height数组的值的有效范围是height[1]~height[n]其中height[1]=0，原因就是sa[0]实际上就是我们补的那个0，所以sa[1]和sa[0]的最长公共前缀自然是0。
		
		for (int i = 1; i <= len; i++) {
			System.out.println(s.substring(sa[i]));
		}
	}
}
