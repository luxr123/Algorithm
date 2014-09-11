package sort;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月11日 下午3:59:03]
 * @Version: [v1.0]
 * 
 */
public class ShellSort {
	// 希尔排序,按自然顺序
	public void shellSort(int[] array, int len) {
		int i, index, key;
		for (int gap = len / 2; gap > 0; gap /= 2) { // 每趟步长折半
			// 直接插入排序
			for (index = gap; index < len; index++) {
				key = array[index];
				for (i = index - gap; i >= 0 && array[i] > key; i -= gap) {
					array[i + gap] = array[i];
				}
				array[i + gap] = key;
			}
		}
	}

	public static void main(String[] args) {
		ShellSort shellSort = new ShellSort();
		int[] array = { 6, 5, 3, 1, 8, 7, 2, 4 };
		shellSort.shellSort(array, array.length);// 注意为数组的个数
		for (int m = 0; m <= array.length - 1; m++) {
			System.out.print(array[m] + "\t");
		}
	}
}
