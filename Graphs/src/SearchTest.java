import java.util.Arrays;
import java.util.LinkedList;

import junit.framework.TestCase;

public abstract class SearchTest extends TestCase {
	
	protected abstract Search getSearch(Graph<Edge> g, int v);
	
	public void testDirectedGraph(){
		System.out.println("Testing directed graph ...");
		Graph<Edge> g = new DirectedGraph<Edge>(6);
		g.addEdge(new Edge(0, 1));
		g.addEdge(new Edge(1, 4));
		g.addEdge(new Edge(4, 5));
		g.addEdge(new Edge(2, 1));
		g.addEdge(new Edge(2, 4));
		g.addEdge(new Edge(3, 2));
		g.addEdge(new Edge(3, 4));
		System.out.println("Graph is "+g);
		Search search = getSearch(g, 0);
		assertEquals(4, search.connectedCount());
		for(int v: search.connectedVertices())
			assertTrue(search.isConnected(v));
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addAll(Arrays.asList(new Integer[]{0, 1, 4, 5}));
		for(int v: search.pathTo(5))
			assertEquals(path.pollFirst().intValue(), v);
		assertEquals(0, ((LinkedList<Integer>)search.pathTo(3)).size());
		search = getSearch(g, 3);
		assertEquals(5, search.connectedCount());
		for(int v: search.connectedVertices())
			assertTrue(search.isConnected(v));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{3, 2, 1}));
		for(int v: search.pathTo(1))
			assertEquals(path.pollFirst().intValue(), v);
		assertEquals(0, ((LinkedList<Integer>)search.pathTo(0)).size());
		System.out.println("pass!");
	}
	
	public void testUndirectedGraph(){
		System.out.println("Testing undirected graph ...");
		Graph<Edge> g = new UndirectedGraph<Edge>(7);
		g.addEdge(new Edge(0, 1));
		g.addEdge(new Edge(1, 4));
		g.addEdge(new Edge(4, 5));
		g.addEdge(new Edge(2, 4));
		g.addEdge(new Edge(3, 2));
		g.addEdge(new Edge(3, 4));
		System.out.println("Graph is "+g);
		Search search = getSearch(g, 0);
		assertEquals(6, search.connectedCount());
		for(int v: search.connectedVertices())
			assertTrue(search.isConnected(v));
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addAll(Arrays.asList(new Integer[]{0, 1, 4, 5}));
		for(int v: search.pathTo(5))
			assertEquals(path.pollFirst().intValue(), v);
		assertEquals(0, ((LinkedList<Integer>)search.pathTo(6)).size());
		search = getSearch(g, 6);
		assertEquals(1, search.connectedCount());
		assertEquals(0, ((LinkedList<Integer>)search.pathTo(0)).size());
		search = getSearch(g, 5);
		assertEquals(6, search.connectedCount());
		for(int v: search.connectedVertices())
			assertTrue(search.isConnected(v));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 4, 1}));
		for(int v: search.pathTo(1))
			assertEquals(path.pollFirst().intValue(), v);
		System.out.println("pass!");
	}
	
}
