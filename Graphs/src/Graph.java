
public interface Graph<E extends Edge> {
	
	public int V();
	
	public int E();
	
	public void addEdge(E e);
	
	public Iterable<E> adj(int v);
	
}
