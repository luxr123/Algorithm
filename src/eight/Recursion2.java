package eight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Recursion2 {

	/**
	 * 写一个函数返回一个集合中的所有子集;
	 * 
	 * <pre>
	 * 对于一个集合，它的子集一共有2n 个(包括空集和它本身)。
	 * </pre>
	 */

	// 解法一: 组合法
	// 它的任何一个子集， 我们都可以理解为这个集合本身的每个元素是否出现而形成的一个序列。比如说， 对于集合{1, 2,
	// 3}，空集表示一个元素都没出现，对应{0, 0, 0}； 子集{1, 3}，表示元素2没出现(用0表示)，1，3出现了(用1表示)，所以它对应
	// {1, 0, 1}。这样一来，我们发现，{1, 2, 3}的所有子集可以用二进制数000到111
	// 的8个数来指示。泛化一下，如果一个集合有n个元素，那么它可以用0到2n -1 总共2n
	// 个数的二进制形式来指示。每次我们只需要检查某个二进制数的哪一位为1， 就把对应的元素加入到这个子集就OK
	public static Set<Set<Integer>> getSubsets(int a[]) { // O(n2^n)
		Set<Set<Integer>> subsets = new HashSet<Set<Integer>>();
		int max = 1 << a.length;
		for (int i = 0; i < max; i++) {
			Set<Integer> set = new HashSet<Integer>();
			int idx = 0, j = i;
			while (j > 0) {
				if ((j & 1) == 1) {
					set.add(a[idx]);
				}
				idx++;
				j >>= 1;
			}
			subsets.add(set);
		}

		return subsets;
	}

	public static ArrayList<ArrayList<Integer>> getSubsets(ArrayList<Integer> set) {
		ArrayList<ArrayList<Integer>> allsubsets = new ArrayList<ArrayList<Integer>>();
		int max = 1 << set.size();
		for (int i = 0; i < max; i++) {
			ArrayList<Integer> subset = new ArrayList<Integer>();
			int k = i;
			int index = 0;
			while (k > 0) {
				if ((k & 1) > 0) {
					subset.add(set.get(index));
				}
				k >>= 1;
				index++;
			}
			allsubsets.add(subset);
		}
		return allsubsets;
	}

	// 解法二: 递归法
	// 因为我们能找到比原问题规模小却同质的问题。比如我要求{1, 2, 3}的所有子集， 我把元素1拿出来，然后去求{2, 3}的所有子集，{2,
	// 3}的子集同时也是{1, 2, 3} 的子集，然后我们把{2, 3}的所有子集都加上元素1后，又得到同样数量的子集， 它们也是{1, 2,
	// 3}的子集。这样一来，我们就可以通过求{2, 3}的所有子集来求 {1, 2, 3}的所有子集了。而同理，{2, 3}也可以如法炮制
	public static ArrayList<ArrayList<Integer>> getSubsets1(ArrayList<Integer> set, int index) {
		ArrayList<ArrayList<Integer>> allsubsets;
		if (set.size() == index) {
			allsubsets = new ArrayList<ArrayList<Integer>>();
			allsubsets.add(new ArrayList<Integer>()); // Empty set
		} else {
			allsubsets = getSubsets1(set, index + 1);
			int item = set.get(index);
			ArrayList<ArrayList<Integer>> moresubsets = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> subset : allsubsets) {
				ArrayList<Integer> newsubset = new ArrayList<Integer>();
				newsubset.addAll(subset); //
				newsubset.add(item);
				moresubsets.add(newsubset);
			}
			allsubsets.addAll(moresubsets);
		}
		return allsubsets;
	}

	/**
	 * 写一个函数返回一个串的所有排列
	 * 
	 */
	// 我们可以把串“abc”中的第0个字符a取出来，然后递归调用permu计算剩余的串“bc” 的排列，得到{bc,
	// cb}。然后再将字符a插入这两个串中的任何一个空位(插空法)，
	// 得到最终所有的排列。比如，a插入串bc的所有(3个)空位，得到{abc,bac,bca}。
	// 递归的终止条件是什么呢？当一个串为空，就无法再取出其中的第0个字符了， 所以此时返回一个空的排列。
	public static ArrayList<String> getPerms(String s) {
		ArrayList<String> permutations = new ArrayList<String>();
		if (s == null) { // error case
			return null;
		} else if (s.length() == 0) { // base case
			permutations.add("");
			return permutations;
		}

		char first = s.charAt(0); // get the first character
		String remainder = s.substring(1); // remove the first character
		ArrayList<String> words = getPerms(remainder);
		for (String word : words) {
			for (int j = 0; j <= word.length(); j++) {
				permutations.add(insertCharAt(word, first, j));
			}
		}
		return permutations;
	}

	public static String insertCharAt(String word, char c, int i) {
		String start = word.substring(0, i);
		String end = word.substring(i);
		return start + c + end;
	}

	/**
	 * 实现一个算法打印出n对括号的有效组合。
	 * 
	 * <pre>
	 * 例如：
	 * 输入：3 （3对括号）
	 * 输出：
	 * ((())) 
	 * (()()) 
	 * (())() 
	 * ()(()) 
	 * ()()()
	 * </pre>
	 * 
	 * 对于一个输出，比如说(()())， 从左边起，取到任意的某个位置得到的串，左括号数量一定是大于或等于右括号的数量，
	 * 只有在这种情况下，这组输出才是有效的。 这样一来，在程序中，只要还有左括号，我们就加入输出串，然后递归调用。
	 * 当退出递归时，如果剩余的右括号数量比剩余的左括号数量多，我们就将右括号加入输出串。 直到最后剩余的左括号和右括号都为0时，即可打印一个输出串
	 */
	public static void printPar(int l, int r, char[] ch, int count) {
		if (l < 0 || r < l)
			return; // invalid state
		if (l == 0 && r == 0) {
			System.out.println(ch); // found one, so print it
		} else {
			if (l > 0) { // try a left paren, if there are some available
				ch[count] = '(';
				printPar(l - 1, r, ch, count + 1);
			}
			if (r > l) { // try a right paren, if there’s a matching left
				ch[count] = ')';
				printPar(l, r - 1, ch, count + 1);
			}
		}
	}

	public static void printPar(int count) {
		char[] str = new char[count * 2];
		printPar(count, count, str, 0);
	}

	/**
	 * 有25分，10分，5分和1分的硬币无限个。写一个函数计算组成n分的方式有几种？
	 * 
	 */
	public static int sumN(int n, int denom) {
		int nextDenom = 0;
		switch (denom) {
		case 25:
			nextDenom = 10;
			break;
		case 10:
			nextDenom = 5;
			break;
		case 5:
			nextDenom = 1;
			break;
		case 1:
			return 1;
		}
		int ways = 0;
		for (int i = 0; i * denom <= n; i++) {
			ways += sumN(n - i * denom, nextDenom);
		}
		return ways;
	}

	public static int sumN1(int sum, int c, int n) {
		int ways = 0;
		if (sum <= n) {
			if (sum == n)
				return 1;
			if (c >= 25)
				ways += sumN1(sum + 25, 25, n);
			if (c >= 10)
				ways += sumN1(sum + 10, 10, n);
			if (c >= 5)
				ways += sumN1(sum + 5, 5, n);
			if (c >= 1)
				ways += sumN1(sum + 1, 1, n);
		}
		return ways;
	}

	/**
	 * 经典的八皇后问题，即在一个8*8的棋盘上放8个皇后，使得这8个皇后无法互相攻击(
	 * 任意2个皇后不能处于同一行，同一列或是对角线上)，输出所有可能的摆放情况
	 * 
	 */
	// 我们用一个一维数组来表示相应行对应的列，比如c[i]=j表示， 第i行的皇后放在第j列。如果当前行是r，皇后放在哪一列呢？c[r]列。
	// 一共有8列，所以我们要让c[r]依次取第0列，第1列，第2列……一直到第7列，
	// 每取一次我们就去考虑，皇后放的位置会不会和前面已经放了的皇后有冲突。 怎样是有冲突呢？同行，同列，对角线。由于已经不会同行了，所以不用考虑这一点。
	// 同列：c[r]==c[j]; 同对角线有两种可能，即主对角线方向和副对角线方向。
	// 主对角线方向满足，行之差等于列之差：r-j==c[r]-c[j]; 副对角线方向满足， 行之差等于列之差的相反数：r-j==c[j]-c[r]。
	// 只有满足了当前皇后和前面所有的皇后都不会互相攻击的时候，才能进入下一级递归
	
	static int columnForRow[] = new int[8];

	static boolean check(int row) {
		for (int i = 0; i < row; i++) {
			int diff = Math.abs(columnForRow[i] - columnForRow[row]);
			if (diff == 0 || diff == row - i)
				return false;
		}
		return true;
	}

	static int PlaceQueen(int row) {
		int ways = 0;
		if (row == 8) {
			printBoard();
			return 1;
		}
		for (int i = 0; i < 8; i++) {
			columnForRow[row] = i;
			if (check(row)) {
				ways += PlaceQueen(row + 1);
			}
		}
		return ways;
	}

	private static void printBoard() {
		for (int i = 0; i < columnForRow.length; i++) {
			System.out.print("c[" + i + "]=" + columnForRow[i] + ";");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println(PlaceQueen(0));

		// int[] a = { 1, 2, 3 };
		// for (Set<Integer> s : getSubsets(a))
		// System.out.println(s);

//		ArrayList<Integer> set = new ArrayList<Integer>() {
//			{
//				add(1);
//				add(2);
//				add(3);
//			}
//
//		};

		// for(ArrayList<Integer> subs: getSubsets(set))
		// System.out.println(subs);
		// for (ArrayList<Integer> subs : getSubsets1(set, 0))
		// System.out.println(subs);

		// for (String s : getPerms("abc"))
		// System.out.println(s);

		// printPar(3);

		// System.out.println(sumN(100, 25));
		// System.out.println(sumN1(0, 25, 100));
	}
}
