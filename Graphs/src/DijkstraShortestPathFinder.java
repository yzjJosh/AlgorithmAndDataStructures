import java.util.*;

public class DijkstraShortestPathFinder implements ShortestPathFinder {
	
	private final int v;
	private final double[] disTo;
	private final int[] edgeTo;

	public DijkstraShortestPathFinder(DirectedGraph<? extends WeightedEdge> graph, int v) {
		if(graph == null)
			throw new NullPointerException();
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		for(WeightedEdge e: graph.edges())
			if(e.weight < 0)
				throw new IllegalArgumentException("Graph has negative edge "+e+"!");
		this.v = v;
		this.disTo = new double[graph.V()];
		this.edgeTo = new int[graph.V()];
		Arrays.fill(disTo, Double.POSITIVE_INFINITY);
		Arrays.fill(edgeTo, -1);
		disTo[v] = 0.0;
		edgeTo[v] = v;
		IndexedPriorityQueue<Integer, Double> pq = new IndexedPriorityQueue<Integer, Double>(true);
		pq.put(v, 0.0);
		while(!pq.isEmpty()){
			int next = pq.peekKey();
			pq.poll();
			relax(graph, next, pq);
		}
			
	}

	@Override
	public Iterable<Integer> pathTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		LinkedList<Integer> ret = new LinkedList<Integer>();
		if(!hasPathTo(w)) return ret;
		ret.addFirst(w);
		while(w != v)
			ret.addFirst(w = edgeTo[w]);
		return ret;
	}

	@Override
	public double distanceTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		return disTo[w];
	}

	@Override
	public boolean hasPathTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		return edgeTo[w] >= 0;
	}
	
	private void relax(Graph<? extends WeightedEdge> graph, int v, IndexedPriorityQueue<Integer, Double> pq){
		for(WeightedEdge e: graph.adj(v))
			if(disTo[e.w] > disTo[v]+e.weight){
				pq.put(e.w, disTo[v]+e.weight);
				disTo[e.w] = disTo[v] + e.weight;
				edgeTo[e.w] = v;
			}
	}
}
