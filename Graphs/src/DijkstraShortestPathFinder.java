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
		disTo = new double[graph.V()];
		edgeTo = new int[graph.V()];
		Arrays.fill(disTo, Double.POSITIVE_INFINITY);
		Arrays.fill(edgeTo, -1);
		TreeMap<Double, Set<Integer>> map = new TreeMap<Double, Set<Integer>>();
		disTo[v] = 0;
		edgeTo[v] = v;
		replace(map, v, 0.0, 0.0);
		while(!map.isEmpty())
			relax(graph, pollSmallest(map), map);
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
	
	private void relax(Graph<? extends WeightedEdge> graph, int v, TreeMap<Double, Set<Integer>> map){
		for(WeightedEdge e: graph.adj(v))
			if(disTo[e.w] > disTo[v]+e.weight){
				replace(map, e.w, disTo[e.w], disTo[v]+e.weight);
				disTo[e.w] = disTo[v] + e.weight;
				edgeTo[e.w] = v;
			}
	}
	
	private int pollSmallest(TreeMap<Double, Set<Integer>> map){
		double smallest = map.firstKey();
		Set<Integer> set = map.get(smallest);
		int res = set.iterator().next();
		set.remove(res);
		if(set.isEmpty()) map.remove(smallest);
		return res;
	}
	
	private void replace(TreeMap<Double, Set<Integer>> map, int v, double old, double new_){
		Set<Integer> set = map.get(old);
		if(set != null){
			set.remove(v);
			if(set.isEmpty())
				map.remove(old);
		}
		set = map.get(new_);
		if(set == null){
			set = new HashSet<Integer>();
			map.put(new_, set);
		}
		set.add(v);
	}

}
