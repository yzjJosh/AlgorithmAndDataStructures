import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

public class DAGLPTest {
	
	private final static double EPSILON = 0.001;
	
	@Test public void test0(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(3);
		g.addEdge(new WeightedEdge(0, 1, -3.9));
		g.addEdge(new WeightedEdge(1, 0, -1.0));
		try{
			new DAGLongestPathFinder(g, 0);
			assertTrue(false);
		}catch(IllegalArgumentException e){}
	}
	
	@Test public void test1(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(4);
		g.addEdge(new WeightedEdge(0, 1, 0.3));
		g.addEdge(new WeightedEdge(0, 2, 1.5));
		g.addEdge(new WeightedEdge(2, 1, -2.0));
		g.addEdge(new WeightedEdge(1, 3, 0.4));
		LongestPathFinder f = new DAGLongestPathFinder(g, 0);
		assertEquals(0.0, f.distanceTo(0), EPSILON);
		assertEquals(0.3, f.distanceTo(1), EPSILON);
		assertEquals(1.5, f.distanceTo(2), EPSILON);
		assertEquals(0.7, f.distanceTo(3), EPSILON);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.add(0);
		assertEquals(path, f.pathTo(0));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 1}));
		assertEquals(path, f.pathTo(1));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 2}));
		assertEquals(path, f.pathTo(2));
		path.clear();
		path.addAll(Arrays.asList(new Integer[]{0, 1, 3}));
		assertEquals(path, f.pathTo(3));
		for(int i=0; i<g.V(); i++)
			assertTrue(f.hasPathTo(i));
	}
	
	@Test public void test2(){
		DirectedGraph<WeightedEdge> g = new DirectedGraph<WeightedEdge>(2);
		LongestPathFinder f = new DAGLongestPathFinder(g, 0);
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
