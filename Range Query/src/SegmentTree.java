import lombok.Getter;
import lombok.NonNull;

public class SegmentTree<T> implements MutableRangeQuery<T> {
    
    private final Node root;
    @Getter
    private final int lo;
    @Getter
    private final int hi;
    @Getter
    private final Operation<T> op;
    
    public SegmentTree(@NonNull Operation<T> op, int n) {
        this(op, 0, n-1);
    }
    
    public SegmentTree(@NonNull Operation<T> op, int lo, int hi) {
        if(lo > hi) {
            throw new IllegalArgumentException("lo: " + lo + ", hi: " + hi);
        }
        this.op = op;
        this.lo = lo;
        this.hi = hi;
        this.root = buildTree(null, lo, hi);
    }
    
    public SegmentTree(@NonNull Operation<T> op, @NonNull T[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("Receive empty array!");
        }
        this.op = op;
        this.lo = 0;
        this.hi = array.length-1;
        this.root = buildTree(array, lo, hi);
    }
    
    private Node buildTree(T[] array, int lo, int hi) {
        if(lo == hi) {
            return new Node(array == null? null: array[lo]);
        }
        int mid = lo + (hi-lo)/2;
        Node left = buildTree(array, lo, mid);
        Node right = buildTree(array, mid+1, hi);
        Node res = new Node(op.apply(left.value, right.value));
        res.left = left;
        res.right = right;
        return res;
    }
    
    @Override
    public T get(int i) {
        return get(i, i);
    }
    
    @Override
    public T get(int lo, int hi) {
        return get(root, this.lo, this.hi, lo, hi);
    }
    
    private T get(Node node, int lo, int hi, int targetLo, int targetHi) {
        if(lo > hi || targetLo > targetHi || targetHi < lo || targetLo > hi) {
            return null;
        }else if(targetLo <= lo && targetHi >= hi) {
            return node.value;
        } else {
            int mid = lo + (hi-lo)/2;
            return op.apply(get(node.left, lo, mid, targetLo, targetHi), get(node.right, mid+1, hi, targetLo, targetHi));
        }
    }
    
    @Override
    public void update(int i, T value) {
        update(root, lo, hi, i, value);
    }
    
    private void update(Node node, int lo, int hi, int i, T value) {
        if (i == lo && i == hi) {
            node.value = value;
        } else if(i >= lo && i <= hi) {
            int mid = lo + (hi-lo)/2;
            if(i <= mid) {
                update(node.left, lo, mid, i, value);
            } else {
                update(node.right, mid+1, hi, i, value);
            }
            node.value = op.apply(node.left.value, node.right.value);
        }
    }
    
    private class Node {
        public T value;
        public Node left;
        public Node right;
        
        public Node(T value) {
            this.value = value;
        }
    }
    
}
