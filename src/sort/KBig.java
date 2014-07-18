package sort;
public class KBig {

	/*
	 * 为了简单起见，这里只是单纯的选取第一个元素作为枢纽元素。这样选取枢纽，就难避免使得算法容易退化。
	 */
	private int kBig(int arr[], int low, int high, int k) {
		int pivot = arr[low];
		int high_tmp = high;
		int low_tmp = low;
		while (low < high) {
			// 从右向左查找，直到找到第一个小于枢纽元素为止
			while (low < high && arr[high] >= pivot) {
				--high;
			}
			arr[low] = arr[high];
			// 从左向右查找，直到找到第一个大于枢纽元素为止
			while (low < high && arr[low] <= pivot) {
				++low;
			}
			arr[high] = arr[low];
		}
		arr[low] = pivot;
		if (low == k - 1) {
			return arr[low];
		} else if (low > k - 1) {
			return kBig(arr, low_tmp, low - 1, k);
		} else {
			return kBig(arr, low + 1, high_tmp, k);
		}
	}

	public static void main(String[] args) {
		KBig big = new KBig();
		int[] arr = { 2, 1, 0, 8, 4, 5, 10, 16, 9, 13 };
		int i, k = 9;
		System.out.println(big.kBig(arr, 0, 9, k));
		for (i = 0; i < k; i++)
			System.out.print(arr[i] + ", ");
	}
}
