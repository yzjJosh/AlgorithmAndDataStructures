import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class TestSegmentTree {
    
    private Long[] array;
    private Operation<Long> op;
    
    protected abstract Operation<Long> getOperation();
    
    
    @Before
    public void setUp() {
        array = new Long[100];
        for(int i=0; i<array.length; i++) {
            array[i] = (long)i;
        }
        op = getOperation();
    }
    
    private Long culmulative(int lo, int hi) {
        if(lo > hi) {
            return null;
        }
        Long res = null;
        for(int i=Math.max(0, lo); i<=Math.min(hi, array.length-1); i++) {
            res = op.apply(res, array[i]);
        }
        return res;
    }
    
    @Test
    public void testGetBoundary() {
        SegmentTree<Long> tree = new SegmentTree<>(op, array);
        assertEquals(0, tree.getLo());
        assertEquals(array.length-1, tree.getHi());
    }
    
    @Test
    public void testGetInvalidRange() {
        SegmentTree<Long> tree = new SegmentTree<>(op, array);
        assertNull(tree.get(array.length));
        assertNull(tree.get(-1));
        assertNull(tree.get(array.length, array.length+1));
        assertNull(tree.get(-2, -1));
        assertNull(tree.get(1, 0));
    }
    
    @Test
    public void testGetSingleElement() {
        SegmentTree<Long> tree = new SegmentTree<>(op, array);
        for(int i=0; i<array.length; i++) {
            assertEquals(array[i], tree.get(i));
        }
    }
    
    @Test
    public void testGetInsideRange() {
        SegmentTree<Long> tree = new SegmentTree<>(op, array);
        for(int i=0; i<array.length; i++) {
            for(int j=i; j<array.length; j++) {
                assertEquals(culmulative(i, j), tree.get(i, j));
            }
        }
    }
    
    @Test
    public void testGetPartiallyInsideRange() {
        SegmentTree<Long> tree = new SegmentTree<>(op, array);
        for(int i=-100; i<array.length; i++) {
            assertEquals(culmulative(i, 1000), tree.get(i, 1000));
        }
    }
    
    @Test
    public void testBuildWithRange() {
        SegmentTree<Long> tree =new SegmentTree<>(op, 5, 8);
        assertEquals(5, tree.getLo());
        assertEquals(8, tree.getHi());
        for(int i=0; i<10; i++) {
            assertNull(tree.get(i));
            assertNull(tree.get(i, i+1));
        }
    }
    
    @Test
    public void testUpdate() {
        SegmentTree<Long> tree =new SegmentTree<>(op, array);
        array[0] = -50L;
        tree.update(0, -50L);
        array[10] = 3L;
        tree.update(10, 3L);
        for(int i=0; i<array.length; i++) {
            assertEquals(array[i], tree.get(i));
        }
        for(int i=0; i<array.length; i++) {
            for(int j=i; j<array.length; j++) {
                assertEquals(culmulative(i, j), tree.get(i, j));
            }
        }
        for(int i=-100; i<array.length; i++) {
            assertEquals(culmulative(i, 1000), tree.get(i, 1000));
        }
    }
    
}
