import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class TestRangeQuery {
    
    protected Long[] array;
    protected Operation<Long> op;
    
    protected abstract Operation<Long> getOperation();
    
    protected abstract RangeQuery<Long> getRangeQuery(Long[] array);
    
    protected Long culmulative(int lo, int hi) {
        if(lo > hi) {
            return null;
        }
        Long res = null;
        for(int i=Math.max(0, lo); i<=Math.min(hi, array.length-1); i++) {
            res = op.apply(res, array[i]);
        }
        return res;
    }
    
    @Before
    public void setUp() {
        array = new Long[100];
        for(int i=0; i<array.length; i++) {
            array[i] = (long)i;
        }
        op = getOperation();
    }
    
    
    @Test
    public void testGetInvalidRange() {
        RangeQuery<Long> rq = getRangeQuery(array);
        assertNull(rq.get(array.length));
        assertNull(rq.get(-1));
        assertNull(rq.get(array.length, array.length+1));
        assertNull(rq.get(-2, -1));
        assertNull(rq.get(1, 0));
    }
    
    @Test
    public void testGetSingleElement() {
        RangeQuery<Long> rq = getRangeQuery(array);
        for(int i=0; i<array.length; i++) {
            assertEquals(array[i], rq.get(i));
        }
    }
    
    @Test
    public void testGetInsideRange() {
        RangeQuery<Long> rq = getRangeQuery(array);
        for(int i=0; i<array.length; i++) {
            for(int j=i; j<array.length; j++) {
                assertEquals(culmulative(i, j), rq.get(i, j));
            }
        }
    }
    
    @Test
    public void testGetPartiallyInsideRange() {
        RangeQuery<Long> rq = getRangeQuery(array);
        for(int i=-100; i<array.length; i++) {
            assertEquals(culmulative(i, 1000), rq.get(i, 1000));
        }
    }
    
}
