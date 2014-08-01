package test;

public class UniqueChars {

	public static void main(String[] args) {
		String str = "ak-47";

		System.out.println(isUniqueChars(str));
	}

	/**
	 * BitMap思想
	 */
	public static boolean isUniqueChars(String str) {
		int checker = 0;
		for (int i = 0; i < str.length(); ++i) {
			int val = str.charAt(i) - 'a';
			System.out.println(val + "," + checker + "," + (checker & (1 << val)));
			if ((checker & (1 << val)) > 0)
				return false;
			checker |= (1 << val);// 第 val位 置为1
		}
		return true;
	}

}
