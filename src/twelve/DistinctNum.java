package twelve;

/**
 * 有一个数组，里面的数在1到N之间，N最大为32000.数组中可能有重复的元素(即有的元素
 * 存在2份)，你并不知道N是多少。给你4KB的内存，你怎么把数组中重复的元素打印出来。
 * 
 * <pre>
 * 我们有4KB的内存，一共有4 * 2^10 * 8位，大于32000，所以我们可以用Bit Map 来做这道题目
 * </pre>
 * 
 */
public class DistinctNum {

	public static void checkDuplicates(int[] array) {
		BitSet bs = new BitSet(32000);
		for (int i = 0; i < array.length; i++) {
			int num = array[i];
			if (bs.get(num)) {
				System.out.format("%d ",num);
			} else {
				bs.set(num);
			}
		}
	}

	public static void main(String[] args) {
		int a[] = { 0, 0, 1, 2, 3, 4, 5, 32000, 7, 8, 9, 10, 11, 1, 2, 13, 15, 16, 32000, 11, 5, 8 };
		checkDuplicates(a);
	}

}

class BitSet {
	int[] bitset;

	public BitSet(int size) {
		bitset = new int[1 + (size/Integer.SIZE)]; // divide by 32
//		bitset = new int[1 + (size >> 5)]; // divide by 32
	}

	boolean get(int pos) {
		int wordNumber = (pos >> 5); // divide by 32
		int bitNumber = (pos & 0x1F); // mod 32
		return (bitset[wordNumber] & (1 << bitNumber)) != 0;
	}

	void set(int pos) {
		int wordNumber = (pos >> 5); // divide by 32
		int bitNumber = (pos & 0x1F); // mod 32
		// int wordNumber = (pos / 32); // divide by 32
		// int bitNumber = (pos % 32); // mod 32
		bitset[wordNumber] |= 1 << bitNumber;// 第 bitNumber位 置为1
	}
}
