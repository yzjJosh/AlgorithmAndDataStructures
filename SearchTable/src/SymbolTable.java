
public abstract class SymbolTable<K, V> {

	public abstract void put(K key, V value);
	
	public abstract V get(K key);
	
	public abstract V remove(K key);
	
	public abstract boolean contains(K key);
	
	public abstract boolean isEmpty();
	
	public abstract int size();
	
	public abstract Iterable<K> keys();
	
}
