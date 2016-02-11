import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class CircleFinderTest {
	
	@Test public void test0(){
		DirectedGraph<Edge> g = new DirectedGraph<Edge>(7);
		CircleFinder finder = new CircleFinder(g);
		assertFalse(finder.hasCircle());
		assertTrue(((List<Integer>)finder.circle()).isEmpty());
		g.addEdge(new Edge(1, 0));
		g.addEdge(new Edge(1, 5));
		g.addEdge(new Edge(5, 3));
		finder = new CircleFinder(g);
		assertFalse(finder.hasCircle());
		assertTrue(((List<Integer>)finder.circle()).isEmpty());
		g.addEdge(new Edge(3, 1));
		finder = new CircleFinder(g);
		assertTrue(finder.hasCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		circle.addAll(Arrays.asList(new Integer[]{1,5,3,1}));
		assertEquals(circle, finder.circle());
	}
	
	@Test public void test1(){
		DirectedGraph<Edge> g = new DirectedGraph<Edge>(3);
		g.addEdge(new Edge(2, 2));
		CircleFinder finder = new CircleFinder(g);
		assertTrue(finder.hasCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		circle.addAll(Arrays.asList(new Integer[]{2, 2}));
		assertEquals(circle, finder.circle());
		g.addEdge(new Edge(0, 1));
		g.addEdge(new Edge(1, 0));
		finder = new CircleFinder(g);
		assertTrue(finder.hasCircle());
		circle.clear();
		circle.addAll(Arrays.asList(new Integer[]{0, 1, 0}));
		assertEquals(circle, finder.circle());
	}
}
