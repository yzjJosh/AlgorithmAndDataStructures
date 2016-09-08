import static org.junit.Assert.*;

import java.util.*;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public abstract class GeoSetTest {
    
    private static GeoSet.Point[] points;
    private static Set<GeoSet.Point> pointSet;
    protected GeoSet set;
    
    protected abstract GeoSet getGeoSet();
    
    protected abstract GeoSet.Point randomPoint(double lo, double hi);
    
    private GeoSet.Point randomPoint() {
        return randomPoint(-100000.0, 100000.0);
    }
    
    private void generatePoints() {
        points = new GeoSet.Point[100000];
        pointSet = new HashSet<>();
        for(int i=0; i<points.length; i++) {
            points[i] = generateNewPoint();
            pointSet.add(points[i]);
        }
    }
    
    private GeoSet.Point generateNewPoint() {
        GeoSet.Point p =null;
        do{
            p = randomPoint();
        }while(pointSet.contains(p));
        return p;
    }

    
    private static GeoSet.Point closestPoint(GeoSet.Point p) {
        GeoSet.Point res= null;
        double dis = Double.POSITIVE_INFINITY;
        for(GeoSet.Point point: points) {
            double temp = point.distanceTo(p);
            if(temp < dis) {
                dis = temp;
                res = point;
            }
        }
        return res;
    }
    
    private static boolean equals(double a, double b) {
        return Math.abs(a-b) < 0.0000001;
    }
    
    private static boolean insideRange(double p, double lo, double hi) {
        return equals(p, lo) || equals(p, hi) || (p > lo && p < hi);
    }
    
    private static Set<GeoSet.Point> range(GeoSet.Point lo, GeoSet.Point hi) {
        Set<GeoSet.Point> set = new HashSet<>();
        for(GeoSet.Point p: points) {
            boolean inside = true;
            for(int i=0; i<p.dimension(); i++) {
                if(!insideRange(p.get(i), lo.get(i), hi.get(i))) {
                    inside = false;
                    break;
                }
            }
            if(inside) {
                set.add(p);
            }
        }
        return set;
    }
    
    private static boolean greater(double a, double b) {
        return !equals(a, b) && a > b;
    }
    
    private static Set<GeoSet.Point> range(GeoSet.Point target, double radius) {
        Set<GeoSet.Point> set = new HashSet<>();
        for(GeoSet.Point p: points) {
            if(!greater(p.distanceTo(target), radius)) {
                set.add(p);
            }
        }
        return set;
    }
    
    @Before
    public void setUp() {
        if(points == null) {
            generatePoints();
        }
        set = getGeoSet();
        for(GeoSet.Point p: points) {
            set.add(p);
        }
    }
    
    @Test
    public void testSize() {
        assertEquals(points.length, set.size());
    }
    
    @Test
    public void testAdd() {
        GeoSet.Point p = generateNewPoint();
        assertFalse(set.contains(p));
        assertTrue(set.add(p));
        assertTrue(set.contains(p));
        assertFalse(set.add(p));
        assertEquals(points.length+1, set.size());
    }
    
    @Test
    public void testContains() {
        for(GeoSet.Point p: points) {
            assertTrue(set.contains(p));
        }
        assertFalse(set.contains(generateNewPoint()));
    }
    
    @Test
    public void testRemove() {
        for(int i=0; i<points.length; i++) {
            assertTrue(set.contains(points[i]));
            assertTrue(set.remove(points[i]));
            assertEquals(points.length-i-1, set.size());
            assertFalse(set.contains(points[i]));
        }
    }
    
    @Test
    public void testClosestPoint() {
        for(int i=0; i<50000; i++) {
            GeoSet.Point p = randomPoint();
            assertEquals(closestPoint(p), set.closestPoint(p));
        }
    }
    
    @Test
    public void testPointsInsideRange() {
        for(int i=0; i<5000; i++) {
            double mid = -100000.0 + Math.random()*200000;
            double width = Math.random() * 50000;
            GeoSet.Point lo = randomPoint(mid-width, mid);
            GeoSet.Point hi = randomPoint(mid+0.1, mid+width);
            Set<GeoSet.Point> points = range(lo, hi);
            int size = 0;
            for(GeoSet.Point p : set.pointsInsideRange(lo, hi)) {
                assertTrue(points.contains(p));
                size ++;
            }
            assertEquals(points.size(), size);
            assertEquals(size, set.numOfPointsInsideRange(lo, hi));
        }
    }
    
    @Test
    public void testPointsInsideRadius() {
        for(int i=0; i<5000; i++) {
            GeoSet.Point target = randomPoint();
            double radius = Math.random() * 100000;
            Set<GeoSet.Point> points = range(target, radius);
            int size = 0;
            for(GeoSet.Point p : set.pointsInsideRange(target, radius)) {
                assertTrue(points.contains(p));
                size ++;
            }
            assertEquals(points.size(), size);
        }
    }
    
    @Test
    public void testIterator() {
        int size = 0;
        Iterator<GeoSet.Point> it = set.iterator();
        while(it.hasNext()) {
            assertTrue(pointSet.contains(it.next()));
            size ++;
        }
       assertEquals(size, pointSet.size());
    }
}
