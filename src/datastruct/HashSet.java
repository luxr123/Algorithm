package datastruct;

public class HashSet<E> {
	private transient HashMap<E, Object> map;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	/**
	 * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
	 * default initial capacity (16) and load factor (0.75).
	 */
	public HashSet() {
		map = new HashMap<E, Object>();
	}

	public HashSet(int initialCapacity, float loadFactor) {
		map = new HashMap<E, Object>(initialCapacity, loadFactor);
	}

	public HashSet(int initialCapacity) {
		map = new HashMap<E, Object>(initialCapacity, 0.75f);
	}

	// 调用 HashMap 的 size() 方法返回 Entry 的数量，就得到该 Set 里元素的个数
	public int size() {
		return map.size();
	}

	// 调用 HashMap 的 isEmpty() 判断该 HashSet 是否为空，
	// 当 HashMap 为空时，对应的 HashSet 也为空
	public boolean isEmpty() {
		return map.isEmpty();
	}

	// 将指定元素放入 HashSet 中，也就是将该元素作为 key 放入 HashMap
	public boolean add(E e) {
		return map.put(e, PRESENT) == null;
	}
}
