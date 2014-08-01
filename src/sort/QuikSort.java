package sort;
public class QuikSort {

	public static int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int i = low - 1;
		int j, tmp;
		for (j = low; j < high; j++) {
			if (arr[j] < pivot) {
				tmp = arr[++i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}
		tmp = arr[++i];
		arr[i] = arr[high];
		arr[high] = tmp;
		return i;
	}

	public static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int mid = partition(arr, low, high);
			quickSort(arr, low, mid - 1);
			quickSort(arr, mid + 1, high);
		}
	}

	public static void main(String[] args) {
		// int[] arr = { 1, 4, 6, 2, 5, 8, 7, 6, 9, 12 };
		int[] arr = { 2, 1, 8, 4, 5, 10, 17, 9, 18, 6 };
		// System.out.println(partition(arr, 0, 9));
		quickSort(arr, 0, 9);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

	}
}
