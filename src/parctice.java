public class parctice {

	/**
	 * 给定一个字符串类型表示的小数，输出其二进制表示
	 */
	public static String printBinary(String n) {
		int intPart = Integer.parseInt(n.substring(0, n.indexOf('.')));
		double decPart = Double.parseDouble(n.substring(n.indexOf('.'), n.length()));
		String int_string = "";
		while (intPart > 0) {
			int r = intPart % 2;
			intPart >>= 1;
			int_string = r + int_string;
		}
		StringBuffer dec_string = new StringBuffer();
		while (decPart > 0) {
			if (dec_string.length() > 32)
				return "ERROR";
			if (decPart == 1) {
				dec_string.append((int) decPart);
				break;
			}
			double r = decPart * 2;
			if (r >= 1) {
				dec_string.append(1);
				decPart = r - 1;
			} else {
				dec_string.append(0);
				decPart = r;
			}
		}
		return int_string + "." + dec_string.toString();
	}

	/**
	 * 写程序计算从整数A变为整数B需要修改的二进制位数。
	 * 
	 * <pre>
	 * 从整数A变到整数B，所需要修改的就只是A和B二进制表示中不同的位，先将A和B做异或，然后再统计结果的二进制表示中1的个数即可
	 * </pre>
	 */
	public static int bitSwapRequired(int a, int b) {
		int count = 0;
		for (int c = a ^ b; c != 0; c = c >> 1) {
			count += c & 1;
		}
		return count;
	}

	/**
	 * 交换一个整数二进制表示中的奇数位和偶数位;
	 * 分别将这个整数的奇数位和偶数位提取出来，然后移位取或即可。
	 */
	public static int swapOddEvenBits(int x) {
		return (((x & 0xaaaaaaaa) >> 1) | ((x & 0x55555555) << 1));
	}

	public static void main(String[] args) {
		System.out.println(bitSwapRequired(5, 3));
		System.out.println(swapOddEvenBits(5));
		
		int ret=0;
		for(int j=4; j>=0; --j){
			int i = ((int) (Math.random() * 100)) % 2;
			System.out.println("i:" + i);
			ret = (ret << 1) | i;
		}
		System.out.println(ret);
		//11010
	}
}
