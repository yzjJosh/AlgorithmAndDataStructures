public abstract class ShortestPath {
	
	public ShortestPath(Graph<? extends WeightedEdge> graph, int v){}
	
	public abstract Iterable<Integer> shortestPathTo(int w);
	
	public abstract double shortestDistanceTo(int w);

}
