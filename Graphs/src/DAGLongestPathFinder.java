public class DAGLongestPathFinder {
	
	private final DAGShortestPathFinder finder;
	
	public DAGLongestPathFinder(DirectedGraph<WeightedEdge> graph, int v){
		if(graph == null)
			throw new NullPointerException();
		if(new CircleFinder(graph).hasCircle())
			throw new IllegalArgumentException("Graph is not DAG!");
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		DirectedGraph<WeightedEdge> negate = new DirectedGraph<WeightedEdge>(graph.V());
		for(WeightedEdge e: graph.edges())
			negate.addEdge(new WeightedEdge(e.v, e.w, -e.weight));
		finder = new DAGShortestPathFinder(negate, v);
	}
	
	public Iterable<Integer> pathTo(int w){
		return finder.pathTo(w);
	}
	
	public double distanceTo(int w){
		if(!hasPathTo(w)) return Double.POSITIVE_INFINITY;
		return -finder.distanceTo(w);
	}
	
	public boolean hasPathTo(int w){
		return finder.hasPathTo(w);
	}
}
