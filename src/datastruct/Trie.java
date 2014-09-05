package datastruct;

/**
 * trie树每一层的节点数是26^i级别的。所以为了节省空间。我们用动态链表，或者用数组来模拟动态。空间的花费，不会超过单词数×单词长度。(转自一大牛)
 */
import java.util.ArrayList;
import java.util.List;

public class Trie {

	private Vertex root;// 一个Trie树有一个根节点

	// 内部类
	protected class Vertex {// 节点类
		protected int words;
		protected int prefixes;
		protected Vertex[] edges;// 每个节点包含26个子节点(类型为自身)

		Vertex() {
			words = 0;
			prefixes = 0;
			edges = new Vertex[26];
			for (int i = 0; i < edges.length; i++)
				edges[i] = null;
		}
	}

	public Trie() {
		root = new Vertex();
	}

	/** */
	/**
	 * Add a word to the Trie.
	 * 
	 * @param word
	 *            The word to be added.
	 */

	public void addWord(String word) {
		addWord(root, word);
	}

	/** */
	/**
	 * Add the word from the specified vertex.
	 * 
	 * @param vertex
	 *            The specified vertex.
	 * @param word
	 *            The word to be added.
	 */

	private void addWord(Vertex vertex, String word) {
		// if all characters of the word has been added
		if (word.length() == 0) {
			vertex.words++;
		} else {
			vertex.prefixes++;
			char c = word.charAt(0);
			c = Character.toLowerCase(c);
			int index = c - 'a';
			if (vertex.edges[index] == null) { // if the edge does NOT exist
				vertex.edges[index] = new Vertex();
			}
			// go the the next character
			addWord(vertex.edges[index], word.substring(1));
		}
	}

	/** */
	/**
	 * List all words in the Trie.
	 * 
	 * @return
	 */

	public List<String> listAllWords() {

		List<String> words = new ArrayList<String>();
		Vertex[] edges = root.edges;

		for (int i = 0; i < edges.length; i++) {
			if (edges[i] != null) {
				String word = "" + (char) ('a' + i);
				depthFirstSearchWords(words, edges[i], word);
			}
		}
		return words;
	}

	/*
	 * DFS find all the words from Node "ptr" 实际就是寻找“以当前前缀本身为单词”的单词
	 */
	public List<String> DFS(String prefixSegment) {
		if (prefixSegment.length() == 0)
			return listAllWords();

		List<String> preWords = new ArrayList<String>();// 查找字典中某个公共前缀的所有单词
		char c = prefixSegment.charAt(0);
		c = Character.toLowerCase(c);
		int index = c - 'a';
		Vertex vertex = root.edges[index];
		if (vertex != null) {
			dfsWords(preWords, vertex, prefixSegment, 1);
		}
		return preWords;
	}

	private void dfsWords(List<String> words, Vertex vertex, String prefixSegment, int index) {
		if (prefixSegment.length() == index) {
			depthFirstSearchWords(words, vertex, prefixSegment);
		} else {
			char c = prefixSegment.charAt(index);
			c = Character.toLowerCase(c);
			int idx = c - 'a';
			if (vertex.edges[idx] != null) {
				dfsWords(words, vertex.edges[idx], prefixSegment, index + 1);
			}
		}
	}

	/** */
	/**
	 * DFS Depth First Search words in the Trie and add them to the List.
	 * 
	 * @param words
	 * @param vertex
	 * @param wordSegment
	 */

	private void depthFirstSearchWords(List<String> words, Vertex vertex, String wordSegment) {
		Vertex[] edges = vertex.edges;
		boolean hasChildren = false;
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] != null) {
				hasChildren = true;
				String newWord = wordSegment + (char) ('a' + i);
				depthFirstSearchWords(words, edges[i], newWord);
			}
		}
		if (!hasChildren) {
			words.add(wordSegment);
		}
	}

	public int countPrefixes(String prefix) {
		return countPrefixes(root, prefix);
	}

	private int countPrefixes(Vertex vertex, String prefixSegment) {
		if (prefixSegment.length() == 0) { // reach the last character of the
											// word
			return vertex.prefixes;
		}

		char c = prefixSegment.charAt(0);
		int index = c - 'a';
		if (vertex.edges[index] == null) { // the word does NOT exist
			return 0;
		} else {
			return countPrefixes(vertex.edges[index], prefixSegment.substring(1));
		}

	}

	public int countWords(String word) {
		return countWords(root, word);
	}

	private int countWords(Vertex vertex, String wordSegment) {
		if (wordSegment.length() == 0) { // reach the last character of the word
			return vertex.words;
		}

		char c = wordSegment.charAt(0);
		int index = c - 'a';
		if (vertex.edges[index] == null) { // the word does NOT exist
			return 0;
		} else {
			return countWords(vertex.edges[index], wordSegment.substring(1));

		}

	}

	public static void main(String args[]) // Just used for test
	{
		Trie trie = new Trie();
		trie.addWord("China");
		trie.addWord("China");
		trie.addWord("China");

		trie.addWord("crawl");
		trie.addWord("crime");
		trie.addWord("ban");
		trie.addWord("China");

		trie.addWord("english");
		trie.addWord("establish");
		trie.addWord("eat");
		System.out.println(trie.root.prefixes);
		System.out.println(trie.root.words);
		//
		List<String> list = trie.DFS("ch");
		// List<String> list = trie.listAllWords();
		for (String s : list)
			System.out.println("words:" + s);

		int count = trie.countPrefixes("c");
		int count1 = trie.countWords("china");
		System.out.println("the count of c prefixes:" + count);
		System.out.println("the count of china countWords:" + count1);

	}
}
