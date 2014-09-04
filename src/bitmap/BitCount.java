package bitmap;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月4日 下午5:10:39]
 * @Version: [v1.0]
 * 
 */
public class BitCount {
	int bitcount_1(int x) {
		int count = 0;
		for (int i = 1 << 31; i > 0; i >>= 1) {
			if ((x & i) > 0) {
				++count;
			}
		}
		return count;
	}

	int bitcount_2(int x) {
		int count = 0;
		for (; x > 0; x >>= 1) {
			if ((x & 1) > 0) {
				++count;
			}
		}
		return count;
	}

	int bitcount_3(int x) {
		int count = 0;
		for (; x != 0; x &= (x - 1)) {// 清除最低位的1
			++count;
		}
		return count;
	}

	void displaybits(int x) {// 打印二进制
		for (int i = (1 << 30); i > 0; i >>= 1) {
			System.out.format("%c", (x & i) > 0 ? '1' : '0');
		}
		System.out.format("\n");
	}

	public static void main(String[] args) {
		int y = 521;
		BitCount bitCount = new BitCount();
		bitCount.displaybits(y);
		System.out.format("%d\n", bitCount.bitcount_3(y));
	}
}
