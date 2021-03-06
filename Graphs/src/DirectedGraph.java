import java.util.LinkedList;

public class DirectedGraph<E extends Edge> implements Graph<E> {
	
	private LinkedList<Edge>[] adjList;
	private LinkedList<Edge> edges;
	
	@SuppressWarnings("unchecked")
	public DirectedGraph(int V){
		if(V <= 0) 
			throw new IllegalArgumentException(V+"");
		adjList = (LinkedList<Edge>[])new LinkedList[V];
		edges = new LinkedList<Edge>();
		for(int i=0; i<V; i++)
			adjList[i] = new LinkedList<Edge>();
	}

	@Override
	public int V() {
		return adjList.length;
	}

	@Override
	public int E() {
		return edges.size();
	}

	@Override
	public void addEdge(E e) {
		if(e == null) throw  new NullPointerException();
		if(e.v >= V() || e.v < 0)
			throw new IndexOutOfBoundsException(e.v+"");
		if(e.w >= V() || e.w < 0)
			throw new IndexOutOfBoundsException(e.w+"");
		adjList[e.v].add(e);
		edges.add(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<E> adj(int v) {
		if(v >= V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		return (Iterable<E>)adjList[v].clone();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<E> edges() {
		return (Iterable<E>) edges.clone();
	}
	
	@Override
	public String toString(){
		String s = V() + " vertices, " + E() + " edges\n";
		for (int v = 0; v < V(); v++){
			s += v + ": ";
			for (Edge e : adj(v))
				s += e + " ";
			s += "\n";
		}
		return s;
	}

}
