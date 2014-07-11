package dijkstra;

/**
 * Simple to Introduction
 * 
 * @ProjectName: [Algorithm]
 * @Package: [dijkstra]
 * @ClassName: [Vertex]
 * @Description: [A graph consists of vertices and edges.]
 * @Author: [xiaorui.lu]
 * @CreateDate: [2014年7月3日 下午8:49:36]
 * @UpdateUser: [xiaorui.lu]
 * @UpdateDate: [2014年7月3日 下午8:49:36]
 * @UpdateRemark: [说明本次修改内容]
 * @Version: [v1.0]
 * 
 */
public class Vertex {
	final private String id;
	final private String name;

	public Vertex(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
