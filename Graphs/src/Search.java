
public abstract class Search {
	
	public Search(Graph<?> graph, int v){}
	
	public abstract boolean isConnected(int w);
	
	public abstract int connectedCount();
	
	public abstract Iterable<Integer> connectedVertices();
	
	public abstract Iterable<Integer> pathTo(int w);
	
}
