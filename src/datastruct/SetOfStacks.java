package datastruct;

import java.util.ArrayList;

public class SetOfStacks {
	ArrayList<ArrayStack<Integer>> stacks = new ArrayList<ArrayStack<Integer>>();
	public int capacity;

	public SetOfStacks(int capacity) {
		this.capacity = capacity;
	}

	public ArrayStack<Integer> getLastStack() {
		if (stacks.size() == 0)
			return null;
		return stacks.get(stacks.size() - 1);
	}

	public void push(int v) { /* see earlier code */
		ArrayStack<Integer> last = getLastStack();
		if (last == null || last.size() == this.capacity) {
			last = new ArrayStack<Integer>(Integer.class);
			stacks.add(last);
			System.out.println("stacks.size:" + stacks.size());
		}
		last.push(v);
	}

	public int pop() {
		ArrayStack<Integer> last = getLastStack();
		int v = last.pop();
		if (last.size() == 0)
			stacks.remove(stacks.size() - 1);
		System.out.println("stacks.size:" + stacks.size());
		return v;
	}

	public static void main(String[] args) {
		SetOfStacks stacks = new SetOfStacks(5);
		stacks.push(10);
		stacks.push(11);
		stacks.push(12);
		stacks.push(13);
		stacks.push(14);
		stacks.push(15);
		stacks.push(19);

		System.out.println(stacks.stacks.get(0).peek());
		System.out.println(stacks.popAt(1));
		System.out.println(stacks.stacks.get(0).peek());
	}

	public int popAt(int index) {
		if (index > stacks.size() - 1)
			throw new IndexOutOfBoundsException("越界了");
		return leftShift(index, true);
	}

	public int leftShift(int index, boolean removeTop) {
		ArrayStack<Integer> stack = stacks.get(index);
		Integer removedItem = null;
		if (removeTop)
			removedItem = stack.pop();
		else
			removedItem = stack.removeBottom();
		if (stack.isEmpty()) {
			stacks.remove(index);
		} else if (stacks.size() > index + 1) {
			int v = leftShift(index + 1, false);
			stack.push(v);
		}
		return removedItem;
	}
}
