import java.util.LinkedList;

public class DirectedGraph implements Graph {
	
	private LinkedList<Integer>[] adjList;
	private int E;
	
	@SuppressWarnings("unchecked")
	public DirectedGraph(int V){
		if(V <= 0) 
			throw new IllegalArgumentException(V+"");
		E = 0;
		adjList = (LinkedList<Integer>[])new LinkedList[V];
		for(int i=0; i<V; i++)
			adjList[i] = new LinkedList<Integer>();
	}

	@Override
	public int V() {
		return adjList.length;
	}

	@Override
	public int E() {
		return E;
	}

	@Override
	public void addEdge(int v, int w) {
		if(v >= V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		if(w >= V() || w < 0)
			throw new IndexOutOfBoundsException(w+"");
		adjList[v].add(w);
		E ++;
	}

	@Override
	public Iterable<Integer> adj(int v) {
		if(v >= V() || v < 0)
			throw new IndexOutOfBoundsException(v+"");
		return adjList[v];
	}
	
	@Override
	public String toString(){
		String s = V() + " vertices, " + E() + " edges\n";
		for (int v = 0; v < V(); v++)
		{
			s += v + ": ";
			for (int w : adj(v))
				s += w + " ";
			s += "\n";
		}
		return s;
	}

}
