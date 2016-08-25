import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class TernarySearchTrie<V> implements StringSymbolTable<V> {
	
	private Node root;
	private int size;
	
	@Override
	public String longestPrefixOf(String s) {
		if(s == null) return null;
		Node node = root;
		String prefix = node.hasValue? "": null;
		StringBuilder str = new StringBuilder();
		for(int i=0; i<s.length(); i++){
			node = node.middle;
			while(node != null){
				if(s.charAt(i) == node.c) break;
				else if(s.charAt(i) < node.c) node = node.left;
				else node = node.right;
			}
			if(node == null) break;
			str.append(node.c);
			if(node.hasValue)
				prefix = str.toString();
		}
		return prefix;
	}

	@Override
	public Iterable<String> keysWithPrefix(String s) {
		LinkedList<String> keys = new LinkedList<String>();
		Node node = getNode(s);
		if(node == null) return keys;
		if(node.hasValue) keys.add(s);
		collectKeys(new StringBuilder(s), node.middle, keys);
		return keys;
	}

	private void collectKeys(StringBuilder str, Node node, LinkedList<String> keys, String query, int len){
		if(node == null) return;
		if(len > query.length()) return;
		char c = len == 0? '\0': query.charAt(len-1);
		if(c == node.c || c == '.'){
			if(node != root) str.append(node.c);
			if(len == query.length()){
				if(node.hasValue) keys.add(str.toString());
			}else
				collectKeys(str, node.middle, keys, query, len+1);
			if(node != root) str.deleteCharAt(str.length()-1);
		}
		if(c < node.c || c == '.')
			collectKeys(str, node.left, keys, query, len);
		if(c > node.c || c == '.')
			collectKeys(str, node.right, keys, query, len);
	}
	
	@Override
	public Iterable<String> keysThatMatch(String s) {
		LinkedList<String> keys = new LinkedList<String>();
		if(s == null) return keys;
		collectKeys(new StringBuilder(), root, keys, s, 0);
		return keys;
	}
	
	private Node put(Node node, String key, V value, int len){
		char c = len == 0? '\0': key.charAt(len-1);
		if(node == null)
			node = new Node(c);
		if(c == node.c){
			if(len == key.length()){
				if(!node.hasValue) size++;
				node.hasValue = true;
				node.value = value;
			}else
				node.middle = put(node.middle, key, value, len+1);
		}else if(c < node.c)
			node.left = put(node.left, key, value, len);
		else
			node.right = put(node.right, key, value, len);
		return node;
	}

	@Override
	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}
	
	private Node getNode(String key){
		if(key == null) return null;
		Node node = root;
		if(node == null) return null;
		for(int i=0; i<key.length(); i++){
			node = node.middle;
			while(node != null){
				if(key.charAt(i) == node.c) break;
				else if(key.charAt(i) < node.c) node = node.left;
				else node = node.right;
			}
			if(node == null) return null;
		}
		return node;
	}

	@Override
	public V get(String key) {
		Node node = getNode(key);
		return node == null? null: (V)node.value;
	}
	
	private Node removeMin(Node setValueNode, Node node){
		if(node == null) return null;
		if(node.left == null){
			setValueNode.c = node.c;
			setValueNode.hasValue = node.hasValue;
			setValueNode.value = node.value;
			setValueNode.middle = node.middle;
			return node.right;
		}else{
			node.left = removeMin(setValueNode, node.left);
			return node;
		}
	}
	
	private Node remove(Node node, String key, int len){
		if(node == null) return null;
		if(len > key.length()) return node;
		char c = len == 0? '\0': key.charAt(len-1);
		if(c == node.c){
			if(len == key.length()){
				if(node.hasValue) size --;	
				node.value = null;
				node.hasValue = false;
			}else
				node.middle = remove(node.middle, key, len+1);
			if(node.middle == null && !node.hasValue){
				//We need to delete this node
				if(node.left == null && node.right == null)
					return null;
				else if(node.right == null)
					return node.left;
				else if(node.left == null)
					return node.right;
				else
					node.right = removeMin(node, node.right);
			}
		}else if(c < node.c)
			node.left = remove(node.left, key, len);
		else
			node.right = remove(node.right, key, len);
		return node;
	}

	@Override
	public V remove(String key) {
		Node node = getNode(key);
		if(node == null || !node.hasValue) return null;
		V ret = (V)node.value;
		root = remove(root, key, 0);
		return ret;
	}

	@Override
	public boolean contains(String key) {
		Node node = getNode(key);
		return node != null && node.hasValue;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	private void collectKeys(StringBuilder str, Node node, LinkedList<String> keys){
		if(node == null) return;
		if(node != root) str.append(node.c);
		if(node.hasValue) keys.add(str.toString());
		collectKeys(str, node.middle, keys);
		if(node != root) str.deleteCharAt(str.length()-1);
		collectKeys(str, node.left, keys);
		collectKeys(str, node.right, keys);
	}

	@Override
	public Iterable<String> keys() {
		LinkedList<String> keys = new LinkedList<String>();
		collectKeys(new StringBuilder(), root, keys);
		return keys;
	}
	
	private String toString(int depth, Node node, int pos){
		String blank = "";
		for(int i=0; i<depth; i++)
			blank += "    |";
		String position = "";
		if(pos == 0) position = "left:";
		else if(pos == 1) position = "middle:";
		else if(pos == 2)position = "right:";
		String content = node==null? "null": node+toString(depth+1, node.left, 0)+toString(depth+1, node.middle, 1)+toString(depth+1, node.right, 2);
		return "\n" + blank + position + content;
	}
	
	@Override
	public String toString(){
		return "\n{"+toString(0, root, -1)+"\n}";
	}
	
	public static class Node{
		public Node left;
		public Node right;
		public Node middle;
		public char c;
		public boolean hasValue;
		public Object value;
		
		public Node(char c){
			this.c = c;
		}
		
		@Override
		public String toString(){
			return "[c="+c+", hasValue="+hasValue+", value="+value+"]";
		}
		
	}
	
	public static void main(String[] args){
		TernarySearchTrie<Integer> trie = new TernarySearchTrie<Integer>();
		String[] words = new String[]{"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		for(int i=0; i<words.length; i++)
			trie.put(words[i], i);
		System.out.println(trie);
		System.out.println(trie.remove("one"));
		System.out.println(trie);
	}

}
