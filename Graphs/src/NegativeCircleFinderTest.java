import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class NegativeCircleFinderTest {
	
	@Test public void test0(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(2);
		g.addEdge(new WeightedEdge(0, 1, 0.0));
		g.addEdge(new WeightedEdge(1, 0, 0.0));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertFalse(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test1(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(2);
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertFalse(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test2(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(1);
		g.addEdge(new WeightedEdge(0, 0, -1.0));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertTrue(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		circle.addAll(Arrays.asList(new Integer[]{0, 0}));
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test3(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(4);
		g.addEdge(new WeightedEdge(0, 1, 3.9));
		g.addEdge(new WeightedEdge(1, 2, -1.0));
		g.addEdge(new WeightedEdge(2, 3, 1.2));
		g.addEdge(new WeightedEdge(3, 0, -7.0));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertTrue(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		circle.addAll(Arrays.asList(new Integer[]{0, 1, 2, 3, 0}));
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test4(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(4);
		g.addEdge(new WeightedEdge(3, 2, 3.5));
		g.addEdge(new WeightedEdge(2, 3, -3.6));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertTrue(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		circle.addAll(Arrays.asList(new Integer[]{2, 3, 2}));
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test5(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(8);
		g.addEdge(new WeightedEdge(4, 5, 0.35));
		g.addEdge(new WeightedEdge(5, 4, 0.35));
		g.addEdge(new WeightedEdge(4, 7, 0.37));
		g.addEdge(new WeightedEdge(5, 7, 0.28));
		g.addEdge(new WeightedEdge(7, 5, 0.28));
		g.addEdge(new WeightedEdge(5, 1, 0.32));
		g.addEdge(new WeightedEdge(0, 4, 0.38));
		g.addEdge(new WeightedEdge(0, 2, 0.26));
		g.addEdge(new WeightedEdge(7, 3, 0.39));
		g.addEdge(new WeightedEdge(1, 3, 0.29));
		g.addEdge(new WeightedEdge(2, 7, 0.34));
		g.addEdge(new WeightedEdge(6, 2, 0.40));
		g.addEdge(new WeightedEdge(3, 6, 0.52));
		g.addEdge(new WeightedEdge(6, 0, 0.58));
		g.addEdge(new WeightedEdge(6, 4, 0.93));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertFalse(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		assertEquals(circle, f.negativeCircle());
	}
	
	@Test public void test6(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(8);
		g.addEdge(new WeightedEdge(5, 4, 0.35));
		g.addEdge(new WeightedEdge(4, 7, 0.37));
		g.addEdge(new WeightedEdge(5, 7, 0.28));
		g.addEdge(new WeightedEdge(5, 1, 0.32));
		g.addEdge(new WeightedEdge(4, 0, 0.38));
		g.addEdge(new WeightedEdge(0, 2, 0.26));
		g.addEdge(new WeightedEdge(3, 7, 0.39));
		g.addEdge(new WeightedEdge(1, 3, 0.29));
		g.addEdge(new WeightedEdge(7, 2, 0.34));
		g.addEdge(new WeightedEdge(6, 2, 0.40));
		g.addEdge(new WeightedEdge(3, 6, 0.52));
		g.addEdge(new WeightedEdge(6, 0, 0.58));
		g.addEdge(new WeightedEdge(6, 4, 0.93));
		NegativeCircleFinder f = new BFNegativeCircleFinder(g);
		assertFalse(f.hasNegativeCircle());
		LinkedList<Integer> circle = new LinkedList<Integer>();
		assertEquals(circle, f.negativeCircle());
	}
}
