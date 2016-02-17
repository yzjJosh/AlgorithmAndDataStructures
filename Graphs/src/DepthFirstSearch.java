import java.util.Arrays;
import java.util.LinkedList;

public class DepthFirstSearch implements Search {
	
	private final int[] edgeTo;
	private final int v;
	private final LinkedList<Integer> connectedVertices;

	public DepthFirstSearch(Graph<?> graph, int v) {
		if(graph == null)
			throw new NullPointerException();
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		this.v = v;
		this.edgeTo = new int[graph.V()];
		this.connectedVertices = new LinkedList<Integer>();
		Arrays.fill(edgeTo, -1);
		edgeTo[v] = v;
		dfs(graph, v);
	}
	
	private void dfs(Graph<?> graph, int v){
		connectedVertices.add(v);
		for(Edge e: graph.adj(v)){
			if(edgeTo[e.w] != -1) continue;
			edgeTo[e.w] = v;
			dfs(graph, e.w);
		}
	}

	@Override
	public boolean isConnected(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		return edgeTo[w] != -1;
	}

	@Override
	public int connectedCount() {
		return connectedVertices.size();
	}

	@Override
	public Iterable<Integer> connectedVertices() {
		return connectedVertices;
	}

	@Override
	public Iterable<Integer> pathTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		LinkedList<Integer> ret = new LinkedList<Integer>();
		if(!isConnected(w)) return ret;
		ret.addFirst(w);
		while(w != v)
			ret.addFirst(w = edgeTo[w]);
		return ret;
	}

}
