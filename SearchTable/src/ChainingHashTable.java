import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class ChainingHashTable<K, V> extends SymbolTable<K, V> {

	private final static int initialSize = 16;
	private final static double thresholdHigh = 0.75;
	private final static double thresholdLow = 0.25;
	private Node[] table;
	private int size;
	
	public ChainingHashTable(){
		table = new Node[initialSize];
		for(int i=0; i<table.length; i++){
			table[i] = new Node(null, null, null, null);
			table[i].pre = table[i].next = table[i];
		}
		size = 0;
	}
	
	private int hash(K key){
		return key == null? 0: Math.abs(key.hashCode()) % table.length;
	}
	
	private boolean equals(Object k1, Object k2){
		if(k1 == k2) return true;
		if(k1 == null || k2 == null) return false;
		return k1.equals(k2);
	}
	
	private boolean __put__(K key, V value){
		Node head = table[hash(key)];
		Node node = head.next;
		while(node != head){
			if(equals(node.key, key)){
				node.val = value;
				return false;
			}
			node = node.next;
		}
		node = new Node(head.pre, head, key, value);
		head.pre = head.pre.next = node;
		return true;
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
		Node head = table[hash(key)];
		Node node = head.next;
		while(node != head){
			if(equals(node.key, key))
				return (V)node.val;
			node = node.next;
		}
		return null;
	}

	@Override
	public V remove(K key) {
		Node head = table[hash(key)];
		Node node = head.next;
		while(node != head){
			if(equals(node.key, key)){
				node.pre.next = node.next;
				node.next.pre = node.pre;
				node.pre = null;
				node.next = null;
				break;
			}
			node = node.next;
		}
		if(node == head) return null;
		if(--size <= table.length*thresholdLow && table.length > initialSize)
			resize(table.length/2);
		return (V)node.val;
	}

	@Override
	public boolean contains(K key) {
		Node head = table[hash(key)];
		Node node = head.next;
		while(node != head){
			if(equals(node.key, key))
				return true;
			node = node.next;
		}
		return false;
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
		for(int i=0; i<table.length; i++){
			Node head = table[i];
			Node node = head.next;
			while(node != head){
				keys.add((K)node.key);
				node = node.next;
			}
		}
		return keys;
	}
	
	private void resize(int newSize){
		if(newSize < size) return;
		Node[] aux = table;
		table = new Node[newSize];
		for(int i=0; i<table.length; i++){
			table[i] = new Node(null, null, null, null);
			table[i].pre = table[i].next = table[i];
		}
		for(int i=0; i<aux.length; i++){
			Node head = aux[i];
			Node node = head.next;
			while(node != head){
				__put__((K)node.key, (V)node.val);
				node = node.next;
			}
		}
	}

	private static class Node{
		public Node pre;
		public Node next;
		public Object key;
		public Object val;
		
		public Node(Node pre, Node next, Object key, Object val){
			this.pre = pre;
			this.next = next;
			this.key = key;
			this.val = val;
		}
	}

}
