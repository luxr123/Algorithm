package datastruct;

public class StackWithMin extends ArrayStack<Integer> {
	ArrayStack<Integer> minStack;

	public StackWithMin(Class<Integer> type) {
		super(type);
		this.minStack = new ArrayStack<Integer>(type);
	}

	public void push(Integer value) {
		super.push(value);
		if (value <= min()) {
			minStack.push(value);
		}
	}

	public Integer pop() {
		int value = super.pop();
		if (value == min()){
			minStack.pop();
		}
		return value;
	}

	public int min() {
		if (minStack.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return minStack.peek();
		}
	}

	public static void main(String[] args) {
		StackWithMin stackWithMin = new StackWithMin(Integer.class);
		stackWithMin.push(102);
		stackWithMin.push(16);
		stackWithMin.push(100);
		stackWithMin.push(2);
		stackWithMin.push(99);
		
		stackWithMin.pop();
		stackWithMin.pop();
		stackWithMin.pop();

		System.out.println(stackWithMin.minStack.peek());
	}
}
