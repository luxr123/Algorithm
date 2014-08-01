package sort;

public class Test {

	static int binarySearch(int[] arr, int n, int value) {
		int left = 0;
		int right = n - 1;
		while (left <= right) {
			int mid = left + ((right - left) >> 1);
			if (value > arr[mid])
				left = mid + 1;
			else if (value < mid)
				right = mid - 1;
			else
			return mid;
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a = { 1, 5, 26, 28, 34, 56, 98, 99, 100, 108 };
		System.out.println(binarySearch(a, 10, 99));
	}
}
