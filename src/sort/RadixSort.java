package sort;

import java.util.Arrays;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年9月5日 下午6:16:17]
 * @Version: [v1.0]
 * 
 */
public class RadixSort {
	public static int[] radixSort(int[] ArrayToSort, int digit) {
		// low to high digit
		for (int k = 1; k <= digit; k++) {
			// temp array to store the sort result inside digit
			int[] tmpArray = new int[ArrayToSort.length];

			// temp array for countingsort
			int[] tmpCountingSortArray = new int[10];

			// CountingSort
			for (int i = 0; i < ArrayToSort.length; i++) {
				// split the specified digit from the element
				int tmpSplitDigit = ArrayToSort[i] / (int) Math.pow(10, k - 1) - (ArrayToSort[i] / (int) Math.pow(10, k)) * 10;
				tmpCountingSortArray[tmpSplitDigit] += 1;
			}
			for (int m = 1; m < 10; m++) {
				tmpCountingSortArray[m] += tmpCountingSortArray[m - 1];
			}
			// output the value to result
			for (int n = ArrayToSort.length - 1; n >= 0; n--) {
				int tmpSplitDigit = ArrayToSort[n] / (int) Math.pow(10, k - 1) - (ArrayToSort[n] / (int) Math.pow(10, k)) * 10;
				tmpArray[tmpCountingSortArray[tmpSplitDigit] - 1] = ArrayToSort[n];
				tmpCountingSortArray[tmpSplitDigit] -= 1;
			}

			// copy the digit-inside sort result to source array
			for (int p = 0; p < ArrayToSort.length; p++) {
				ArrayToSort[p] = tmpArray[p];
			}
			
			System.out.println(Arrays.toString(ArrayToSort));
		}

		return ArrayToSort;
	}

	public static void main(String[] args) {
//		int[] ary = new int[] { 2, 3, 2, 5, 3, 2, 8 };
		int[] ary = new int[] { 332, 653, 632, 755, 433, 722, 48 };

		int[] res = radixSort(ary, 3);

		for (int vr : res) {
			System.out.format("%d ", vr);
		}

	}
}
