import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class GeoSet3DTest extends GeoSetTest{

    @Override
    protected GeoSet.Point randomPoint(double lo, double hi) {
        double[] values = new double[3];
        values[0] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[1] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[2] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        return new GeoSet.Point(values);
    }
    
    @Test
    public void testDimension() {
        assertEquals(3, set.dimension());
    }
}
