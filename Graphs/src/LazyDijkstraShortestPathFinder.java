import java.util.Arrays;
import java.util.LinkedList;

public class LazyDijkstraShortestPathFinder implements ShortestPathFinder {

    private final int v;
    private final double[] disTo;
    private final int[] edgeTo;

    public LazyDijkstraShortestPathFinder(DirectedGraph<? extends WeightedEdge> graph, int v) {
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
        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>(true);
        pq.add(new WeightedEdge(v, v, 0.0));
        while(!pq.isEmpty()) {
            WeightedEdge cur = pq.poll();
            if(edgeTo[cur.w] != -1) {
                continue;
            }
            edgeTo[cur.w] = cur.v;
            disTo[cur.w] = cur.weight;
            for(WeightedEdge e: graph.adj(cur.w)) {
                if(edgeTo[e.w] == -1) {
                    pq.add(new WeightedEdge(e.v, e.w, cur.weight + e.weight));
                }
            }
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
