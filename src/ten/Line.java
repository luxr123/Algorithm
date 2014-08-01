package ten;

import java.util.HashMap;
import java.util.Map;

/**
 * 在一个二维图上有许多点，找出一条经过最多点的直线。
 * 
 * <pre>
 * 当两直线的斜率和截距相差都小于epsilon时，都将被映射到一个整数， 从而认为它们是一条直线。
 * </pre>
 */
public class Line {

	class Point {
		double x, y;
	}

	double epsilon, slope, intercept;
//	boolean bslope;

	Line() {
	};

	Line(Point p, Point q) {
		epsilon = 0.0001;
		if (java.lang.Math.abs(p.x - q.x) > epsilon) {
			slope = (p.y - q.y) / (p.x - q.x);
			intercept = p.y - slope * p.x;
//			bslope = true;
		} else {
//			bslope = false;
			intercept = p.x;
		}
	}

	@Override
	public int hashCode() {
		int sl = (int) (slope * 1000);
		int in = (int) (intercept * 1000);
		return sl * 1000 + in;
	}

	public Line find_best_line(Point[] p, int pointNum) {
		Line bestline = null;
		boolean first = true;
		Map<Integer, Integer> mii = new HashMap<Integer, Integer>();
		for (int i = 0; i < pointNum; ++i) {
			for (int j = i + 1; j < pointNum; ++j) {
				Line l = new Line(p[i], p[j]);
				if (!mii.containsKey(l.hashCode()))
					mii.put(l.hashCode(), 0);
				mii.put(l.hashCode(), mii.get(l.hashCode()) + 1);

				if (first) {
					bestline = l;
					first = false;
				} else {
					if (mii.get(l.hashCode()) > mii.get(bestline.hashCode()))
						bestline = l;
				}
			}
		}
//		int a = mii.get(bestline.hashCode());
//		System.out.println(a);
//		System.out.println((1 + java.lang.Math.sqrt(1 + 8 * a)) / 2);
		return bestline;
	}

	public static void main(String[] args) {
	}
}
