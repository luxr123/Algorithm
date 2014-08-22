package bitmap;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月22日 上午9:36:35]
 * @Version: [v1.0]
 * 
 */
public class TwoBitmap extends OneBitmap {

	int getT(int i) {
		if (!get(i) && !get(i + 1)) /* 检查表示数字的两位数字 */
			return 0;
		else if (!get(i) && get(i + 1))
			return 1;
		else if (get(i) && !get(i + 1))
			return 2;
		else
			return 3;
	}

	/**
	 * 4种状态,00表示不存在，01表示出现一次，10表示出现2次或者多次，11没有定义
	 */
	void setT(int i) {
		/* 检测对应位的当前值，然后进行变换 */
		int t = getT(i);
		switch (t) {
		case 0:
			set(i + 1);
			break;
		case 1:
			set(i);
			clear(i + 1);
			break;
		case 2:
			break;
		}
	}

	/* 清除当前值 */
	void clearT(int i) {
		clear(i);
		clear(i + 1);
	}

	public static void main(String[] args) {
		TwoBitmap bitmap = new TwoBitmap();
		int[] b = { 1, 4, 2, 3 };
		for (int i = 0; i < b.length; i++)
			bitmap.setT(b[i] * 2); /* 注意乘以2 */
		for (int i = 0; i < CHARNUM / INTBITS; i += 2)
			if (bitmap.getT(i) == 1)
				System.out.format("%d\n", i / 2);
	}
}
