package recursion;

/**
 * 题目：序列123...N，N介于3和9之间，在其中加入+、-或者空格，使其和为0。如123456 1-2 3-4 5+6 7
 * 等价于1-23-45+67=0。请问，如何获得所有组合？
 * 
 */
public class CombineZero {
	/*
	 * 思路： 1.由于N是从3至9的范围，解决方案就是针对每个N的取值，先计算三个分隔符“_ + -”填入序列1-N的所有可能组合的情况总数
	 * 2.设置所有情况的组合序列（采用3进制的思想，即set函数） 3.对每种组合计算表达式值，如果为0则输出（利用栈&状态机，calculate函数）
	 */
	static final int BASE = 3;

	/*
	 * Calculate函数就是计算每一种set后的表达式值，采用了简单的自动机思想和简单的栈（数组）。值得说明的是，
	 * calculate程序程序的功能是计算中缀表达式的值
	 * ，因此需要使用栈来保存操作数的值。由于题目中操作符只有加和减，两者是同一优先级的，因此可以直接简化为一个从左向右的扫描过程
	 * ，同时栈只需要保存两个操作数的值即可
	 * （所以stack数组的长度才是3，其中一个用于保存操作符）。如果运算符不止一个优先级，那么栈就必须是变长了，而且需要一个独立的操作符栈
	 * ，同时执行过程就会是中缀转后缀的算法了。因此这个函数是针对题目的特定优化和简化
	 */
	/*
	 * Initialize the string with 1 to 9 and seperated by whitespace for len in
	 * range(3, 9+1): for each possibility of seperators: calculate the result,
	 * if it's zero, output
	 */
	public int calculate(char input[], int n) {
		// Use a simple state machine to interpret the input as an expression
		int[] stack = new int[3];
		int sp = 0;
		for (int i = 0; i < n * 2 - 1; i++) {
			switch (input[i]) {
			case ' ':
				stack[sp] = stack[sp] * 10;
				break;
			case '+':
			case '-':
				if (sp == 0) {
					stack[1] = input[i];
					sp = 2;
				} else {
					stack[0] = stack[0] + (stack[1] == '-' ? -1 : 1) * stack[2];
					stack[1] = input[i];
					stack[2] = 0;
				}
				break;
			default: // digits
				stack[sp] += input[i] - '0';
				break;
			}
		}
		if (sp == 2) {
			stack[0] = stack[0] + (stack[1] == '-' ? -1 : 1) * stack[2];
		}
		return stack[0];
	}

	/*
	 * Set函数的本质是将每一种分隔符的排列情况映射为一个整数（即1-all之间的数字），由于这些数字是连续的，而连续的整数转换为某个进制上的表示是不会有重复的
	 * ，故遍历这个区间，就可以通过进制转换表示出所有的可能，有多少个数被转换，就能输出多少种可能。
	 */
	public void set(char str[], int n, int num) {
		// Transform num into base3 to generate the presentation of seperators
		int sp;
		char choice[] = new char[] { ' ', '+', '-' };
		for (sp = (n - 1) * 2 - 1; sp > 0; sp -= 2) {
			str[sp] = choice[num % BASE];
			num /= BASE;
		}
	}

	public void result0() {
		char[] str = new char[] { '1', ' ', '2', ' ', '3', ' ', '4', ' ', '5', ' ', '6', ' ', '7', ' ', '8', ' ', '9', ' ' };
		int n, sp, all;
		// 对于每个N，先计算出所有可能组合情况的数目，然后根据所有的情况数目all，将[1,all]的值传给函数set，
		// 并根据每个数值的三进制（分隔符只有三种情况，如果是四种分隔符，那就是四进制了）表示为str赋值。
		for (n = 3; n <= 9; n++) {// 3-9
			all = 1;
			for (sp = 1; sp < n; sp++) { // Count the num of possibilities on
											// 1-n
				all *= BASE;
			}
			while (all > 0) {
				set(str, n, --all);
				if (calculate(str, n) == 0) {
					str[n * 2 - 1] = '\0';
					for (int i = 0; i < n * 2 - 1; i++)
						System.out.format("%s", str[i]);
					System.out.print("=0");
					System.out.println();
				}
			}
		}
	}

	public static void main(String[] args) {
		CombineZero combineZero = new CombineZero();
		combineZero.result0();
	}
}
