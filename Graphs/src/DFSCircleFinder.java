import java.util.*;

public class DFSCircleFinder implements CircleFinder {
	
	private final LinkedList<Integer> circle;
	
	public DFSCircleFinder(DirectedGraph<?> graph){
		if(graph == null)
			throw new NullPointerException();
		circle = new LinkedList<Integer>();
		int[] edgeTo = new int[graph.V()];
		boolean[] inStack = new boolean[graph.V()];
		Arrays.fill(edgeTo, -1);
		for(int i=0; i<graph.V(); i++){
			if(edgeTo[i] >= 0) continue; 
			if(dfs(graph, i, i, edgeTo, inStack))
				break;
		}
	}
	
	private boolean dfs(DirectedGraph<?> graph, int from, int v, int[] edgeTo, boolean[] inStack){
		if(inStack[v]){
			circle.add(v);
			for(int n=from; n!=v; n=edgeTo[n])
				circle.addFirst(n);
			circle.addFirst(v);
			return true;
		}
		if(edgeTo[v] >= 0) return false;
		boolean res = false;
		inStack[v] = true;
		edgeTo[v] = from;
		for(Edge e: graph.adj(v))
			if(res = dfs(graph, e.v, e.w, edgeTo, inStack))
				break;
		inStack[v] = false;
		return res;
	}
	
	public boolean hasCircle(){
		return !circle.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public Iterable<Integer> circle(){
		return (Iterable<Integer>) circle.clone();
	}
}
