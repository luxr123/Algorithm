package datastruct;

/**
 * Java : 数组实现的栈，能存储任意类型的数据
 *
 * @author xiaorui.lu
 */
import java.lang.reflect.Array;

public class ArrayStack<T extends Comparable<T>> {

	private static final int DEFAULT_SIZE = 12;
	private static final Class DEFAULT_TYPE = Integer.class;
	private T[] mArray;
	private int count;

	public ArrayStack() {
		this(DEFAULT_TYPE, DEFAULT_SIZE);
	}

	public ArrayStack(Class<T> type) {
		this(type, DEFAULT_SIZE);
	}

	public ArrayStack(Class<T> type, int size) {
		// 不能直接使用mArray = new T[DEFAULT_SIZE];
		mArray = (T[]) Array.newInstance(type, size);
		count = 0;
	}

	// 将val添加到栈中
	public void push(T val) {
		mArray[count++] = val;
	}

	// 返回“栈顶元素值”
	public T peek() {
		return mArray[count - 1];
	}

	// 返回“栈顶元素值”，并删除“栈顶元素”
	public T pop() {
		return removeElementAt(count - 1);
	}

	public T removeBottom() {
		return removeElementAt(0);
	}

	public synchronized T removeElementAt(int index) {
		if (index >= count) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " + count);
		} else if (index < 0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int i = count - index - 1;
		T ret = mArray[index];
		if (i > 0) {
			System.arraycopy(mArray, index + 1, mArray, index, i);
		}
		count--;
		mArray[count] = null; /* to let gc do its work */
		return ret;
	}

	// 返回“栈”的大小
	public int size() {
		return count;
	}

	// 返回“栈”是否为空
	public boolean isEmpty() {
		return size() == 0;
	}

	// 打印“栈”
	public void PrintArrayStack() {
		if (isEmpty()) {
			System.out.printf("stack is Empty\n");
		}

		System.out.printf("stack size()=%d\n", size());

		int i = size() - 1;
		while (i >= 0) {
			System.out.println(mArray[i]);
			i--;
		}
	}

	/**
	 * 按升序排序
	 * @param srcStack
	 * @return
	 */
	public ArrayStack<T> sort(ArrayStack<T> srcStack) {
		ArrayStack<T> sortStack = new ArrayStack<T>();
		while (!srcStack.isEmpty()) {
			T tmp = srcStack.pop();
			while (!sortStack.isEmpty() && sortStack.peek().compareTo(tmp) > 0) {
				T tmp2 = sortStack.pop();
				srcStack.push(tmp2);
			}
			sortStack.push(tmp);
		}
		return sortStack;
	}

	public static void main(String[] args) {
		String tmp;
		ArrayStack<Integer> astack = new ArrayStack<Integer>(Integer.class);

		// 将10, 20, 30 依次推入栈中
		// astack.push(10);
		// astack.push(20);
		// astack.push(30);

		// System.out.println("removeBottom:" + astack.removeBottom());
		//
		// System.out.println("tmp=" + astack.mArray[0]);
		// System.out.println("tmp=" + astack.mArray[1]);
		// System.out.println("tmp=" + astack.mArray[2]);
		// // 将“栈顶元素”赋值给tmp，并删除“栈顶元素”
		// tmp = astack.pop();
		// System.out.println("tmp=" + tmp);
		// System.out.println("tmp=" + astack.mArray[1]);
		//
		// // 只将“栈顶”赋值给tmp，不删除该元素.
		// tmp = astack.peek();
		// System.out.println("tmp=" + tmp);
		//
		// astack.push("40");
		// astack.PrintArrayStack(); // 打印栈

		astack.push(3);
		astack.push(2);
		astack.push(4);
		astack.push(3);

		ArrayStack<Integer> stack = astack.sort(astack);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());

	}

}