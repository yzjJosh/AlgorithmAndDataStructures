
public abstract class GeoSet2DTest extends GeoSetTest{

    @Override
    protected GeoSet.Point randomPoint(double lo, double hi) {
        double[] values = new double[2];
        values[0] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[1] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        return new GeoSet.Point(values);
    }
}
