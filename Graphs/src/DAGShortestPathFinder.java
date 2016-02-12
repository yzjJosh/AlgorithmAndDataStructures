import java.util.*;

public class DAGShortestPathFinder implements ShortestPathFinder{
	
	private int v;
	private int[] edgeTo;
	private double[] disTo;
	
	public DAGShortestPathFinder(DirectedGraph<? extends WeightedEdge> graph, int v){
		if(graph == null)
			throw new NullPointerException();
		if(new CircleFinder(graph).hasCircle())
			throw new IllegalArgumentException("Graph is not DAG!");
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		this.v = v;
		this.edgeTo = new int[graph.V()];
		this.disTo = new double[graph.V()];
		Arrays.fill(edgeTo, -1);
		Arrays.fill(disTo, Double.POSITIVE_INFINITY);
		disTo[v] = 0.0;
		LinkedList<Integer> order = new LinkedList<Integer>();
		dfs(graph, v, v, order);
		for(int n: order)
			relax(graph, n);
	}
	
	private void dfs(DirectedGraph<?> graph, int from, int n, LinkedList<Integer> order){
		if(edgeTo[n] >= 0) return;
		edgeTo[n] = from;
		for(Edge e: graph.adj(n))
			dfs(graph, n, e.w, order);
		order.addFirst(n);
	}
	
	private void relax(DirectedGraph<? extends WeightedEdge> graph, int n){
		for(WeightedEdge e: graph.adj(n))
			if(disTo[e.w] > disTo[e.v]+e.weight){
				disTo[e.w] = disTo[e.v]+e.weight;
				edgeTo[e.w] = n;
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

}
