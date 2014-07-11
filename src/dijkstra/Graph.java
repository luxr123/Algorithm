package dijkstra;

import java.util.List;

/**
 * Simple to Introduction
 * 
 * @ProjectName: [Algorithm]
 * @Package: [dijkstra]
 * @ClassName: [Graph]
 * @Description: [Both represent a graph.]
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年7月3日 下午8:50:22]
 * @UpdateUser: [xiaorui.lu]
 * @UpdateDate: [2014年7月3日 下午8:50:22]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class Graph {
	// private final List<Vertex> vertexes;
	private final List<Edge> edges;

	public Graph(List<Edge> edges) {
		super();
		this.edges = edges;
	}

	// public Graph(List<Vertex> vertexes, List<Edge> edges) {
	// this.vertexes = vertexes;
	// this.edges = edges;
	// }

	// public List<Vertex> getVertexes() {
	// return vertexes;
	// }

	public List<Edge> getEdges() {
		return edges;
	}

}
