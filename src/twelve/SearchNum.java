package twelve;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 给你一个文件，里面包含40亿个整数，写一个算法找出该文件中不包含的一个整数， 假设你有1GB内存可用。 如果你只有10MB的内存呢？
 * 
 * <pre>
 * 将int型变量a的第k位清0，即a=a&~(1<<k) 
 * 将int型变量a的第k位置1， 即a=a|(1<<k)
 * </pre>
 */
public class SearchNum {

	/*
	 * 第一种情况
	 */
	static byte[] bitfield = new byte[0xFFFFFFF / 8];

	static void findOpenNumber2() throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader("src/twelve/input_file.txt"));
		while (in.hasNextInt()) {
			int n = in.nextInt();
			/*
			 * Finds the corresponding number in the bitfield by using the OR
			 * operator to set the nth bit of a byte (e.g.. 10 would correspond
			 * to the 2nd bit of index 2 in the byte array).
			 */
			// bitfield[n>>3] |= 1 << (n & 0X7);
			bitfield[n / 8] |= 1 << (n % 8);// 第(n % 8)位置置为1
		}
		in.close();
		for (int i = 0; i < bitfield.length; i++) {
			for (int j = 0; j < 8; j++) {
				/*
				 * Retrieves the individual bits of each byte. When 0 bit is
				 * found, finds the corresponding value.
				 */
				if ((bitfield[i] & (1 << j)) == 0) {
					System.out.println(i + ":" + (i * 8 + j));
					return;
				}
			}
		}
	}

	/*
	 * 第二种情况:如果你只有10MB的内存呢？
	 */
	 static int bitsize = 1048576; // 2^20 bits (2^17 bytes)
	 static int blockNum = 4096; // 2^12
	static byte[] bitfield2 = new byte[bitsize / 8];
	static int[] blocks = new int[blockNum];

	static void findOpenNumber() throws FileNotFoundException {
		int starting = -1;
		Scanner in = new Scanner(new FileReader("src/twelve/input_file.txt"));
		while (in.hasNextInt()) {
			int n = in.nextInt();
			blocks[n / (bitfield2.length * 8)]++;
		}
		in.close();

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] < bitfield2.length * 8) {
				/*
				 * if value < 2^20, then at least 1 number is missing in that
				 * section.
				 */
				starting = i * bitfield2.length * 8;
				break;
			}
		}

		in = new Scanner(new FileReader("src/twelve/input_file.txt"));
		while (in.hasNextInt()) {
			int n = in.nextInt();
			/*
			 * If the number is inside the block that’s missing numbers, we
			 * record it
			 */
			if (n >= starting && n < starting + bitfield2.length * 8)
				bitfield2[(n - starting) / 8] |= 1 << ((n - starting) % 8);
		}
		in.close();

		for (int i = 0; i < bitfield2.length; i++) {
			for (int j = 0; j < 8; j++) {
				/*
				 * Retrieves the individual bits of each byte. When 0 bit is
				 * found, finds the corresponding value.
				 */
				if ((bitfield2[i] & (1 << j)) == 0) {
					System.out.println(i * 8 + j + starting);
					return;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		// findOpenNumber2();
		findOpenNumber();
		System.out.println(Integer.SIZE);
	}
}
