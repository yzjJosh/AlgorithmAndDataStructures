import junit.framework.TestCase;

public class QuickUnionTest extends TestCase {
	
	public void testQuickUnion(){
		WeightedQuickUnion qu = new WeightedQuickUnion(10);
		assertEquals(qu.partsNum(), 10);
		qu.connect(0, 1);
		assertEquals(qu.partsNum(), 9);
		assertTrue(qu.isConnected(0, 1));
		qu.connect(0, 5);
		assertEquals(qu.partsNum(), 8);
		assertTrue(qu.isConnected(1, 5));
		assertFalse(qu.isConnected(0, 2));
		qu.connect(0, 1);
		assertEquals(qu.partsNum(), 8);
		qu.connect(5, 2);
		assertEquals(qu.partsNum(), 7);
		assertTrue(qu.isConnected(0, 2));
	}

}
