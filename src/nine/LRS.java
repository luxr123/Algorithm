package nine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* 最长重复子串(有覆盖) Longest Repeat Substring */
public class LRS {

	/**
	 * 解法1:最直接的方法就是子串和子串间相互比较，这样查看所有的子串对，时间复杂度为O(n^2)
	 */
	int maxlen; /* 记录最长重复子串长度 */
	int maxindex; /* 记录最长重复子串的起始位置 */

	/* 最长重复子串 基本算法 */
	int comlen(String str, int p, int q) {
		int len = 0;
		while (p < str.length() && q < str.length() && str.charAt(p++) == str.charAt(q++)) {
			len++;
		}
		return len;
	}

	void LRS_base(String arr, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				int len = comlen(arr, i, j);
				if (len > maxlen) {
					maxlen = len;
					maxindex = i;
				}
			}
		}
		outputLRS(arr);
	}

	/**
	 * 解法2:后缀数组
	 * 
	 * <pre>
	 *                                            i(后缀数组下标)          maxlen(最大的相同数量，根据此数量截取)
	 * 0. banana            0. a                  0(0-1数组比较以此类推)    1  
	 * 1. anana             1. ana                1                       3
	 * 2. nana       ==>    2. anana          =>  2                       0
	 * 3. ana        sort   3. banana             3                       0
	 * 4. na                4. na                 4                       2
	 * 5. a                 5. nana
	 * </pre>
	 */
	/* 最长重复子串 后缀数组 */
	Set<String> suff = new TreeSet<String>();// 存储后缀数组，并排序

	int startIndex;// 记录公共字符串截取的起始位置

	public String LRS_suffix(String arr, int size) {
		maxlen = maxindex = 0;
		for (int i = 0; i < size; i++) { /* 初始化后缀数组 */
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
		List<String> results = new ArrayList<String>();

		String result = suffList.get(maxindex).substring(startIndex, startIndex + maxlen);// 根据最长的重复子序列下标和截取位置，获得最长的重复子序列

		return result;
	}

	Integer[] comlen(String next, String next2) {
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
		}
		maxlen = maxlen < tmp ? tmp : maxlen;
		return new Integer[] { maxlen, startIndex };
	}

	/*
	 * 输出LRS 在后缀数组方法中，maxindex=0 因为传进来的就是后缀数组suff[]，从0打印maxlen个字符
	 */
	void outputLRS(String arr) {
		if (maxlen == 0) {
			System.out.format("NULL LRS\n");
			return;
		}
		System.out.format("The len of LRS is %d\n", maxlen);

		int i = maxindex;
		while (maxlen-- > 0) {
			System.out.format("%c", arr.charAt(i++));
		}
	}

	public static void main(String[] args) {
		LRS lrs = new LRS();
//		String X = "anbnanana";
		 String X = "banana";
		/* 基本算法 */
		// lrs.LRS_base(X, X.length());

		String res = lrs.LRS_suffix(X, X.length());
		System.out.println(res);
	}
}
