
public abstract class GeoSet1DTest extends GeoSetTest{

    @Override
    protected GeoSet.Point randomPoint(double lo, double hi) {
        double[] values = new double[1];
        values[0] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        return new GeoSet.Point(values);
    }
}
