import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KDTree5DTest extends GeoSet5DTest {

    @Override
    protected GeoSet getGeoSet() {
        return new KDTree(5);
    }
    
    @Test
    public void testDimension() {
        assertEquals(5, set.dimension());
    }

}
