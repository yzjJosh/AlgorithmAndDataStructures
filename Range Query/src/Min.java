
public class Min<T extends Comparable<T>> implements Operation<T> {

    @Override
    public T apply(T a, T b) {
        if(a == null && b == null) {
            return null;
        } else if(a == null) {
            return b;
        } else if(b == null) {
            return a;
        } else {
            return a.compareTo(b) <= 0? a: b;
        }
    }

}
