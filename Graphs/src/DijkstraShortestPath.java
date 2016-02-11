public class DijkstraShortestPath extends ShortestPath {

	public DijkstraShortestPath(Graph<? extends WeightedEdge> graph, int v) {
		super(graph, v);
		
	}

	@Override
	public Iterable<Integer> shortestPathTo(int w) {
		return null;
	}

	@Override
	public double shortestDistanceTo(int w) {
		return 0;
	}

}
