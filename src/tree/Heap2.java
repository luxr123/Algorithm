package tree;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Heap2<T extends Comparable<T>> {
	ArrayList<T> items;
	int cursor; // 用于计数

	public Heap2(int size) {
		items = new ArrayList<T>(size);
		cursor = -1;
	}

	public Heap2() {
		items = new ArrayList<T>();
		cursor = -1;
	}

	/**
	 * 上移操作
	 * 
	 * @param index
	 *            被上移元素的起始位置。
	 */
	void siftUp(int index) {
		T intent = items.get(index);
		while (index > 0) {
			int pindex = (index - 1) / 2;
			T parent = items.get(pindex);
			if (intent.compareTo(parent) > 0) {// 上移的条件，比父节点大
				items.set(index, parent);
				index = pindex;
			} else
				break;
		}
		items.set(index, intent);
	}

	/**
	 * 下移操作
	 * 
	 * @param index
	 *            被下移的元素的起始位置
	 */
	void siftDown(int index) {
		T intent = items.get(index);
		int l_index = 2 * index + 1;
		while (l_index < items.size()) {
			T maxChild = items.get(l_index);
			int max_index = l_index;

			int r_index = l_index + 1;
			if (r_index < items.size()) {
				T rightChild = items.get(r_index);
				if (rightChild.compareTo(maxChild) > 0) {
					maxChild = rightChild;
					max_index = r_index;
				}
			}

			if (maxChild.compareTo(intent) > 0) {
				items.set(index, maxChild);
				index = max_index;
				l_index = index * 2 + 1;
			} else
				break;

		}
		items.set(index, intent);
	}

	public void add(T item) {
		// 先添加到最后
		items.add(item);
		// 循环上移，以完成重构
		siftUp(items.size() - 1);
	}

	public T deleteTop() {
		if (items.isEmpty()) {
			throw new NoSuchElementException();
		}
		// 先获取顶部节点
		T maxItem = items.get(0);
		T lastItem = items.remove(items.size() - 1);
		if (items.isEmpty()) {
			return lastItem;
		}
		// 将尾部的节点放置顶部，下移，完成重构
		items.set(0, lastItem);
		siftDown(0);
		return maxItem;
	}

	public T next() {
		cursor++;
		if (cursor < 0 || cursor >= (items.size())) {
			return null;
		}
		return items.get(cursor);
	}

	public T first() {
		if (items.size() == 0)
			return null;
		cursor = 0;
		return items.get(0);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public int size() {
		return items.size();
	}

	public void clear() {
		items.clear();
	}
	
	public static void main(String[] args) {
		Heap2<Integer> heap2  = new Heap2<Integer>(10);
		heap2.add(2);
		heap2.add(4);
		heap2.add(8);
		heap2.add(5);
		
		System.out.println(heap2.next());
	}
}
