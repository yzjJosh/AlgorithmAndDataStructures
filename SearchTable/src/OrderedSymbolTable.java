
public interface OrderedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {

	public K min();
	
	public K max();
	
	public K floor(K key);
	
	public K ceiling(K key);
	
	public int rank(K key);
	
	public K select(int rank);
	
	public int size(K lo, K hi);
	
	public Iterable<K> keys(K lo, K hi);
	
}
