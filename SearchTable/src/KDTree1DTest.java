import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KDTree1DTest extends GeoSet1DTest {

    @Override
    protected GeoSet getGeoSet() {
        return new KDTree(1);
    }
    
    @Test
    public void testDimension() {
        assertEquals(1, set.dimension());
    }

}
