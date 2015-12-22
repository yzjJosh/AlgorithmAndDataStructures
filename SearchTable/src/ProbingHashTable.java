import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class ProbingHashTable<K, V> extends SymbolTable<K, V>{
	
	private final static int initialSize = 16;
	private final static double thresholdHigh = 0.5;
	private final static double thresholdLow = 0.125;
	private Node[] table;
	private int size;
	
	public ProbingHashTable(){
		this.table = new Node[initialSize];
		this.size = 0;
	}

	private int hash(Object key){
		return key == null? 0: Math.abs(key.hashCode()) % table.length;
	}
	
	private boolean equals(Object k1, Object k2){
		if(k1 == k2) return true;
		if(k1 == null || k2 == null) return false;
		return k1.equals(k2);
	}
	
	private int next(int index){
		return index == table.length-1? 0: index+1;
	}
	
	private int prev(int index){
		return index == 0? table.length-1: index-1;
	}
	
	private boolean __put__(K key, V value){
		Node node = null;
		int index = hash(key);
		do{
			node = table[index];
			index = next(index);
		}while(node!=null && !equals(node.key, key));
		if(node == null){
			table[prev(index)] = new Node(key, value);
			return true;
		}else{
			node.val = value;
			return false;
		}
	}
	
	@Override
	public void put(K key, V value) {
		if(__put__(key, value)){
			if(++size >= table.length*thresholdHigh)
				resize(table.length*2);
		}
	}

	@Override
	public V get(K key) {
		Node node = null;
		int index = hash(key);
		do{
			node = table[index];
			index = next(index);
		}while(node!=null && !equals(node.key, key));
		if(node == null)
			return null;
		else
			return (V)node.val;
	}

	@Override
	public V remove(K key) {
		Node node = null;
		int index = hash(key);
		do{
			node = table[index];
			index = next(index);
		}while(node!=null && !equals(node.key, key));
		if(node == null)
			return null;
		else{
			table[prev(index)] = null;
			if(--size <= table.length*thresholdLow && table.length > initialSize)
				resize(table.length/2);
			else{
				for(; table[index]!=null; index=next(index)){
					Node temp = table[index];
					table[index] = null;
					__put__((K)temp.key, (V)temp.val);
				}
			}
			return (V)node.val;
		}
	}

	@Override
	public boolean contains(K key) {
		Node node = null;
		int index = hash(key);
		do{
			node = table[index];
			index = next(index);
		}while(node!=null && !equals(node.key, key));
		return node != null;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterable<K> keys() {
		LinkedList<K> keys = new LinkedList<K>();
		for(int i=0; i<table.length; i++)
			if(table[i] != null)
				keys.add((K)table[i].key);
		return keys;
	}
	
	private void resize(int newSize){
		if(newSize < size) return;
		Node[] aux = table;
		table = new Node[newSize];
		for(int i=0; i<aux.length; i++)
			if(aux[i] != null)
				__put__((K)aux[i].key, (V)aux[i].val);
	}
	
	private static class Node{
		public Object key;
		public Object val;
		
		public Node(Object key, Object val){
			this.key = key;
			this.val = val;
		}
	}

}
