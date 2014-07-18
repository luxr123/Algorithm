package tree;

public class Heap<E extends Comparable<E>> {

	private Object items[];
	private int last;
	private int capacity;

	public Heap() {
		items = new Object[11];
		last = 0;
		capacity = 7;
	}

	public Heap(int cap) {
		items = new Object[cap + 1];
		last = 0;
		capacity = cap;
	}

	public int size() {
		return last;
	}

	//
	// returns the number of elements in the heap
	//

	public boolean isEmpty() {
		return size() == 0;
	}

	//
	// is the heap empty?
	//

	public E min() throws HeapException {
		if (isEmpty())
			throw new HeapException("The heap is empty.");
		else
			return (E) items[1];
	}

	//
	// returns element with smallest key, without removal
	//

	private int compare(Object x, Object y) {
		return ((E) x).compareTo((E) y);
	}

	public void insert(E e) throws HeapException {
		if (size() == capacity)
			throw new HeapException("Heap overflow.");
		else {
			last++;
			items[last] = e;
			upHeapBubble();
		}
	}

	// inserts e into the heap
	// throws exception if heap overflow
	//

	public E removeMin() throws HeapException {
		if (isEmpty())
			throw new HeapException("Heap is empty.");
		else {
			E min = min();
			items[1] = items[last];
			last--;
			downHeapBubble();
			return min;
		}
	}

	//
	// removes and returns smallest element of the heap
	// throws exception is heap is empty
	//

	/**
	 * downHeapBubble() method is used after the removeMin() method to reorder
	 * the elements in order to preserve the Heap properties
	 */
	private void downHeapBubble() {
		int index = 1;
		while (true) {
			int child = index * 2;
			if (child > size())
				break;
			if (child + 1 <= size()) {
				// if there are two children -> take the smalles or
				// if they are equal take the left one
				child = findMin(child, child + 1);
			}
			if (compare(items[index], items[child]) <= 0)
				break;
			swap(index, child);
			index = child;
		}
	}

	/**
	 * upHeapBubble() method is used after the insert(E e) method to reorder the
	 * elements in order to preserve the Heap properties
	 */
	private void upHeapBubble() {
		int index = size();
		while (index > 1) {
			int parent = index / 2;
			if (compare(items[index], items[parent]) >= 0)
				// break if the parent is greater or equal to the current
				// element
				break;
			swap(index, parent);
			index = parent;
		}
	}

	/**
	 * Swaps two integers i and j
	 * 
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		Object temp = items[i];
		items[i] = items[j];
		items[j] = temp;
	}

	/**
	 * the method is used in the downHeapBubble() method
	 * 
	 * @param leftChild
	 * @param rightChild
	 * @return min of left and right child, if they are equal return the left
	 */
	private int findMin(int leftChild, int rightChild) {
		if (compare(items[leftChild], items[rightChild]) <= 0)
			return leftChild;
		else
			return rightChild;
	}

	public String toString() {
		String s = "[";
		for (int i = 1; i <= size(); i++) {
			s += items[i];
			if (i != last)
				s += ",";
		}
		return s + "]";
	}
	//
	// outputs the entries in S in the order S[1] to S[last]
	// in same style as used in ArrayQueue
	//

}

class HeapException extends RuntimeException {
	public HeapException() {
	};

	public HeapException(String msg) {
		super(msg);
	}
}
