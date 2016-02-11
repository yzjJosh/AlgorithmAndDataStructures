
public interface Search {
	
	public boolean isConnected(int w);
	
	public int connectedCount();
	
	public Iterable<Integer> connectedVertices();
	
	public Iterable<Integer> pathTo(int w);
	
}
