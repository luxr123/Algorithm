package datastruct;

public class StackWithMin extends ArrayStack<Integer> {
	ArrayStack<Integer> stack;

	public StackWithMin(Class<Integer> type) {
		super(type);
		this.stack = new ArrayStack<Integer>(type);
	}

	public void push(Integer value) {
		super.push(value);
		if (value <= min()) {
			stack.push(value);
		}
	}

	public Integer pop() {
		int value = super.pop();
		if (value == min()) {
			stack.pop();
		}
		return value;
	}

	public int min() {
		if (stack.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return stack.peek();
		}
	}

	public static void main(String[] args) {
		StackWithMin stackWithMin = new StackWithMin(Integer.class);
		stackWithMin.push(100);
		stackWithMin.push(102);
		stackWithMin.push(16);
		stackWithMin.push(99);
		
		stackWithMin.pop();
		stackWithMin.pop();
		stackWithMin.push(2);
		stackWithMin.pop();
		
		System.out.println(stackWithMin.stack.peek());
	}
}
