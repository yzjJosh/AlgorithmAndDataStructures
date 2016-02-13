import java.util.LinkedList;

public class BFNegativeCircleFinder implements NegativeCircleFinder{
	
	private LinkedList<Integer> negativeCircle;
	
	public BFNegativeCircleFinder(DirectedGraph<WeightedEdge> graph){
		if(graph == null)
			throw new NullPointerException();
		boolean[] visited = new boolean[graph.V()];
		for(int i=0; i<graph.V(); i++){
			if(visited[i]) continue;
			BFShortestPathFinder f = new BFShortestPathFinder(graph, i);
			if(f.hasNegativeCircle()){
				negativeCircle = (LinkedList<Integer>) f.negativeCircle();
				break;
			}
			for(int v: new DepthFirstSearch(graph, i).connectedVertices())
				visited[v] = true;
		}
	}

	@Override
	public boolean hasNegativeCircle() {
		return negativeCircle != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Integer> negativeCircle() {
		if(!hasNegativeCircle()) return new LinkedList<Integer>();
		return (Iterable<Integer>) negativeCircle.clone();
	}

}
