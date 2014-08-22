package bitmap;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月22日 上午10:45:09]
 * @Version: [v1.0]
 * 
 */
/**
 * 4种状态,00表示不存在，01表示出现一次，10表示出现2次或者多次，11没有定义
 * <pre>
 * 在2.5亿个整数找出不重复的整数，内存不足以容纳着2.5亿个整数
 * 2.5*10^8*4B = 1G
 * ==>换成bit
 * 2.5*10^8/8 B= 32M
 * 每个数占用两个bit,则为 32*2 = 64M
 * 
 * 如果这2.5亿个数里面既有正数又有负数那么就用两个2Bitmap分别存储正数和负数（取绝对值存储），零就随便放
 * <pre>
 */
public class TwoBitmapAdv {
	/*
	 * &0xFF其实没用，作用就是为了“确保”，char为8个bit位
	 */
	int[] bitmap = new int[10];

	// x表示一个整数，num表示bitmap中已经拥有x的个数
	// 由于我们只能用2个bit来存储x的个数，所以num的个数最多为3
	void set(int x, int num) {
		int m = x >> 2;// x / 4
		int n = x & 3;// x % 4

		// 将x对于为值上的个数值先清零，但是有要保证其他位置上的数不变
		// bitmap[m] &= ~((0x3 << (2 * n)) & 0xFF);
		bitmap[m] &= ~(0x3 << (2 * n));
		// 重新对x的个数赋值
		// bitmap[m] |= ((num & 3) << (2 * n) & 0xFF);
		// num += 3;
		num = num > 3 ? 3 : num;
		bitmap[m] |= (num & 3) << (2 * n);
	}

	void clear(int x) {
		int m = x >> 2;
		int n = x & 3;
		// bitmap[m] &= ~((0x3 << (2 * n)) & 0xFF);
		bitmap[m] &= ~(0x3 << (2 * n));// 2-bitmap
		// bitmap[m] &= ~(1 << (n));//bitmap
	}

	int get(int x) {
		int m = x >> 2;
		int n = x & 3;

		return ((bitmap[m] & (0x3 << (2 * n))) >> (2 * n));
	}

	void add(int x) {
		int num = get(x);
		set(x, num + 1);
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 1, 4, 5, 5, 5, 5, 7 }; // 找出数组a中不重复的元素
		TwoBitmapAdv bitmap = new TwoBitmapAdv();
		for (int i = 0; i < a.length; i++) {
			bitmap.add(a[i]);
		}

		// 现在可以查看每个元素出现的次数
		for (int i = 0; i < a.length; i++) {
			System.out.format("%d %d", a[i], bitmap.get(a[i]));
		}
		System.out.println("\n=============\n");

		System.out.println(bitmap.get(2) > 0);
	}
}
