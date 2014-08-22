package bitmap;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月22日 上午9:42:01]
 * @Version: [v1.0]
 * 
 */
public class OneBitmap {

	static final int INTBITS = 32;
	static final int MASK = 0x1f;
	static final int SHIFT = 5;
	static final int CHARNUM = 256;
	int[] a = new int[CHARNUM / INTBITS];

	boolean get(int i) {
		return (a[i >> SHIFT] & (1 << (i & MASK))) != 0;
	}

	void set(int i) {
		a[i >> SHIFT] |= 1 << (i & MASK);
	}

	void clear(int i) {
		a[i >> SHIFT] &= ~(1 << (i & MASK));
	}

	public static void main(String[] args) {
		OneBitmap bitmap = new OneBitmap();
		bitmap.set(10);
		System.out.println(bitmap.get(10));
	}
}
