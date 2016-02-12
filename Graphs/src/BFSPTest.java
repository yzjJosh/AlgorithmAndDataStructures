import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

public class BFSPTest {
	
	private final static double EPSILON = 0.001;
	
	@Test public void test0(){
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
		ShortestPathFinder f = new BFShortestPathFinder(g, 0);
		assertEquals(0.0, f.distanceTo(0), EPSILON);
		assertEquals(1.05, f.distanceTo(1), EPSILON);
		assertEquals(0.26, f.distanceTo(2), EPSILON);
		assertEquals(0.99, f.distanceTo(3), EPSILON);
		assertEquals(0.38, f.distanceTo(4), EPSILON);
		assertEquals(0.73, f.distanceTo(5), EPSILON);
		assertEquals(1.51, f.distanceTo(6), EPSILON);
		assertEquals(0.60, f.distanceTo(7), EPSILON);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(0);
		assertEquals(path, f.pathTo(0));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 4, 5, 1}));
		assertEquals(path, f.pathTo(1));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2}));
		assertEquals(path, f.pathTo(2));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2, 7, 3}));
		assertEquals(path, f.pathTo(3));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 4}));
		assertEquals(path, f.pathTo(4));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 4, 5}));
		assertEquals(path, f.pathTo(5));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2, 7, 3, 6}));
		assertEquals(path, f.pathTo(6));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2, 7}));
		assertEquals(path, f.pathTo(7));
		for(int i=0; i<g.V(); i++)
			assertTrue(f.hasPathTo(i));
	}
	
	@Test public void test1(){
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
		ShortestPathFinder f = new BFShortestPathFinder(g, 5);
		assertEquals(0.73, f.distanceTo(0), EPSILON);
		assertEquals(0.32, f.distanceTo(1), EPSILON);
		assertEquals(0.62, f.distanceTo(2), EPSILON);
		assertEquals(0.61, f.distanceTo(3), EPSILON);
		assertEquals(0.35, f.distanceTo(4), EPSILON);
		assertEquals(0.00, f.distanceTo(5), EPSILON);
		assertEquals(1.13, f.distanceTo(6), EPSILON);
		assertEquals(0.28, f.distanceTo(7), EPSILON);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addAll(Arrays.asList(new Integer[]{5, 4, 0}));
		assertEquals(path, f.pathTo(0));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 1}));
		assertEquals(path, f.pathTo(1));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 7, 2}));
		assertEquals(path, f.pathTo(2));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 1, 3}));
		assertEquals(path, f.pathTo(3));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 4}));
		assertEquals(path, f.pathTo(4));
		path.clear();
		path.add(5);
		assertEquals(path, f.pathTo(5));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 1, 3, 6}));
		assertEquals(path, f.pathTo(6));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{5, 7}));
		assertEquals(path, f.pathTo(7));
		for(int i=0; i<g.V(); i++)
			assertTrue(f.hasPathTo(i));
	}
	
	@Test public void test2(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(4);
		g.addEdge(new WeightedEdge(0, 1, 0.3));
		g.addEdge(new WeightedEdge(0, 2, 1.5));
		g.addEdge(new WeightedEdge(2, 1, -2.0));
		g.addEdge(new WeightedEdge(1, 3, 0.4));
		ShortestPathFinder f = new BFShortestPathFinder(g, 0);
		assertEquals(0.0, f.distanceTo(0), EPSILON);
		assertEquals(-0.5, f.distanceTo(1), EPSILON);
		assertEquals(1.5, f.distanceTo(2), EPSILON);
		assertEquals(-0.1, f.distanceTo(3), EPSILON);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(0);
		assertEquals(path, f.pathTo(0));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2, 1}));
		assertEquals(path, f.pathTo(1));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2}));
		assertEquals(path, f.pathTo(2));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2, 1, 3}));
		assertEquals(path, f.pathTo(3));
		for(int i=0; i<g.V(); i++)
			assertTrue(f.hasPathTo(i));
	}
	
	@Test public void test3(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(2);
		ShortestPathFinder f = new BFShortestPathFinder(g, 0);
		assertEquals(0.0, f.distanceTo(0), EPSILON);
		assertEquals(Double.POSITIVE_INFINITY, f.distanceTo(1), EPSILON);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(0);
		assertEquals(path, f.pathTo(0));
		path.clear();
		assertEquals(path, f.pathTo(1));
		assertTrue(f.hasPathTo(0));
		assertFalse(f.hasPathTo(1));
	}
}
