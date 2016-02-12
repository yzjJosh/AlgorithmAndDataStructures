import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class TopologicalSortTest {
	
	@Test public void test0(){
		DirectedGraph<Edge> graph = new DirectedGraph<Edge>(100);
		assertTrue(isTopologicalOrder(graph, new TopologicalSort(graph).getOrder()));
	}
	
	@Test public void test1(){
		for(int k=0; k<10; k++){
			DirectedGraph<Edge> graph = new DirectedGraph<Edge>(100);
			for(int i=0; i<graph.V()-1; i++){
				int to = i+1+(int)Math.random()*(graph.V()-i-2);
				graph.addEdge(new Edge(i, to));
			}
			assertTrue(isTopologicalOrder(graph, new TopologicalSort(graph).getOrder()));
		}
	}
	
	@Test public void test2(){
		DirectedGraph<Edge> graph = new DirectedGraph<Edge>(5);
		graph.addEdge(new Edge(0, 1));
		graph.addEdge(new Edge(0, 3));
		graph.addEdge(new Edge(1, 3));
		graph.addEdge(new Edge(0, 2));
		graph.addEdge(new Edge(2, 4));
		graph.addEdge(new Edge(2, 3));
		graph.addEdge(new Edge(4, 3));
		assertTrue(isTopologicalOrder(graph, new TopologicalSort(graph).getOrder()));
	}
	
	@SuppressWarnings("unchecked")
	private boolean isTopologicalOrder(DirectedGraph<?> graph, Iterable<Integer> order){
		Set<Integer>[] predecesors = (Set<Integer>[])new Set[graph.V()];
		for(int i=0; i<graph.V(); i++)
			predecesors[i] = new HashSet<Integer>();
		for(Edge e: graph.edges())
			predecesors[e.w].add(e.v);
		boolean[] visited = new boolean[graph.V()];
		for(int v: order){
			for(int predecessor: predecesors[v])
				if(!visited[predecessor])
					return false;
			visited[v] = true;
		}
		return true;
	}
	
}
