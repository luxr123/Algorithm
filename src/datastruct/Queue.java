package datastruct;

/**
 * 使用两个栈实现一个队列
 */
public class Queue<T extends Comparable<T>> {

	ArrayStack<T> s1, s2;

	public Queue() {
		super();
		this.s1 = new ArrayStack<T>();
		this.s2 = new ArrayStack<T>();
	}

	public int size() {
		return s1.size() + s2.size();
	}

	public void add(T value) {
		s1.push(value);
	}

	public T peek() {
		if (!s2.isEmpty())
			return s2.peek();
		while (!s1.isEmpty())
			s2.push(s1.pop());
		return s2.peek();
	}

	public T remove() {
		if (!s2.isEmpty())
			return s2.pop();
		while (!s1.isEmpty())
			s2.push(s1.pop());
		return s2.pop();
	}
}
