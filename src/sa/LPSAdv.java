package sa;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LPSAdv {
	static final int MAXN = 2200;
	int[] wa = new int[MAXN];
	int[] wb = new int[MAXN];
	int[] wv = new int[MAXN];
	int[] ws = new int[MAXN];

	private boolean cmp(int[] r, int a, int b, int l) {
		return (r[a] == r[b]) && (r[a + l] == r[b + l]);
	}

	public void da(int[] r, int[] sa, int n, int m) {
		int i, j, p;
		int[] x = wa, y = wb;
		int[] t = new int[MAXN];

		for (i = 0; i < m; i++)
			ws[i] = 0;
		for (i = 0; i < n; i++)
			ws[x[i] = r[i]]++;
		for (i = 1; i < m; i++)
			ws[i] += ws[i - 1];
		for (i = n - 1; i >= 0; i--)
			sa[--ws[x[i]]] = i;

		for (j = 1, p = 1; p < n; j *= 2, m = p) {
			for (p = 0, i = n - j; i < n; i++)
				y[p++] = i;
			for (i = 0; i < n; i++)
				if (sa[i] >= j)
					y[p++] = sa[i] - j;
			for (i = 0; i < n; i++)
				wv[i] = x[y[i]];
			for (i = 0; i < m; i++)
				ws[i] = 0;
			for (i = 0; i < n; i++)
				ws[wv[i]]++;
			for (i = 1; i < m; i++)
				ws[i] += ws[i - 1];
			for (i = n - 1; i >= 0; i--)
				sa[--ws[wv[i]]] = y[i];
			for (t = x, x = y, y = t, p = 1, x[sa[0]] = 0, i = 1; i < n; i++)
				x[sa[i]] = cmp(y, sa[i - 1], sa[i], j) ? p - 1 : p++;
		}
	}

	static int[] rank = new int[MAXN];
	static int[] height = new int[MAXN];

	void calheight(int[] r, int[] sa, int n) {
		int i, j, k = 0;
		for (i = 1; i <= n; i++)
			rank[sa[i]] = i;
		for (i = 0; i < n; height[rank[i++]] = k)
			for (k = k > 0 ? k - 1 : 0, j = sa[rank[i] - 1]; r[i + k] == r[j + k]; k++)
				;
	}

	static int[] sa;

	static String ori1;
	static String ori2;

	public static void main(String[] args) {
		String s1 = "aaafffffbbbbcccaaa";
		String s2 = "aaafffffccc";
		long start = System.currentTimeMillis();

		ori1 = s1;
		ori2 = s2;

		String s = s1 + "|" + s2;

		int len = s.length();
		LPSAdv doubling = new LPSAdv();
		int[] r = new int[len + 1];
		sa = new int[len + 1];
		for (int i = 0; i < len; i++) {
			r[i] = s.charAt(i) - 'a' + 1;
		}
		r[len] = 0;
		doubling.da(r, sa, len + 1, 128);

		doubling.calheight(r, sa, len);

		System.out.println(Lrs(s));
		
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}

	static List<Integer> hasIdx = new ArrayList<Integer>();

	static int Lrs(String s) {
		Map<String, Integer> map1 = new LinkedHashMap<String, Integer>();
		Map<String, Integer> map2 = new LinkedHashMap<String, Integer>();
		int sum = 0;
		if (s == null)
			return 0;
		for (int i = 1; i < s.length(); i++) {
			if (hasIdx.contains(i))
				continue;
			Integer tmpLen = comlen(s.substring(sa[i]), s.substring(sa[i + 1]));
			if (tmpLen == 0)
				continue;
			if (tmpLen >= 3) {
				for (int j = 0; j < tmpLen; j++)
					hasIdx.add(rank[sa[i] + j]);
				String sub = s.substring(sa[i]).substring(0, tmpLen);
				map1.put(sub, ori1.indexOf(sub));
				map2.put(sub, ori2.indexOf(sub));
			}
			sum += tmpLen;
		}
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		int max = -1;
		for (Entry<String, Integer> entry : map1.entrySet()) {
			Integer index = entry.getValue();
			if (index < max)
				list1.add(entry.getKey());
			else
				max = index;
		}
		max = -1;
		for (Entry<String, Integer> entry : map2.entrySet()) {
			Integer index = entry.getValue();
			if (index < max)
				list2.add(entry.getKey());
			else
				max = index;
		}
		int res1 = sum;
		for (String str : list1)
			res1 -= str.length();
		int res2 = sum;
		for (String str : list2)
			res2 -= str.length();

		return sum = res1 > res2 ? res2 : res1;
	}

	static String FLAG = "|";

	private static Integer comlen(String next, String next2) {
		if ((next.contains(FLAG) && next2.contains(FLAG)) || (!next.contains(FLAG) && !next2.contains(FLAG)))
			return 0;
		char[] c1 = next.toCharArray();
		char[] c2 = next2.toCharArray();
		int tmp = 0;
		for (int i = 1; (c1[0] == c2[0]) && (i < (c1.length > c2.length ? c2.length : c1.length)); i++) {
			if ((c1[i] == c2[i]) && (c1[i] >= c1[i - 1])) {
				tmp++;
			} else {
				tmp = tmp >= 2 ? (tmp + 1) : 0;
				return tmp;
			}
		}
		return tmp = tmp >= 2 ? (tmp + 1) : 0;
	}
}