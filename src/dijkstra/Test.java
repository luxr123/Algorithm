package dijkstra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple to Introduction
 * 
 * @ProjectName: [Algorithm]
 * @Package: [dijkstra]
 * @ClassName: [Test]
 * @Description: [一句话描述该类的功能]
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年7月3日 下午8:53:14]
 * @UpdateUser: [xiaorui.lu]
 * @UpdateDate: [2014年7月3日 下午8:53:14]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class Test {

	private static List<Vertex> nodes;
	private static List<Edge> edges;

	public static void main(String[] args) {

		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		for (int i = 0; i < 11; i++) {
			Vertex location = new Vertex("Vertex_" + i, "Node_" + i);
			nodes.add(location);
		}

		addLane("Edge_0", 0, 1, 85);
		addLane("Edge_1", 0, 2, 217);
		addLane("Edge_2", 0, 4, 173);
		addLane("Edge_3", 2, 6, 186);
		addLane("Edge_4", 2, 7, 103);
		addLane("Edge_5", 3, 7, 183);
		addLane("Edge_6", 5, 8, 250);
		addLane("Edge_7", 8, 9, 84);
		addLane("Edge_8", 7, 9, 167);
		addLane("Edge_9", 4, 9, 502);
		addLane("Edge_10", 9, 10, 197);
		addLane("Edge_11", 1, 10, 600);

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(nodes.get(0));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));

		// assertNotNull(path);
		// assertTrue(path.size() > 0);
		if (null != path && path.size() > 0)
			for (Vertex vertex : path) {
				System.out.println(vertex);
			}

	}

	private static void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);
	}
}
