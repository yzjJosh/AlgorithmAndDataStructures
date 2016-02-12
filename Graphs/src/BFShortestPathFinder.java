import java.util.*;

public class BFShortestPathFinder implements ShortestPathFinder {
	
	private final int v;
	private final int[] edgeTo;
	private final double[] disTo;
	
	public BFShortestPathFinder(DirectedGraph<? extends WeightedEdge> graph, int v){
		if(graph == null)
			throw new NullPointerException();
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		this.v = v;
		this.edgeTo = new int[graph.V()];
		this.disTo = new double[graph.V()];
		Arrays.fill(edgeTo, -1);
		Arrays.fill(disTo, Double.POSITIVE_INFINITY);
		edgeTo[v] = v;
		disTo[v] = 0.0;
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(v);
		while(!queue.isEmpty())
			relax(graph, queue.pollFirst(), queue);
	}
	
	private void relax(DirectedGraph<? extends WeightedEdge> graph, int v, List<Integer> queue){
		for(WeightedEdge e: graph.adj(v))
			if(disTo[e.w] > disTo[v]+e.weight){
				disTo[e.w] = disTo[v]+e.weight;
				edgeTo[e.w] = v;
				queue.add(e.w);
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