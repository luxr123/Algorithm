package datastruct;

public class HashMap<K, V> {
	static final int DEFAULT_INITIAL_CAPACITY = 16;
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	transient Entry<K, V>[] table;
	transient int size;
	int threshold;
	final float loadFactor;

	// The number of times this HashMap has been structurally modified
	transient volatile int modCount;

	// 以指定初始化容量、负载因子创建 HashMap
	@SuppressWarnings("unchecked")
	public HashMap(int initialCapacity, float loadFactor) {
		// 初始容量不能为负数
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		// 如果初始容量大于最大容量，让出示容量
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		// 负载因子必须大于 0 的数值
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		// 计算出大于 initialCapacity 的最小的 2 的 n 次方值。
		int capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;
		this.loadFactor = loadFactor;
		// 设置容量极限等于容量 * 负载因子
		threshold = (int) (capacity * loadFactor);
		// 初始化 table 数组
		table = new Entry[capacity]; //
		init();
	}

	void init() {
	}

	@SuppressWarnings("unchecked")
	public HashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		table = new Entry[DEFAULT_INITIAL_CAPACITY];
		init();
	}

	public V put(K key, V value) {
		// 如果 key 为 null，调用 putForNullKey 方法进行处理
		if (key == null)
			return putForNullKey(value);
		// 根据 key 的 keyCode 计算 Hash 值
		int hash = hash(key.hashCode());
		// 搜索指定 hash 值在对应 table 中的索引
		int i = indexFor(hash, table.length);
		// 如果 i 索引处的 Entry 不为 null，通过循环不断遍历 e 元素的下一个元素
		for (Entry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			// 找到指定 key 与需要放入的 key 相等（hash 值相同
			// 通过 equals 比较放回 true）
			if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}
		// 如果 i 索引处的 Entry 为 null，表明此处还没有 Entry
		modCount++;
		// 将 key、value 添加到 i 索引处
		addEntry(hash, key, value, i);
		return null;
	}

	protected V putForNullKey(V value) {
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}
		modCount++;
		addEntry(0, null, value, 0);
		return null;
	}

	void addEntry(int hash, K key, V value, int bucketIndex) {
		// 获取指定 bucketIndex 索引处的 Entry
		Entry<K, V> e = table[bucketIndex];
		// 将新创建的 Entry 放入 bucketIndex 索引处，并让新的 Entry 指向原来的 Entry
		table[bucketIndex] = new Entry<K, V>(hash, key, value, e);
		// 如果 Map 中的 key-value 对的数量超过了极限
		if (size++ >= threshold)
			// 把 table 对象的长度扩充到 2 倍。
			resize(2 * table.length);
	}

	public V get(Object key) {
		// 如果 key 是 null，调用 getForNullKey 取出对应的 value
		if (key == null)
			return getForNullKey();
		// 根据该 key 的 hashCode 值计算它的 hash 码
		int hash = hash(key.hashCode());
		// 直接取出 table 数组中指定索引处的值，
		for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
			Object k;
			// 如果该 Entry 的 key 与被搜索 key 相同
			if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
				return e.value;
		}
		return null;
	}

	private V getForNullKey() {
		for (Entry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null)
				return e.value;
		}
		return null;
	}

	final Entry<K, V> getEntry(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
				return e;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	void resize(int newCapacity) {
		Entry[] oldTable = table;
		int oldCapacity = oldTable.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		Entry[] newTable = new Entry[newCapacity];
		transfer(newTable);
		table = newTable;
		threshold = (int) (newCapacity * loadFactor);
	}

	/**
	 * Transfers all entries from current table to newTable.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void transfer(Entry[] newTable) {
		Entry[] src = table;
		int newCapacity = newTable.length;
		for (int j = 0; j < src.length; j++) {
			Entry<K, V> e = src[j];
			if (e != null) {
				src[j] = null;// 个人觉得应该是防止内存泄露
				do {
					Entry<K, V> next = e.next;
					int i = indexFor(e.hash, newCapacity);
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
				} while (e != null);
			}
		}
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static class Entry<K, V> {
		final K key;
		V value;
		Entry<K, V> next;
		final int hash;

		Entry(int h, K k, V v, Entry<K, V> n) {
			value = v;
			next = n;
			key = k;
			hash = h;
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		void recordAccess(HashMap<K, V> m) {
		}
	}

	public static void main(String[] args) {
		HashMap<String, String> mm = new HashMap<String, String>();
		Long aBeginTime = System.currentTimeMillis();// 记录BeginTime
		for (int i = 0; i < 100000; i++) {
			mm.put("" + i, "" + i * 100);
		}
		Long aEndTime = System.currentTimeMillis();// 记录EndTime
		System.out.println("insert time-->" + (aEndTime - aBeginTime));

		Long lBeginTime = System.currentTimeMillis();// 记录BeginTime
		mm.get("" + 100000);
		Long lEndTime = System.currentTimeMillis();// 记录EndTime
		System.out.println("seach time--->" + (lEndTime - lBeginTime));

		mm.put(null, "xx");
		System.out.println(mm.get(null));
	}
}
