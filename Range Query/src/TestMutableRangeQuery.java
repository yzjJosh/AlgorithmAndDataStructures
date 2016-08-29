import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class TestMutableRangeQuery extends TestRangeQuery{
    
    @Test
    public void testUpdate() {
        MutableRangeQuery<Long> rq = (MutableRangeQuery<Long>) getRangeQuery(array);
        array[0] = -50L;
        rq.update(0, -50L);
        array[10] = 3L;
        rq.update(10, 3L);
        for(int i=0; i<array.length; i++) {
            assertEquals(array[i], rq.get(i));
        }
        for(int i=0; i<array.length; i++) {
            for(int j=i; j<array.length; j++) {
                assertEquals(culmulative(i, j), rq.get(i, j));
            }
        }
        for(int i=-100; i<array.length; i++) {
            assertEquals(culmulative(i, 1000), rq.get(i, 1000));
        }
    }
    
}
