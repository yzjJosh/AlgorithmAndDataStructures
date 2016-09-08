
public class KDTree3DTest extends GeoSet3DTest {

    @Override
    protected GeoSet getGeoSet() {
        return new KDTree(3);
    }

}
