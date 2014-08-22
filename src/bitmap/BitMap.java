package bitmap;

/*
 * 题目：编写函数squeeze(s1,s2)，将字符串s1中任何与字符串s2中字符匹配的字符都删除。
 * 
 * 分析：这道题目的注意点主要有两个，一是搜索匹配的字符，二是删除字符时后续字符的前移。这两方面都是有技巧在里面的，见代码
 */
public class BitMap {

	/**
	 * 方法一：暴力搜索
	 */
	String squeeze1(String s1, String s2) {
		String ret = "";
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (!isHave(s2, c))
				ret += c;
		}
		return ret;
	}

	boolean isHave(String s, char c) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c)
				return true;
		}
		return false;
	}

	/**
	 * 方法二：基于字符ASCII码的索引
	 */
	byte[] index = new byte[256];// ASCII码为0

	String squeeze2(String s1, String s2) {
		creatIndex(s2);
		String ret = "";
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if (index[c] != 1)
				ret += c;
		}
		return ret;
	}

	void creatIndex(String s) {
		for (int i = 0; i < s.length(); i++)
			index[s.charAt(i)] = 1;
	}

	/**
	 * 方法三：位索引(bit-map) void set(int i) { index[i >> SHIFT] |= 1 << (i & MASK);
	 * } //i&MASK相当于对32取余，即看i用第几位表示
	 */
	static final int INTBITS = 32;
	static final int MASK = 0x1f;
	static final int SHIFT = 5;
	static final int CHARNUM = 256;
	int[] bitfield = new int[CHARNUM / INTBITS];

	String squeeze3(String s1, String s2) {
		creatIndex2(s2);
		String ret = "";
		for (int i = 0; i < s1.length(); i++) {
			char c = s1.charAt(i);
			if ((bitfield[c >> SHIFT] & 1 << (c & MASK)) == 0)
				ret += c;
		}
		return ret;
	}

	void creatIndex2(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			bitfield[c >> SHIFT] |= 1 << (c & MASK);
		}
	}

	void clear(String s) {
		// a[i>>5] &= ~(1<<(mas - i & mas));
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			bitfield[c >> SHIFT] &= ~(1 << (c & MASK));
		}
	}

	public static void main(String[] args) {
		BitMap bitMap = new BitMap();
		String s1 = "abcdefghigklmn";
		String s2 = "helloworld";
		System.out.println(bitMap.squeeze1(s1, s2));

		System.out.println(bitMap.squeeze2(s1, s2));

		System.out.println(bitMap.squeeze3(s1, s2));

	}
}
