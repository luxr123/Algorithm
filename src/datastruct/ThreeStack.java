package datastruct;

/**
 * 方法一：
 * 
 */
public class ThreeStack {
	int stackSize = 300;
	int[] buffer = new int[stackSize * 3];
	int[] stackPointer = { 0, 0, 0 }; // stack pointers to track top elem

	void push(int stackNum, int value) {
		/*
		 * Find the index of the top element in the array + 1, and increment the
		 * stack pointer
		 */
		int index = stackNum * stackSize + stackPointer[stackNum] + 1;
		stackPointer[stackNum]++;
		buffer[index] = value;
	}

	int pop(int stackNum) {
		int index = stackNum * stackSize + stackPointer[stackNum];
		stackPointer[stackNum]--;
		int value = buffer[index];
		buffer[index] = 0;
		return value;
	}

	int peek(int stackNum) {
		int index = stackNum * stackSize + stackPointer[stackNum];
		return buffer[index];
	}

	boolean isEmpty(int stackNum) {
		return stackPointer[stackNum] == stackNum * stackSize;
	}
}

/**
 * 方法二：
 * 
 */
class ThreeStack2 {
	int stackSize = 300;
	int indexUsed = 0;
	int[] stackPointer = { -1, -1, -1 };
	StackNode[] buffer = new StackNode[stackSize * 3];

	void push(int stackNum, int value) {
		int lastIndex = stackPointer[stackNum];
		stackPointer[stackNum] = indexUsed;
		indexUsed++;
		buffer[stackPointer[stackNum]] = new StackNode(lastIndex, value);
	}

	int pop(int stackNum) {
		int value = buffer[stackPointer[stackNum]].value;
		int lastIndex = stackPointer[stackNum];
		stackPointer[stackNum] = buffer[stackPointer[stackNum]].previous;
		buffer[lastIndex] = null;
		indexUsed--;
		return value;
	}

	int peek(int stack) {
		return buffer[stackPointer[stack]].value;
	}

	boolean isEmpty(int stackNum) {
		return stackPointer[stackNum] == -1;
	}

	class StackNode {
		public int previous;
		public int value;

		public StackNode(int p, int v) {
			value = v;
			previous = p;
		}
	}
}
