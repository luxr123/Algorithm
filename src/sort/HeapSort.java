package sort;
public class HeapSort {

	/**
	 * 最大堆排序算法，将source按最大堆排序算法由小到大排序 --堆顶为1
	 * 
	 * @param source
	 */
	public void heapSort(int[] source) {
		if (source == null || source.length <= 1)
			return;
		int len = source.length;
		bulidMaxHeap(source);
		for (int lastElement = len - 1; lastElement > 0; lastElement--) {
			swap(source, 0, lastElement);
			maxHeapily(source, 1, lastElement);
		}
	}
	
	/*public void k_min(int[] arr,int len,int k)
	{
		int i;
		build_maxheap(arr,k);
		for (i = k;i < len;i++)
		{
			if (arr[i] < arr[0])
			{
				arr[0] = arr[i];
				max_heapify(arr,1,k);
			}
		}
	}*/

	/**
	 * 构建最大堆树
	 * 
	 * @param source
	 */
	private void bulidMaxHeap(int[] source) {
		int hSize = source.length;
		// beginFlag以后的都是叶子节点。
		int beginFlag = (int) Math.floor(hSize / 2);
		for (int i = beginFlag; i >= 1; i--) {
			maxHeapily(source, i, hSize);
		}
	}

	/**
	 * 以第i个元素为根的子树保持最大树,终点为第heapSize个元素。source中第heapSize个元素之后的元素不予理会。
	 * 即堆的大小为heapSize，从数组第一个元素到第heapSize个元素为止。
	 * 
	 * @param source
	 * @param i
	 *            第i个元素，指的是从1开始的。因此后边在取数组元素的时候，都有  减一操作 (-1).
	 * @param heapSize
	 */
	private void maxHeapily(int[] source, int i, int heapSize) {
		int left = Left(i);
		int right = Right(i);
		int largest = i;
		if (heapSize >= left && source[left - 1] > source[i - 1]) {
			largest = left;
		}
		if (heapSize >= right && source[right - 1] > source[largest - 1]) {
			largest = right;
		}
		if (largest != i) {//将最大元素提升，并递归
			swap(source, i - 1, largest - 1);
			maxHeapily(source, largest, heapSize);
		}
	}

	/**
	 * 根据序号获得左儿子的序号
	 */
	public int Left(int i) {
		return 2 * i;
	}

	/**
	 * 根据序号获得右儿子的序号
	 */
	public int Right(int i) {
		return 2 * i + 1;
	}

	public void swap(int array[], int i, int j) {
		array[i] = array[i] + array[j];
		array[j] = array[i] - array[j];
		array[i] = array[i] - array[j];
	}

	public static void main(String[] args) {
		HeapSort heapSort = new HeapSort();

		int len = 10;
		int[] arr = new int[len];
		for (int i = 1; i < len; i++) {
			arr[i] = (int) (Math.random() * 100 % 100);
		}

		heapSort.heapSort(arr);
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + ",");

	}
}
