package nine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * The longest palindrome substring
 */
/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月3日 下午3:37:43]
 * @Version: [v1.0]
 * 
 */
/**
 * 题：给出一个只由小写英文字符a,b,c...y,z组成的字符串S,求S中最长回文串的长度。回文就是正反读都是一样的字符串,如aba, abba等。
 * 
 * 例如：aaaa与abab：最长的回文串长度分别为4、3。
 */
public class LPS {

	/* 最长重复子串 后缀数组 */
	Set<String> suff = new TreeSet<String>();// 存储后缀数组，并排序
	String FLAG = "#";
	int startIndex;// 记录公共字符串截取的起始位置

	public String LCS_suffix(String str) {
		int maxlen, maxindex;
		String str2 = reverse(str);
		String arr = str + FLAG + str2;
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
			return new Integer[] { 0, 0 };
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
		return new Integer[] { maxlen, startIndex };
	}

	private String reverse(String str) {
		if ((null == str) || (str.length() <= 1)) {
			return str;
		}
		return reverse(str.substring(1)) + str.charAt(0);
	}

	/**
	 * O(n)解法 参考:http://www.felix021.com/blog/read.php?2040
	 */
	int maxid; // maxid表示最大回文子串中心的位置
	int max_rb; // max_rb则为id+LPS_rb[id]，也就是最大回文子串的边界。
	int maxlen; // maxlen记录最大回文字串的长度
	int maxindex; // maxindex记录最大回文字串的下标
	/**
	 * <pre>
	 * LPS_rb 记录以字符S[i]为中心的最长回文子串向左/右扩张的长度（包括S[i]，也就是把该回文串“对折”以后的长度），比如S和P的对应关系： 
	 * S # 1 # 2 # 2 # 1 # 2 # 3 # 2 # 1 # 
	 * P 1 2 1 2 5 2 1 4 1 2 1 6 1 2 1 2 1 
	 * (p.s. 可以看出，P[i]-1正好是原字符串中回文串的总长度）
	 * </pre>
	 */
	int[] LPS_rb = new int[100]; // i为中心的回文子串右边界下标right border
	char[] str = new char[100]; // 原字符串处理后的副本

	void LPS_linear(char[] X, int xlen) {
		max_rb = maxid = 0;

		str[0] = '$'; // 将原串处理成所需的形式
		for (int i = 0; i < X.length; i++) {
			int n = 2 * i + 2;
			str[n - 1] = '#';
			str[n] = X[i];
			str[n + 1] = '#';
		}

		int len = 2 * X.length + 2;
		for (int i = 1; i < len; i++) {// 计算LPS_rb的值
			// 记j = 2 * maxid - i，也就是说 j 是 i 关于 maxid 的对称点。
			LPS_rb[i] = max_rb > i ? Math.min(LPS_rb[2 * maxid - i], (max_rb - i)) : 1;/* 最关键的部分 */
			while (str[i - LPS_rb[i]] == str[i + LPS_rb[i]])
				// 左右扩展
				LPS_rb[i]++;

			if (i + LPS_rb[i] > max_rb) {
				max_rb = i + LPS_rb[i];
				maxid = i;
				if (LPS_rb[i] - 1 > maxlen) {
					maxlen = LPS_rb[i] - 1;
					maxindex = i;
				}
			}
		}
		System.out.println(str);
		System.out.println(maxlen);
		System.out.println(maxindex);
	}

	public static void main(String[] args) {
		String str = "babcbabcbaccba";
		// String str = "abaaba";
		LPS lps = new LPS();
		// System.out.println(lps.LCS_suffix(str));//abcbabcba
		lps.LPS_linear(str.toCharArray(), str.length());
	}
}
