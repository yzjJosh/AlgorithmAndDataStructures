import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class BreathFirstSearch implements Search {

	private final int[] edgeTo;
	private final int v;
	private int connectedCount;
	private final LinkedList<Integer> connectedVertices;
	
	public BreathFirstSearch(Graph<?> graph, int v) {
		if(graph == null)
			throw new NullPointerException();
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		this.v = v;
		this.edgeTo = new int[graph.V()];
		this.connectedCount = 0;
		this.connectedVertices = new LinkedList<Integer>();
		Arrays.fill(edgeTo, -1);
		edgeTo[v] = v;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(v);
		while(!queue.isEmpty()){
			int w = queue.pollFirst();
			connectedCount ++;
			connectedVertices.add(w);
			for(Edge e: graph.adj(w)){
				if(edgeTo[e.w] != -1) continue;
				edgeTo[e.w] = w;
				queue.add(e.w);
			}
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
		return connectedCount;
	}

	@Override
	public Iterable<Integer> connectedVertices() {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		ret.addAll(connectedVertices);
		return ret;
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
