import java.util.*;

public class KDTree implements GeoSet {

    private final static double EPSILON = 0.0000001;
    
    private final double precision;
    private final int k;
    private Node root;

    public KDTree(int k) {
        this(k, EPSILON);
    }

    public KDTree(int k, double precision) {
        if (k <= 0 || precision < 0.0) {
            throw new IllegalArgumentException();
        }
        this.k = k;
        this.precision = precision;
        this.root = null;
    }

    public double getPrecision() {
        return precision;
    }

    @Override
    public boolean add(Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
        if (point.dimension() != k) {
            throw new IllegalArgumentException();
        }
        int preSize = size();
        this.root = add(root, point, 0);
        return size() != preSize;
    }

    private boolean equals(double a, double b) {
        return Math.abs(a - b) < precision;
    }
    
    private boolean greater(double a, double b) {
        return !equals(a, b) && a > b;
    }
    
    private boolean greater(Point a, Point b) {
        for(int i=0; i<k; i++) {
            if(!greater(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean smaller(double a, double b) {
        return !equals(a, b) && a < b;
    }

    private boolean equals(Point a, Point b) {
        for (int i = 0; i < a.dimension(); i++) {
            if (!equals(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }
    
    private Point max(Node node, int dimension) {
        return node == null? null: node.max[dimension];
    }
    
    private Point max(Point a, Point b, int dimension) {
        if(a == null && b == null) {
            return null;
        } else if(a == null) {
            return b;
        } else if(b == null) {
            return a;
        } else {
            return a.get(dimension) >= b.get(dimension)? a: b;
        }
    }
    
    private void fixNodeMetaData(Node node) {
        if(node == null) return;
        node.size = size(node.left) + size(node.right) + 1;
        for(int i=0; i<k; i++) {
            node.max[i] = max(node.point, max(max(node.left, i), max(node.right, i), i), i);
        }
    }

    private Node add(Node root, Point p, int dimension) {
        if (root == null) {
            return new Node(p);
        }
        double cur = root.point.get(dimension);
        double target = p.get(dimension);
        if (equals(root.point, p)) {
            return root;
        } else if (!greater(target, cur)) {
            root.left = add(root.left, p, (dimension + 1) % k);
        } else {
            root.right = add(root.right, p, (dimension + 1) % k);
        }
        fixNodeMetaData(root);
        return root;
    }

    public boolean repOK() {
        return repOK(root, 0);
    }

    private boolean repOK(Node root, int dimension) {
        double[][] range = new double[k][2];
        for (int i = 0; i < k; i++) {
            range[i][0] = Double.NEGATIVE_INFINITY;
            range[i][1] = Double.POSITIVE_INFINITY;
        }
        if(!repOK(root, dimension, range)) {
            System.err.println("Error subtree (start dimension is " + dimension + " ) is: " + toString(root));
            return false;
        } else {
            return true;
        }
    }

    private boolean repOK(Node root, int dimension, double[][] range) {
        if (root == null) {
            return true;
        }
        for (int i = 0; i < k; i++) {
            double val = root.point.get(i);
            if (greater(val, range[i][1]) || !greater(val, range[i][0])) {
                System.err.println("Range of " + i + "th dimension should be " + Arrays.toString(range[i]) + ", " + val
                        + " found!");
                return false;
            }
        }
        double val = root.point.get(dimension);
        if (root.size != size(root.left) + size(root.right) + 1) {
            System.out.println("Size should be " + (size(root.left) + size(root.right) + 1) + ", but " + root.size 
                    + " was found!");
            return false;
        }
        for(int i=0; i<k; i++) {
            Point should = max(root.point, max(max(root.left, i), max(root.right, i), i), i);
            if(root.max[i] != should) {
                System.out.println("The max point on dimension " + i + " should be " + should + ", but found " + root.max[i]);
                return false;
            }
        }
        double temp = range[dimension][1];
        range[dimension][1] = val;
        if (!repOK(root.left, (dimension + 1) % k, range)) {
            return false;
        }
        range[dimension][1] = temp;
        temp = range[dimension][0];
        range[dimension][0] = val;
        if (!repOK(root.right, (dimension + 1) % k, range)) {
            return false;
        }
        range[dimension][0] = temp;
        return true;
    }

    @Override
    public boolean remove(Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
        if (point.dimension() != k) {
            throw new IllegalArgumentException();
        }
        int preSize = size();
        this.root = remove(root, point, 0);
        return size() != preSize;
    }

    private Node remove(Node root, Point p, int dimension) {
        if (root == null) {
            return null;
        }
        double cur = root.point.get(dimension);
        double target = p.get(dimension);
        if (equals(root.point, p)) {
            if (root.left != null) {
                root.point = root.left.max[dimension];
                root.left = remove(root.left, root.point, (dimension + 1) % k);
            } else if(root.right != null) {
                root.point = root.right.max[dimension];
                root.left = remove(root.right, root.point, (dimension + 1) % k);
                root.right = null;
            } else {
                root = null;
            }
        } else if (!greater(target, cur)) {
            root.left = remove(root.left, p, (dimension + 1) % k);
        } else {
            root.right = remove(root.right, p, (dimension + 1) % k);
        }
        fixNodeMetaData(root);
        return root;
    }

    @Override
    public boolean contains(Point point) {
        if (point == null || point.dimension() != k) {
            return false;
        } else {
            return get(root, point, 0) != null;
        }
    }

    private Node get(Node root, Point p, int dimension) {
        if (root == null) {
            return null;
        }
        while(root != null) {
            double cur = root.point.get(dimension);
            double target = p.get(dimension);
            if (equals(root.point, p)) {
                return root;
            } else if (!greater(target, cur)) {
                root = root.left;
            } else {
                root = root.right;
            }
            dimension = (dimension+1)%k;
        }
        return root;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Point closestPoint(Point point) {
        if (point == null) {
            throw new NullPointerException();
        }
        if (point.dimension() != k) {
            throw new IllegalArgumentException();
        }
        return closestPoint(root, 0, smallest(), greatest(), point, Double.POSITIVE_INFINITY);
    }
    
    private Point smallest() {
        double[] values = new double[k];
        Arrays.fill(values, Double.NEGATIVE_INFINITY);
        return new Point(values);
    }
    
    private Point greatest() {
        double[] values = new double[k];
        Arrays.fill(values, Double.POSITIVE_INFINITY);
        return new Point(values);
    }
    
    private double distanceToRange(double p, double lo, double hi) {
        if(!smaller(p, lo) && !greater(p, hi)) {
            return 0.0;
        } else if(p < lo){
            return lo - p;
        } else {
            return p - hi;
        }
    }
    
    private double distanceToRange(Point p, Point lo, Point hi) {
        if(p == null) {
            return Double.POSITIVE_INFINITY;
        }
        double res = 0;
        for(int i=0; i<k; i++) {
            double temp = distanceToRange(p.get(i), lo.get(i), hi.get(i));
            res += temp*temp;
        }
        return res;
    }
    
    private Point leftMidPoint(Point lo, Point hi, Point mid, int dimension) {
        double[] values = new double[k];
        for(int i=0; i<k; i++) {
            values[i] = hi.get(i);
        }
        values[dimension] = mid.get(dimension);
        return new Point(values);
    }
    
    private Point rightMidPoint(Point lo, Point hi, Point mid, int dimension) {
        double[] values = new double[k];
        for(int i=0; i<k; i++) {
            values[i] = lo.get(i);
        }
        values[dimension] = mid.get(dimension);
        return new Point(values);
    }
    
    private Point closestPoint(Node root, int dimension, Point lo, Point hi, Point target, double upperBound) {
        if(root == null) {
            return null;
        }
        Point res = null;
        double dis = root.point.distanceTo(target);
        if(dis < upperBound) {
            upperBound = dis;
            res = root.point;
        }
        Point leftMid = leftMidPoint(lo, hi, root.point, dimension);
        Point rightMid = rightMidPoint(lo, hi, root.point, dimension);
        double leftDis = distanceToRange(target, lo, leftMid);
        double rightDis = distanceToRange(target, rightMid, hi);
        if(leftDis <= rightDis) {
            if(leftDis < upperBound) {
                Point left = closestPoint(root.left, (dimension+1)%k, lo, leftMid, target, upperBound);
                if(left != null) {
                    upperBound = left.distanceTo(target);
                    res = left;
                }
            }
            if(rightDis < upperBound) {
                Point right = closestPoint(root.right, (dimension+1)%k, rightMid, hi, target, upperBound);
                if(right != null) {
                    upperBound = right.distanceTo(target);
                    res = right;
                }
            }
        } else {
            if(rightDis < upperBound) {
                Point right = closestPoint(root.right, (dimension+1)%k, rightMid, hi, target, upperBound);
                if(right != null) {
                    upperBound = right.distanceTo(target);
                    res = right;
                }
            }
            if(leftDis < upperBound) {
                Point left = closestPoint(root.left, (dimension+1)%k, lo, leftMid, target, upperBound);
                if(left != null) {
                    upperBound = left.distanceTo(target);
                    res = left;
                }
            }
        }
        return res;
    }

    @Override
    public Iterable<Point> pointsInsideRange(Point lo, Point hi) {
        if (lo == null || hi == null) {
            throw new NullPointerException();
        }
        if (lo.dimension() != k || hi.dimension() != k || greater(lo, hi)) {
            throw new IllegalArgumentException();
        }
        
        List<Point> res = new LinkedList<>();
        collectPoints(root, 0, lo, hi, res);
        return res;
    }
    
    private boolean insideRange(double p, double lo, double hi) {
        return !smaller(p, lo) && !greater(p, hi);
    }
    
    private boolean insideRange(Point p, Point lo, Point hi) {
        for(int i=0; i<k; i++) {
            if(!insideRange(p.get(i), lo.get(i), hi.get(i))) {
                return false;
            }
        }
        return true;
    }
    
    private void collectPoints(Node root, int dimension, Point lo, Point hi, List<Point> res) {
        if(root == null) {
            return;
        }
        if(insideRange(root.point, lo, hi)) {
            res.add(root.point);
        }
        if(!smaller(root.point.get(dimension), lo.get(dimension))) {
            collectPoints(root.left, (dimension+1)%k, lo, hi, res);
        }
        if(!greater(root.point.get(dimension), hi.get(dimension))) {
            collectPoints(root.right, (dimension+1)%k, lo, hi, res);
        }
    }
    
    public Iterable<Point> pointsInsideRange(Point point, double radius) {
        if (point == null) {
            throw new NullPointerException();
        }
        if (point.dimension() != k || radius < 0.0) {
            throw new IllegalArgumentException();
        }
        List<Point> res = new LinkedList<>();
        collectPoints(root, 0, smallest(), greatest(), point, radius, res);
        return res;
    }
    
    
    private void collectPoints(Node root, int dimension, Point lo, Point hi, Point p, double radius, List<Point> res) {
        if(root == null) {
            return;
        }
        if(!greater(root.point.distanceTo(p), radius)) {
            res.add(root.point);
        }
        Point leftMid = leftMidPoint(lo, hi, root.point, dimension);
        Point rightMid = rightMidPoint(lo, hi, root.point, dimension);
        double leftDis = distanceToRange(p, lo, leftMid);
        double rightDis = distanceToRange(p, rightMid, hi);
        if(!greater(leftDis, radius)) {
            collectPoints(root.left, (dimension+1)%k, lo, leftMid, p, radius, res);
        }
        if(!greater(rightDis, radius)) {
            collectPoints(root.right, (dimension+1)%k, rightMid, hi, p, radius, res);
        }
    }

    @Override
    public int numOfPointsInsideRange(Point lo, Point hi) {
        if (lo == null || hi == null) {
            throw new NullPointerException();
        }
        if (lo.dimension() != k || hi.dimension() != k || greater(lo, hi)) {
            throw new IllegalArgumentException();
        }
        return size(root) - numOfPointsOutsideRange(root, 0, lo, hi);
    }
    
    private int numOfPointsOutsideRange(Node root, int dimension, Point lo, Point hi) {
        if(root == null) {
            return 0;
        }
        double loVal = lo.get(dimension);
        double hiVal = hi.get(dimension);
        double cur = root.point.get(dimension);
        int left = smaller(cur, loVal)? size(root.left): numOfPointsOutsideRange(root.left, (dimension+1)%k, lo, hi);
        int right = !smaller(cur, hiVal)? size(root.right): numOfPointsOutsideRange(root.right, (dimension+1)%k, lo, hi);
        return left + right + (insideRange(root.point, lo, hi)? 0: 1);
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public int dimension() {
        return k;
    }

    private void collectPoints(Node root, List<Point> res) {
        if(root == null) {
            return;
        }
        collectPoints(root.left, res);
        res.add(root.point);
        collectPoints(root.right, res);
    }
    
    @Override
    public Iterator<Point> iterator() {
        List<Point> list = new LinkedList<>();
        collectPoints(root, list);
        return list.iterator();
    }

    private String toString(int depth, Node node) {
        if (node == null) {
            return "";
        }
        String blank = "";
        for (int i = 0; i < depth; i++) {
            blank += "    |";
        }
        return "\n" + blank + node + toString(depth + 1, node.left) + toString(depth + 1, node.right);
    }
    
    private String toString(Node node) {
        return "\n{" + toString(0, root) + "\n}";
    }

    @Override
    public String toString() {
        return toString(root);
    }

    private class Node {
        public Point point;
        public Node left;
        public Node right;
        public Point[] max;
        public int size;

        public Node(Point point) {
            this(point, null, null);
        }

        public Node(Point point, Node left, Node right) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.max = new Point[k];
            this.size = 1;
            for(int i=0; i<k; i++) {
                this.max[i] = point;
            }
        }

        @Override
        public String toString() {
            return point.toString();
        }
    }
}
