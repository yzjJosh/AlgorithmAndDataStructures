
public interface SymbolTable<K, V> {

	public void put(K key, V value);
	
	public V get(K key);
	
	public V remove(K key);
	
	public boolean contains(K key);
	
	public boolean isEmpty();
	
	public int size();
	
	public Iterable<K> keys();
	
}
