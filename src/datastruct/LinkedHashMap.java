package datastruct;

public class LinkedHashMap<K, V> extends HashMap<K, V> {

	// 双向循环链表的头结点，整个LinkedHa只哟shMap中只有一个header，
	// 它将哈希表中所有的Entry贯穿起来，header中不保存key-value对，只保存前后节点的引用
	private transient Entry<K, V> header;

	// 双向链表中元素排序规则的标志位。
	// accessOrder为false，表示按插入顺序排序
	// accessOrder为true，表示按访问顺序排序
	private final boolean accessOrder;

	// 调用HashMap的构造方法来构造底层的数组
	public LinkedHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		accessOrder = false; // 链表中的元素默认按照插入顺序排序
	}

	// 加载因子取默认的0.75f
	public LinkedHashMap(int initialCapacity) {
		super(initialCapacity, 0.75f);
		accessOrder = false;
	}

	// 加载因子取默认的0.75f，容量取默认的16
	public LinkedHashMap() {
		super();
		accessOrder = false;
	}

	// 该构造方法可以指定链表中的元素排序的规则
	public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor);
		this.accessOrder = accessOrder;
	}

	// 覆写父类的init()方法（HashMap中的init方法为空），
	// 该方法在父类的构造方法和Clone、readObject中在插入元素前被调用，
	// 初始化一个空的双向循环链表，头结点中不保存数据，头结点的下一个节点才开始保存数据。
	@Override
	void init() {
		header = new Entry<K, V>(-1, null, null, null);
		header.before = header.after = header;
	}

	// 覆写HashMap中的transfer方法，它在父类的resize方法中被调用，
	// 扩容后，将key-value对重新映射到新的newTable中
	// 覆写该方法的目的是为了提高复制的效率，
	// 这里充分利用双向循环链表的特点进行迭代，不用对底层的数组进行for循环。
	void transfer(HashMap.Entry[] newTable) {
		int newCapacity = newTable.length;
		for (Entry<K, V> e = header.after; e != header; e = e.after) {
			int index = indexFor(e.hash, newCapacity);
			e.next = newTable[index];
			newTable[index] = e;
		}
	}

	// 覆写HashMap中的get方法，通过getEntry方法获取Entry对象。
	// 注意这里的recordAccess方法，
	// 如果链表中元素的排序规则是按照插入的先后顺序排序的话，该方法什么也不做，
	// 如果链表中元素的排序规则是按照访问的先后顺序排序的话，则将e移到链表的末尾处。
	public V get(Object key) {
		Entry<K, V> e = (Entry<K, V>) getEntry(key);
		if (e == null)
			return null;
		e.recordAccess(this);
		return e.value;
	}

	// 覆写HashMap中的addEntry方法，LinkedHashmap并没有覆写HashMap中的put方法，
	// 而是覆写了put方法所调用的addEntry方法和recordAccess方法，
	// put方法在插入的key已存在的情况下，会调用recordAccess方法，
	// 在插入的key不存在的情况下，要调用addEntry插入新的Entry
	void addEntry(int hash, K key, V value, int bucketIndex) {
		// 创建新的Entry，并插入到LinkedHashMap中
		createEntry(hash, key, value, bucketIndex);

		// 双向链表的第一个有效节点（header后的那个节点）为近期最少使用的节点
		Entry<K, V> eldest = header.after;
		// 如果有必要，则删除掉该近期最少使用的节点，
		// 这要看对removeEldestEntry的覆写,由于默认为false，因此默认是不做任何处理的。
		if (removeEldestEntry(eldest)) {
			// removeEntryForKey(eldest.key);
		} else {
			// 扩容到原来的2倍
			if (size >= threshold)
				resize(2 * table.length);
		}
	}

	void createEntry(int hash, K key, V value, int bucketIndex) {
		// 创建新的Entry，并将其插入到数组对应槽的单链表的头结点处，这点与HashMap中相同
		HashMap.Entry<K, V> old = table[bucketIndex];
		Entry<K, V> e = new Entry<K, V>(hash, key, value, old);
		table[bucketIndex] = e;
		// 每次插入Entry时，都将其移到双向链表的尾部，
		// 这便会按照Entry插入LinkedHashMap的先后顺序来迭代元素，
		// 同时，新put进来的Entry是最近访问的Entry，把其放在链表末尾 ，符合LRU算法的实现
		e.addBefore(header);
		size++;
	}

	// 该方法是用来被覆写的，一般如果用LinkedHashmap实现LRU算法，就要覆写该方法，
	// 比如可以将该方法覆写为如果设定的内存已满，则返回true，这样当再次向LinkedHashMap中put
	// Entry时，在调用的addEntry方法中便会将近期最少使用的节点删除掉（header后的那个节点）。
	protected boolean removeEldestEntry(HashMap.Entry<K, V> eldest) {
		return false;
	}

	// Enty的数据结构，多了两个指向前后节点的引用
	private static class Entry<K, V> extends HashMap.Entry<K, V> {
		// These fields comprise the doubly linked list used for iteration.
		Entry<K, V> before, after;

		// 调用父类的构造方法
		Entry(int hash, K key, V value, HashMap.Entry<K, V> next) {
			super(hash, key, value, next);
		}

		// 双向循环链表中，删除当前的Entry
		private void remove() {
			before.after = after;
			after.before = before;
		}

		// 双向循环立链表中，将当前的Entry插入到existingEntry的前面
		private void addBefore(Entry<K, V> existingEntry) {
			after = existingEntry;
			before = existingEntry.before;
			before.after = this;
			after.before = this;
		}

		// 覆写HashMap中的recordAccess方法（HashMap中该方法为空），
		// 当调用父类的put方法，在发现插入的key已经存在时，会调用该方法，
		// 调用LinkedHashmap覆写的get方法时，也会调用到该方法，
		// 该方法提供了LRU算法的实现，它将最近使用的Entry放到双向循环链表的尾部，
		// accessOrder为true时，get方法会调用recordAccess方法
		// put方法在覆盖key-value对时也会调用recordAccess方法
		// 它们导致Entry最近使用，因此将其移到双向链表的末尾
		void recordAccess(HashMap<K, V> m) {
			LinkedHashMap<K, V> lm = (LinkedHashMap<K, V>) m;
			// 如果链表中元素按照访问顺序排序，则将当前访问的Entry移到双向循环链表的尾部，
			// 如果是按照插入的先后顺序排序，则不做任何事情。
			if (lm.accessOrder) {
				lm.modCount++;
				// 移除当前访问的Entry
				remove();
				// 将当前访问的Entry插入到链表的尾部
				addBefore(lm.header);
			}
		}
	}

	public static void main(String[] args) {
		LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<String, Integer>();
		hashMap.put(null, 1);
		// System.out.println(hashMap.get(null));
	}
}