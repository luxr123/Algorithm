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
public class SegmentCover2 {
	/**
	 * 方法一：
	 * 
	 * 首先说排序对于处理很多问题都是非常有效的，例如寻找兄弟单词等问题中，经过排序处理后，问题就明朗了很多；
	 * 
	 * 线段覆盖长度也是这样，将线段排序后，然后扫描一遍就可以得到覆盖的长度。具体做法：排序时，先按线段的起始端点排序，如果始点相同则按照末端点排，
	 * 然后从头扫描
	 * ，寻找连续段；所谓连续段即下一条线段的始点不大于当前线段的末点就一直扫描，直到找到断层的，计算当前长度，然后继续重复扫描直到最后，便得总长度。
	 */
	static Segment[] segArr = new Segment[100];

	public Segment SEGMENT = new Segment();

	class Segment {
		int start;
		int end;
	}

	/* 计算线段覆盖长度 */
	public int lenCount(Segment[] segArr, int size) {
		int length = 0, start = 0, end = 0;
		for (int i = 0; i < size; ++i) {
			start = segArr[i].start;
			end = segArr[i].end;
			while (end >= segArr[i + 1].start) {
				i++;
				end = segArr[i].end > end ? segArr[i].end : end;
			}
			length += (end - start);
		}
		return length;
	}

	/* 测试线段 answer: 71 */
	static int[][] segTest = { { 5, 8 }, { 10, 45 }, { 0, 7 }, { 2, 3 }, { 3, 9 }, { 13, 26 }, { 15, 38 }, { 50, 67 }, { 39, 42 },
			{ 70, 80 } };

	public static void main(String[] args) {// 测试线段
		SegmentCover2 segmentTree = new SegmentCover2();
		for (int i = 0; i < 10; i++) {
			Segment segment = segmentTree.SEGMENT;
			segment.start = segTest[i][0];
			segment.end = segTest[i][1];
			segArr[i] = segment;
		}
		// 排序
	}
}
