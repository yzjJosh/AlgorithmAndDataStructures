
public class MaxSparseTable<T extends Comparable<T>> extends SparseTable<T>{

    public MaxSparseTable(T[] array) {
        super(new Max<T>(), array);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public T get(int lo, int hi) {
        lo = Math.max(getLo(), lo);
        hi = Math.min(getHi(), hi);
        if(lo > hi) {
            return null;
        }
        int log = logFloor(hi-lo+1);
        return getOp().apply((T)table[lo][log], (T)table[hi-(1<<log)+1][log]);
    }
}
