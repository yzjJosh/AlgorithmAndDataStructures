import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class BinarySearchTree<K extends Comparable<K>, V> extends OrderedSymbolTable<K, V> {
	
	protected Node root;
	
	private Node floorNode(K key){
		if(key == null) return null;
		Node node = root;
		Node floor = null;
		while(node != null){
			int comp = key.compareTo((K) node.key);
			if(comp < 0) node = node.left;
			else if(comp == 0) return node;
			else{
				floor = node;
				node = node.right;
			}
		}
		return floor;
	}
	
	protected Node getNode(K key){
		Node node = floorNode(key);
		if(node != null && key.compareTo((K)node.key) == 0)
			return node;
		else
			return null;
	}
	

	@Override
	public K min() {
		if(root == null) return null;
		Node node = root;
		while(node.left != null)
			node = node.left;
		return (K)node.key;
	}

	@Override
	public K max() {
		if(root == null) return null;
		Node node = root;
		while(node.right != null)
			node = node.right;
		return (K)node.key;
	}

	@Override
	public K floor(K key) {
		Node floor = floorNode(key);
		return floor==null? null: (K)floor.key;
	}

	@Override
	public K ceiling(K key) {
		if(key == null) return null;
		Node node = root;
		Node ceiling = null;
		while(node != null){
			int comp = key.compareTo((K) node.key);
			if(comp < 0) {
				ceiling = node;
				node = node.left;
			}else if(comp == 0){
				ceiling = node;
				break;
			}else node = node.right;
		}
		return ceiling==null? null: (K)ceiling.key;
	}
	
	private int size(Node node){
		return node == null? 0: node.size;
	}
	

	@Override
	public int rank(K key) {
		if(key == null) return -1;
		Node node = root;
		int curRank = 0;
		while(node != null){
			int comp = key.compareTo((K)node.key);
			if(comp == 0){
				curRank += size(node.left);
				break;
			}else if(comp < 0) node = node.left;
			else{
				curRank += size(node.left)+1;
				node = node.right;
			}
		}
		return curRank;
	}

	@Override
	public K select(int rank) {
		if(rank<0 || rank>=size()) return null;
		Node node = root;
		int curRank = 0;
		while(node != null){
			curRank += size(node.left);
			if(curRank == rank) return (K)node.key;
			else if(curRank < rank) {
				curRank ++;
				node = node.right;
			}else{
				curRank -= size(node.left);
				node = node.left;
			}
		}
		assert(false);
		return null;
	}

	@Override
	public int size(K lo, K hi) {
		if(lo == null || hi == null) return 0;
		if(lo.compareTo(hi) > 0) return 0;
		int indexLo = rank(lo);
		int indexHi = rank(floor(hi));
		return indexHi - indexLo + 1;
	}
	
	private void collectKeys(Node node, LinkedList<K> keys, K lo, K hi){
		if(node == null) return;
		int locomp = lo.compareTo((K)node.key);
		int hicomp = hi.compareTo((K)node.key);
		if(locomp < 0)
			collectKeys(node.left, keys, lo, hi);
		if(locomp<=0 && hicomp>=0)
			keys.add((K)node.key);
		if(hicomp > 0)
			collectKeys(node.right, keys, lo, hi);
	}

	@Override
	public Iterable<K> keys(K lo, K hi) {
		LinkedList<K> keys = new LinkedList<K>();
		if(lo == null || hi == null) return keys;
		if(lo.compareTo(hi) > 0) return keys;
		collectKeys(root, keys, lo, hi);
		return keys;
	}
	
	private Node put(Node node, K key, V value){
		if(node == null)
			return new Node(key, value, 1);
		int comp = key.compareTo((K)node.key);
		if(comp == 0)
			node.value = value;
		else if(comp < 0)
			node.left = put(node.left, key, value);
		else
			node.right = put(node.right, key, value);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	@Override
	public void put(K key, V value) {
		if(key == null) return;
		root = put(root, key, value);
	}

	@Override
	public V get(K key) {
		Node node = getNode(key);
		return node==null? null: (V)node.value;
	}
	
	private Node removeMin(Node setValueNode, Node node){
		if(node == null) return null;
		if(node.left == null) {
			setValueNode.key = node.key;
			setValueNode.value = node.value;
			return null;
		}
		node.left = removeMin(setValueNode, node.left);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	private Node remove(Node node, K key){
		if(node == null) return null;
		int comp = key.compareTo((K)node.key);
		if(comp == 0){
			if(node.left == null && node.right == null) return null;
			else if(node.left == null) return node.right;
			else if(node.right == null) return node.left;
			else node.right = removeMin(node, node.right);
		}else if(comp < 0)
			node.left = remove(node.left, key);
		else if(comp > 0)
			node.right = remove(node.right, key);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	@Override
	public V remove(K key) {
		Node node = getNode(key);
		if(node == null) return null;
		V ret = (V)node.value;
		root = remove(root, key);
		return ret;
	}

	@Override
	public boolean contains(K key) {
		return getNode(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size(root);
	}
	
	private void collectKeys(Node node, LinkedList<K> keys){
		if(node == null) return;
		collectKeys(node.left, keys);
		keys.add((K)node.key);
		collectKeys(node.right, keys);
	}

	@Override
	public Iterable<K> keys() {
		LinkedList<K> keys = new LinkedList<K>();
		collectKeys(root, keys);
		return keys;
	}
	
	private String toString(int depth, Node node){
		if(node == null) return "";
		String blank = "";
		for(int i=0; i<depth; i++)
			blank += "    ";
		return "\n"+blank+node+toString(depth+1, node.left)+toString(depth+1, node.right);
	}
	
	@Override
	public String toString(){
		return "\n{"+toString(0, root)+"\n}";
	}
	
	public static class Node{
		public Object key;
		public Object value;
		public Node left;
		public Node right;
		public int size;
		
		public Node(Object key, Object value, int size){
			this.key = key;
			this.value = value;
			this.size = size;
		}
		
		@Override
		public String toString(){
			return key+"="+value;
		}
	}

}
