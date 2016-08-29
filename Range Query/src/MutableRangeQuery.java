
public interface MutableRangeQuery<T> extends RangeQuery<T> {
    
    public void update(int i, T value);

}
