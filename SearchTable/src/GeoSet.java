import java.util.Arrays;
import java.util.List;

public interface GeoSet extends Iterable<GeoSet.Point>{
    
    public boolean add(Point point);
    
    public boolean remove(Point point);
    
    public boolean contains(Point point);
    
    public boolean isEmpty();
    
    public Point closestPoint(Point point);
    
    public Iterable<Point> pointsInsideRange(Point lo, Point hi);
    
    public Iterable<Point> pointsInsideRange(Point p, double radius);
    
    public int numOfPointsInsideRange(Point lo, Point hi);
    
    public int size();
    
    public int dimension();
    
    public static class Point implements Cloneable{
        
        private final double[] values;
        
        public Point(double[] point) {
            if(point == null) {
                throw new NullPointerException();
            }
            this.values = new double[point.length];
            for(int i=0; i<values.length; i++) {
                values[i] = point[i];
            }
        }
        
        public Point(List<Double> point) {
            if(point == null) {
                throw new NullPointerException();
            }
            this.values = new double[point.size()];
            int i = 0;
            for(double v: point) {
                values[i++] = v;
            }
        }
        
        public Point(Point point) {
            if(point == null) {
                throw new NullPointerException();
            }
            this.values = point.values;
        }
        
        public int dimension() {
            return values.length;
        }
        
        public double get(int i) {
            if(i < 0 || i >= values.length) {
                throw new IndexOutOfBoundsException(String.valueOf(i));
            }
            return values[i];
        }
        
        public double distanceTo(Point p) {
            if(p == null) {
                throw new NullPointerException();
            }
            if(p.dimension() != this.dimension()) {
                throw new IllegalArgumentException();
            }
            double res = 0;
            for(int i=0; i<values.length; i++) {
                double temp = values[i] - p.values[i];
                res += temp * temp;
            }
            return res;
        }
        
        @Override
        public Object clone() {
            return new Point(this);
        }
        
        @Override
        public String toString() {
            return Arrays.toString(values);
        }
        
        @Override
        public boolean equals(Object o) {
            if(o == null) {
                return false;
            }
            if(!(o instanceof Point)) {
                return false;
            }
            Point p = (Point)o;
            return Arrays.equals(this.values, p.values);
        }
        
        @Override
        public int hashCode() {
            return Arrays.hashCode(values);
        }
    }
    
}
