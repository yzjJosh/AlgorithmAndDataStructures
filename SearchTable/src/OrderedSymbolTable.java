
public abstract class OrderedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {

	public abstract K min();
	
	public abstract K max();
	
	public abstract K floor(K key);
	
	public abstract K ceiling(K key);
	
	public abstract int rank(K key);
	
	public abstract K select(int rank);
	
	public abstract int size(K lo, K hi);
	
	public abstract Iterable<K> keys(K lo, K hi);
	
}
