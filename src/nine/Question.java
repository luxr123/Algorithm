package nine;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 马戏团设计了这样一个节目：叠罗汉。一群人往上叠，每个人都踩在另一个人的肩膀上。 要求上面的人要比下面的人矮而且比下面的人轻。给出每个人的身高和体重，
 * 写一个函数计算叠罗汉节目中最多可以叠多少人？
 * 
 * <pre>
 * 例子：
 * 输入(身高 体重)：(65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)
 * 输出：最多可叠人数：6 （从上到下是：(56, 90) (60,95) (65,100) (68,110) (70,150) (75,190)）
 * </pre>
 */

public class Question {

	static ArrayList<Person> items;
	ArrayList<Person> lastFoundSeq;
	ArrayList<Person> maxSeq;

	static {
		final Person p1 = new Person(65, 100);
		final Person p2 = new Person(70, 150);
		final Person p3 = new Person(56, 290);
		final Person p4 = new Person(75, 190);
		final Person p5 = new Person(60, 95);
		final Person p6 = new Person(68, 110);
		items = new ArrayList<Person>() {
			{
				add(p1);
				add(p2);
				add(p3);
				add(p4);
				add(p5);
				add(p6);
			}
		};
	}

	// Returns longer sequence
	ArrayList<Person> seqWithMaxLength(ArrayList<Person> seq1, ArrayList<Person> seq2) {
		return seq1.size() > seq2.size() ? seq1 : seq2;
	}

	// Fills next seq w decreased wts&returns index of 1st unfit item.
	int fillNextSeq(int startFrom, ArrayList<Person> seq) {
		int firstUnfitItem = startFrom;
		if (startFrom < items.size()) {
			for (int i = 0; i < items.size(); i++) {
				Person item = items.get(i);
				if (i == 0 || items.get(i - 1).isBefore(item)) {
					seq.add(item);
				} else {
					firstUnfitItem = i;
				}
			}
		}
		return firstUnfitItem;
	}

	// Find the maximum length sequence
	void findMaxSeq() {
		Collections.sort(items);
		int currentUnfit = 0;
		while (currentUnfit < items.size()) {
			ArrayList<Person> nextSeq = new ArrayList<Person>();
			int nextUnfit = fillNextSeq(currentUnfit, nextSeq);
			maxSeq = seqWithMaxLength(maxSeq, nextSeq);
			if (nextUnfit == currentUnfit)
				break;
			else
				currentUnfit = nextUnfit;
		}
	}

	static int n = 4;
	static Integer d[] = new Integer[n];

	static int lis(int p[], int n) {
		int k = 1;
		d[0] = p[0];
		for (int i = 1; i < n; ++i) {
			if (p[i] >= d[k - 1])
				d[k++] = p[i];
			else {
				int j;
				for (j = k - 1; j >= 0 && d[j] > p[i]; --j)
					;// 用二分可将复杂度降到O(nlogn)
				d[j + 1] = p[i];
			}
		}
		return k;
	}

	public static void main(String[] args) {
		int p[] = { 2, 5, 1, 8 };
		System.out.println(lis(p, n));
		for (Integer i : d)
			System.out.println(i);
	}
}

class Person implements Comparable<Person> {

	Integer hight;
	Integer weight;

	public boolean isBefore(Person item) {

		return false;
	}

	public Person(Integer hight, Integer weight) {
		super();
		this.hight = hight;
		this.weight = weight;
	}

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Person p2) {
		if (this.getHight() < p2.getHight() || this.getWeight() < p2.getWeight())
			return -1;
		else if (this.getHight() > p2.getHight() || this.getWeight() > p2.getWeight())
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Person [hight=" + hight + ", weight=" + weight + "]";
	}
}
