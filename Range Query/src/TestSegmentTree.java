import static org.junit.Assert.*;

import org.junit.Test;

public abstract class TestSegmentTree extends TestMutableRangeQuery {
    
    @Override
    protected RangeQuery<Long> getRangeQuery(Long[] array) {
        return new SegmentTree<>(getOperation(), array);
    }
    
    @Test
    public void testBuildWithRange() {
        SegmentTree<Long> tree =new SegmentTree<>(getOperation(), 5, 8);
        assertEquals(5, tree.getLo());
        assertEquals(8, tree.getHi());
        for(int i=0; i<10; i++) {
            assertNull(tree.get(i));
            assertNull(tree.get(i, i+1));
        }
    }
    
    @Test
    public void testGetBoundary() {
        SegmentTree<Long> tree = (SegmentTree<Long>) getRangeQuery(array);
        assertEquals(0, tree.getLo());
        assertEquals(array.length-1, tree.getHi());
    }

}
