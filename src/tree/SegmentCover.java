/**
 * 给定一些线段，线段有起点和终点，求这些线段的覆盖长度，重复的部分只计算一次。
 * 本节给出两种方法，一种是基于排序来做的，一种是使用线段树来做的
 */
package tree;

/**
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年8月13日 下午2:50:01]
 * @Version: [v1.0]
 * 
 */
public class SegmentCover {
	/* 线段树求线段覆盖长度 */
	static final int BORDER = 100; // 设线段端点坐标不超过100

	static Node[] segTree = new Node[4 * BORDER];

	class Node {// 线段树
		int left;
		int right;
		boolean isCover; // 标记是否被覆盖
	}

	public SegmentCover() {
		for (int i = 0; i < segTree.length; i++)
			segTree[i] = new Node();
	}

	/* 构建线段树 根节点开始构建区间[lef,rig]的线段树 */
	void construct(int index, int lef, int rig) {
		segTree[index].left = lef;
		segTree[index].right = rig;
		if (rig - 1 == lef) {// 到单位1线段
			segTree[index].isCover = false;
			return;
		}
		int mid = (lef + rig) >> 1;
		construct((index << 1) + 1, lef, mid);
		construct((index << 1) + 2, mid, rig); // 非mid+1，线段覆盖[mid,mid+1]
		segTree[index].isCover = false;
	}

	/* 插入线段[start,end]到线段树, 同时标记覆盖 */
	void insert(int index, int start, int end) {
		if (segTree[index].isCover)
			return; // 如已覆盖，没必要继续向下插

		if (segTree[index].left == start && segTree[index].right == end) {
			segTree[index].isCover = true;
			return;
		}
		int mid = (segTree[index].left + segTree[index].right) >> 1;
		if (end <= mid) {
			insert((index << 1) + 1, start, end);
		} else if (start >= mid) {// 勿漏=
			insert((index << 1) + 2, start, end);
		} else {
			insert((index << 1) + 1, start, mid);
			insert((index << 1) + 2, mid, end);
			// 注：不是mid+1，线段覆盖，不能漏[mid,mid+1]
		}
	}

	/* 计算线段覆盖长度 */
	int count(int index) {
		if (segTree[index].isCover) {
			return segTree[index].right - segTree[index].left;
		} else if (segTree[index].right - segTree[index].left == 1) {
			return 0;
		}
		return count((index << 1) + 1) + count((index << 1) + 2);
	}

	/* 测试线段 answer: 71 */
	static int[][] segment = { { 5, 8 }, { 10, 45 }, { 0, 7 }, { 2, 3 }, { 3, 9 }, { 13, 26 }, { 15, 38 }, { 50, 67 }, { 39, 42 },
			{ 70, 80 } };

	public static void main(String[] args) {
		SegmentCover cover = new SegmentCover();
		cover.construct(0, 0, 100); // 构建[0,100]线段树
		for (int i = 0; i < segment.length; i++) {// 插入测试线段
			cover.insert(0, segment[i][0], segment[i][1]);
		}
		System.out.format("the cover length is %d\n", cover.count(0));

		int a = 192, b = 168, c = 1, d = 100;
		System.out.println((a << 24) | (b << 16) | (c << 8) | d);
		System.out.println((a << 20) | (b << 12) | (c << 4) | d);
	}

}
