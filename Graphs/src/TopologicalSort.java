import java.util.*;

public class TopologicalSort {
	
	private LinkedList<Integer> order;
	
	public TopologicalSort(DirectedGraph<?> graph){
		if(graph == null)
			throw new NullPointerException();
		if(new DFSCircleFinder(graph).hasCircle())
			throw new IllegalArgumentException("Graph is not a DAG!");
		order = new LinkedList<Integer>();
		boolean[] visited = new boolean[graph.V()];
		for(int i=0; i<graph.V(); i++)
			dfs(graph, i, visited);
	}
	
	private void dfs(DirectedGraph<?> graph, int n, boolean[] visited){
		if(visited[n]) return;
		visited[n] = true;
		for(Edge e: graph.adj(n))
			dfs(graph, e.w, visited);
		order.addFirst(n);
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<Integer> getOrder(){
		return (Iterable<Integer>) order.clone();
	}
}
