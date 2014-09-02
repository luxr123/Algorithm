package sort;

public class QuikSortAdv {

	private static int partition(int[] arr, int low, int high) {
		int pivot = arr[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && arr[high] >= pivot)
				high--;
			arr[low] = arr[high]; // 比中轴小的记录移到低端

			while (low < high && arr[low] <= pivot)
				low++;
			arr[high] = arr[low]; // 比中轴大的记录移到高端
		}
		arr[low] = pivot; // 中轴记录到尾
		return low; // 返回中轴的位置
	}

	public static void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int mid = partition(arr, low, high);// 将list数组进行一分为二
			quickSort(arr, low, mid - 1);// 对低字表进行递归排序
			quickSort(arr, mid + 1, high); // 对高字表进行递归排序
		}
	}

	public static void main(String[] args) {
		// int[] arr = { 1, 4, 6, 2, 5, 8, 7, 6, 9, 12 };
		int[] arr = { 2, 1, 8, 4, 5, 10, 2, 9, 18, 6 };
		// System.out.println(partition(arr, 0, 9));
		quickSort(arr, 0, arr.length - 1);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

	}
}
