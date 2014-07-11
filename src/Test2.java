public class Test2 {

	public int partition(int[] arr, int start, int end) {
		int pivort = arr[end];
		int low = start - 1;
		int tmp;
		for (int i = start; i < end; i++) {
			if (arr[i] < pivort) {
				// swap
				low += 1;
				if (low != i) {
					arr[low] = arr[low] + arr[i];
					arr[i] = arr[low] - arr[i];
					arr[low] = arr[low] - arr[i];
				}

				// tmp = arr[low];
				// arr[low] = arr[i];
				// arr[i] = tmp;
			}
		}

		low += 1;
		// arr[low] = arr[low] + arr[end];
		// arr[end] = arr[low] - arr[end];
		// arr[low] = arr[low] - arr[end];

		if (low != end) {
			tmp = arr[low];
			arr[low] = arr[end];
			arr[end] = tmp;
		}

		return low;
	}

	public void quickSort(int[] arr, int start, int end) {
		if (start < end) {
			int mid = partition(arr, start, end);
			quickSort(arr, start, mid - 1);
			quickSort(arr, mid + 1, end);
		}
	}

	// ////////////////////////////////////////

	public void k_min(int[] arr, int len, int k) {
		int i;
		bulidMaxHeap(arr, k);
		for (i = k; i < len; i++) {
			if (arr[i] < arr[0]) {
				arr[0] = arr[i];
				maxHeapily(arr, 1, k);
			}
		}
	}

	public void heapSort(int[] source, int len) {
		if (null == source || len <= 1)
			return;
		bulidMaxHeap(source, len);
		for (int lastElement = len - 1; lastElement >= 1; lastElement--) {
			swap(source, 0, lastElement);
			maxHeapily(source, 1, lastElement);
		}

	}

	private void bulidMaxHeap(int[] source, int len) {
		int beginFlag = (int) Math.floor(len / 2);
		for (int i = beginFlag; i >= 1; i--) {
			maxHeapily(source, i, len);
		}
	}

	private void maxHeapily(int[] source, int i, int len) {
		int LEFT = left(i);
		int RIGHT = right(i);
		int largest = i;
		if (LEFT <= len && source[LEFT - 1] >= source[i - 1])
			largest = LEFT;
		if (RIGHT <= len && source[RIGHT - 1] >= source[largest - 1])
			largest = RIGHT;
		if (largest != i) {
			swap(source, i - 1, largest - 1);
			maxHeapily(source, largest, len);
		}

	}

	private void swap(int[] source, int i, int largest) {
		source[i] = source[i] + source[largest];
		source[largest] = source[i] - source[largest];
		source[i] = source[i] - source[largest];
	}

	private int left(int i) {
		return 2 * i;
	}

	private int right(int i) {
		return 2 * i + 1;
	}

	public static void main(String[] args) {
		Test2 test = new Test2();
		int[] arr = { 2, 1, 8, 4, 5, 10, 17, 19, 18, 6 };
		// test.quickSort(arr, 0, 9);
//		test.heapSort(arr, 6);
		test.k_min(arr, 10, 4);
		for (int i = 0; i < 4; i++)
			System.out.print(arr[i] + " ");
	}
}
