import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KDTree2DTest extends GeoSet2DTest {

    @Override
    protected GeoSet getGeoSet() {
        return new KDTree(2);
    }
    
    @Test
    public void testDimension() {
        assertEquals(2, set.dimension());
    }

}
