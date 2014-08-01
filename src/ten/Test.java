package ten;

public class Test {

	public static int search(int[] arr, int left, int right, int x) {
		while (left <= right) {
			int mid = (left + right) >> 1;
			if (arr[mid] <= x) {
				if (arr[mid] >= arr[left]) {
					left = mid + 1;
				}

			}

		}
		return 0;
	}
}
