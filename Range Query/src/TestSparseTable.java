import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class TestSparseTable extends TestRangeQuery {

    @Test
    public void testGetBoundary() {
        SparseTable<Long> tree = (SparseTable<Long>) getRangeQuery(array);
        assertEquals(0, tree.getLo());
        assertEquals(array.length-1, tree.getHi());
    }
    
}
