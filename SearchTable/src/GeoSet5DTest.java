
public abstract class GeoSet5DTest extends GeoSetTest{

    @Override
    protected GeoSet.Point randomPoint(double lo, double hi) {
        double[] values = new double[5];
        values[0] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[1] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[2] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[3] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        values[4] = lo + (double)((int)(Math.random()*(hi-lo)*1000))/1000;
        return new GeoSet.Point(values);
    }
}
