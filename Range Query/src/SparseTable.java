import lombok.Getter;
import lombok.NonNull;

@SuppressWarnings("unchecked")
public class SparseTable<T> implements RangeQuery<T> {
    
    protected final Object[][] table;
    @Getter
    private final Operation<T> op;
    @Getter
    private final int lo;
    @Getter
    private final int hi;
    
    public SparseTable(@NonNull Operation<T> op, @NonNull T[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("Receive empty array!");
        }
        this.op = op;
        this.lo = 0;
        this.hi = array.length-1;
        this.table = (T[][]) new Object[array.length][logCeiling(array.length)];
        for(int i=table.length-1; i>=0; i--) {
            table[i][0] = array[i];
            for(int j=1; i+(1<<j) <= table.length; j++) {
                table[i][j] = op.apply((T)table[i][j-1], (T)table[i+(1<<(j-1))][j-1]);
            }
        }
    }
    
    protected int logCeiling(int n) {
        int log = 0;
        for(int i=1; i<n; i<<=1, log++);
        return log;
    }
    
    protected int logFloor(int n) {
        int log = 0;
        while(n > 1) {
            n >>>= 1;
            log ++;
        }
        return log;
    }

    @Override
    public T get(int lo, int hi) {
        lo = Math.max(lo, this.lo);
        hi = Math.min(hi, this.hi);
        T res = null;
        while(lo <= hi) {
            int log = logFloor(hi-lo+1);
            res = op.apply(res, (T)table[lo][log]);
            lo += 1<<log;
        }
        return res;
    }

    @Override
    public T get(int i) {
        if(i < lo || i > hi) {
            return null;
        } else {
            return (T)table[i][0];
        }
    }

}
