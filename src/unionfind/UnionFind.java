package unionfind;

public class UnionFind {
	int MAX = 10;

	int[] father = new int[MAX]; /* father[x]表示x的父节点 */
	int[] rank = new int[MAX]; /* rank[x]表示x的秩 */

	public UnionFind() {
		Make_Set();
	}

	/* 初始化集合 */
	void Make_Set() {
		for (int i = 0; i < father.length; i++) {
			father[i] = i; // 根据实际情况指定的父节点可变化
			rank[i] = 0; // 根据实际情况初始化秩也有所变化
		}
	}

	/**
	 * 当我们经过"递推"找到祖先节点后，"回溯"的时候顺便将它的子孙节点都直接指向祖先，
	 * 这样以后再次Find_Set(x)时复杂度就变成O(1)了
	 */
	/* 查找x元素所在的集合,回溯时压缩路径 */
	int Find_Set(int x) {
		if (x != father[x]) {
			father[x] = Find_Set(father[x]); // 这个回溯时的压缩路径是精华
		}
		return father[x];
	}

	/*
	 * 按秩合并x,y所在的集合 下面的那个if else结构不是绝对的，具体根据实际情况变化 但是，宗旨是不变的即，按秩合并，实时更新秩。
	 */
	void Union(int x, int y) {
		x = Find_Set(x);
		y = Find_Set(y);
		if (x == y)
			return;
		if (rank[x] > rank[y]) {
			father[y] = x;
			rank[x] += rank[y];
		} else {
			if (rank[x] == rank[y]) {
				rank[y]++;
			}
			father[x] = y;
		}
	}

	public static void main(String[] args) {
		UnionFind uf = new UnionFind();
		uf.Union(1, 2);
		uf.Union(1, 3);
		uf.Union(4, 6);
		uf.Union(3, 4);
		uf.Union(4, 5);
		for (int i = 0; i < uf.father.length; i++) {
			System.out.println(uf.father[i]);
		}
		
		System.out.println((1<<24)|(1<<16)|(1<<8)|1);
	}
}
