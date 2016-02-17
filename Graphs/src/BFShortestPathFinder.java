import java.util.*;

public class BFShortestPathFinder implements ShortestPathFinder, NegativeCircleFinder {
	
	private final int v;
	private final int[] edgeTo;
	private final double[] disTo;
	private int cost;
	private Iterable<Integer> negativaCircle;
	
	public BFShortestPathFinder(DirectedGraph<? extends WeightedEdge> graph, int v){
		if(graph == null)
			throw new NullPointerException();
		if(v >= graph.V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		this.v = v;
		this.edgeTo = new int[graph.V()];
		this.disTo = new double[graph.V()];
		this.cost = 0;
		Arrays.fill(disTo, Double.POSITIVE_INFINITY);
		disTo[v] = 0.0;
		edgeTo[v] = -1;
		boolean[] inQ = new boolean[graph.V()];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(v);
		inQ[v] = true;
		while(!queue.isEmpty() && !hasNegativeCircle()){
			inQ[queue.peekFirst()] = false;
			relax(graph, queue.pollFirst(), queue, inQ);
		}
	}
	
	private void relax(DirectedGraph<? extends WeightedEdge> graph, int v, List<Integer> queue, boolean[] inQ){
		for(WeightedEdge e: graph.adj(v))
			if(disTo[e.w] > disTo[v]+e.weight){
				disTo[e.w] = disTo[v]+e.weight;
				edgeTo[e.w] = v;
				if(!inQ[e.w]){
					queue.add(e.w);
					inQ[e.w] = true;
				}
				if((++cost)%graph.V() == 0)
					findNegativeCircle();
			}
	}
	

	@Override
	public Iterable<Integer> pathTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		if(hasNegativeCircle())
			throw new IllegalStateException("Negative circle found, no shortest path exists!");
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
		if(hasNegativeCircle())
			throw new IllegalStateException("Negative circle found, no shortest path exists!");
		return disTo[w];
	}

	@Override
	public boolean hasPathTo(int w) {
		if(w >= edgeTo.length || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		if(hasNegativeCircle())
			throw new IllegalStateException("Negative circle found, no shortest path exists!");
		return disTo[w] != Double.POSITIVE_INFINITY;
	}
	
	private void findNegativeCircle(){
		DirectedGraph<Edge> g = new DirectedGraph<Edge>(edgeTo.length);
		for(int i=0; i<edgeTo.length; i++){
			if(disTo[i] == Double.POSITIVE_INFINITY) continue;
			if(edgeTo[i] < 0) continue;
			g.addEdge(new Edge(edgeTo[i], i));
		}
		CircleFinder f = new DFSCircleFinder(g);
		if(f.hasCircle())
			negativaCircle = f.circle();
	}

	@Override
	public boolean hasNegativeCircle() {
		return negativaCircle != null;
	}


	@Override
	public Iterable<Integer> negativeCircle() {
		if(!hasNegativeCircle()) return new LinkedList<Integer>();
		return negativaCircle;
	}

}