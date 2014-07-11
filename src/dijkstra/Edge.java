package dijkstra;

/**
 * Simple to Introduction
 * 
 * @ProjectName: [Algorithm]
 * @Package: [dijkstra]
 * @ClassName: [Edge]
 * @Description: [A edge has a source and a destination.]
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年7月3日 下午8:50:03]
 * @UpdateUser: [xiaorui.lu]
 * @UpdateDate: [2014年7月3日 下午8:50:03]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class Edge {
	private final String id;
	private final Vertex source;
	private final Vertex destination;
	private final int weight;

	public Edge(String id, Vertex source, Vertex destination, int weight) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public Vertex getDestination() {
		return destination;
	}

	public Vertex getSource() {
		return source;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return source + " " + destination;
	}

}
