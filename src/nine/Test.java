package nine;

public class Test {

	static int Continumax(String str1, String str2) {
		int i, len1, len2, len, s1_start, s2_start, idx, curmax, max;
		len1 = str1.length();
		len2 = str2.length();
		len = len1 + len2;
		max = 0;
		for (i = 0; i < len; i++) {
			s1_start = s2_start = 0;
			if (i < len1)
				s1_start = len1 - i; // 每次开始匹配的起始位置
			else
				s2_start = i - len1;
			curmax = 0;
			for (idx = 0; (s1_start + idx < len1) && (s2_start + idx < len2); idx++) {
				if (str1.charAt(s1_start + idx) == str2.charAt(s2_start + idx))
					curmax++;
				else { // 只要有一个不相等，就说明相等的公共字符断了，不连续了，要保存curmax与max中的最大值，并将curmax重置为0
					max = curmax > max ? curmax : max;
					curmax = 0;
				}
			}
			max = curmax > max ? curmax : max;
		}
		return max;
	}

	public static void main(String[] args) {
		String str1 = "acaccbabb";
		String str2 = "acbac";
		int len = Continumax(str1, str2);
		System.out.format("最长公共连续子串的长度为：%d\n", len);
	}
}
