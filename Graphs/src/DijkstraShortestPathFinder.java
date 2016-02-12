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
		double[] heap = new double[graph.V()+1];
		int[] key = new int[graph.V()+1];
		int[] index = new int[graph.V()];
		int size = 0;
		size = updateKey(heap, key, index, v, 0.0, size);
		while(size > 0)
			size = relax(graph, poll(heap, key, index, size--), heap, key, index, size);
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
	
	private int relax(Graph<? extends WeightedEdge> graph, int v, double[] heap, int[] key, int[] index, int size){
		for(WeightedEdge e: graph.adj(v))
			if(disTo[e.w] > disTo[v]+e.weight){
				size = updateKey(heap, key, index, e.w, disTo[v]+e.weight, size);
				disTo[e.w] = disTo[v] + e.weight;
				edgeTo[e.w] = v;
			}
		return size;
	}
	
	private void swap(double[] heap, int[] key, int[] index, int i, int j){
		double val = heap[i];
		int temp = key[i];
		heap[i] = heap[j];
		key[i] = key[j];
		heap[j] = val;
		key[j] = temp;
		index[key[i]] = i;
		index[key[j]] = j;
	}
	
	private int parent(int n){
		return n/2;
	}
	
	private int left(int n){
		return n*2;
	}
	
	private int right(int n){
		return left(n) + 1;
	}
	
	private void swim(int n, double[] heap, int[] key, int[] index) {
		while(n > 1){
			int parent = parent(n);
			if(heap[parent] > heap[n]){
				swap(heap, key, index, n, parent);
				n = parent;
			}else
				break;
		}
	}
	
	private void sink(int n, double[] heap, int[] key, int[] index, int size){
		while(n <= size){
			int left = left(n);
			int right = right(n);
			double lvalue = left<=size? heap[left]: Double.POSITIVE_INFINITY;
			double rvalue = right<=size? heap[right]: Double.POSITIVE_INFINITY;
			if(heap[n]<=lvalue && heap[n]<=rvalue) break;
			else if(lvalue<rvalue) {
				swap(heap, key, index, n, left);
				n = left;
			} else {
				swap(heap, key, index, n, right);
				n = right;
			}
		}
	}
	
	private int poll(double[] heap, int[] key, int[] index, int size){
		swap(heap, key, index, size, 1);
		sink(1, heap, key, index, size-1);
		index[key[size]] = 0;
		return key[size];
	}
	
	private int updateKey(double[] heap, int[] key, int[] index, int k, double v, int size) {
		if(index[k] == 0){
			index[k] = ++size;
			key[size] = k;
		}
		heap[index[k]] = v;
		swim(index[k], heap, key, index);
		return size;
	}
}
