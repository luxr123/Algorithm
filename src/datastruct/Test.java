package datastruct;

import java.util.ArrayList;
import java.util.List;

public class Test {
	Vertex root;

	class Vertex {
		int words;
		int prefixes;
		Vertex[] edges;

		Vertex() {
			words = 0;
			prefixes = 0;
			edges = new Vertex[26];
			for (int i = 0; i < edges.length; i++) {
				edges[i] = null;
			}
		}
	}

	Test() {
		root = new Vertex();
	}

	public void addWord(String word) {
		addWord(root, word);
	}

	private void addWord(Vertex vertex, String word) {
		if (word.length() == 0)
			vertex.words++;
		else {
			vertex.prefixes++;
			char c = word.charAt(0);
			c = Character.toLowerCase(c);
			int idx = c - 'a';
			if (vertex.edges[idx] == null) {
				vertex.edges[idx] = new Vertex();
			}
			addWord(vertex.edges[idx], word.substring(1));
		}
	}

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

	private void depthFirstSearchWords(List<String> words, Vertex vertex, String wordSegment) {
		Vertex[] edges = vertex.edges;
		boolean hasChildren = false;
		for (int i = 0; i < edges.length; i++) {
			if (edges[i] != null) {
				hasChildren = true;
				String word = wordSegment + (char) ('a' + i);
				depthFirstSearchWords(words, edges[i], word);
			}
		}
		if(!hasChildren)
			words.add(wordSegment);
	}
	
	public static void main(String[] args) {
		System.out.println("abc".length());
	}
}
