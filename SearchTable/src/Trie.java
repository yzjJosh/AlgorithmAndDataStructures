import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Trie<V> implements StringSymbolTable<V> {

	private TrieNode root;
	private int size;
	
	public Trie(){
		root = new TrieNode();
	}
	
	@Override
	public String longestPrefixOf(String s) {
		if(s == null) return null;
		TrieNode node = root;
		String prefix = node.hasValue? "": null;
		StringBuilder path = new StringBuilder();
		for(int i=0; i<s.length(); i++){
			node = node.next[s.charAt(i)];
			if(node == null) break;
			path.append(s.charAt(i));
			if(node.hasValue)
				prefix = path.toString();
		}
		return prefix;
	}

	@Override
	public Iterable<String> keysWithPrefix(String s) {
		LinkedList<String> keys = new LinkedList<String>();
		if(s == null) return keys;
		TrieNode node = root;
		for(int i=0; i<s.length(); i++){
			node = node.next[s.charAt(i)];
			if(node == null) return keys;
		}
		collectKeys(new StringBuilder(s), node, keys);
		return keys;
	}
	
	private void collectKeys(StringBuilder key, TrieNode cur, LinkedList<String> keys, String query, int depth){
		if(cur == null) return;
		if(depth > query.length()) return;
		else if(depth == query.length()){
			if(cur.hasValue) keys.add(key.toString());
			return;
		}else{
			if(query.charAt(depth) == '.')
				for(int i=0; i<cur.next.length; i++){
					key.append((char)i);
					collectKeys(key, cur.next[i], keys, query, depth+1);
					key.deleteCharAt(key.length()-1);
				}
			else{
				key.append(query.charAt(depth));
				collectKeys(key, cur.next[query.charAt(depth)], keys, query, depth+1);
				key.deleteCharAt(key.length()-1);
			}
		}
	}

	@Override
	public Iterable<String> keysThatMatch(String s) {
		LinkedList<String> keys = new LinkedList<String>();
		if(s == null) return keys;
		collectKeys(new StringBuilder(), root, keys, s, 0);
		return keys;
	}

	@Override
	public void put(String key, V value) {
		if(key == null) return;
		TrieNode node = root;
		for(int i=0; i<key.length(); i++){
			if(node.next[key.charAt(i)] == null)
				node.next[key.charAt(i)] = new TrieNode();
			node = node.next[key.charAt(i)];
		}
		if(!node.hasValue) size ++;
		node.value = value;
		node.hasValue = true;
	}

	@Override
	public V get(String key) {
		if(key == null) return null;
		TrieNode node = root;
		for(int i=0; i<key.length(); i++){
			node = node.next[key.charAt(i)];
			if(node == null) return null;
		}
		return (V)node.value;
	}

	private TrieNode remove(TrieNode node, String key, int depth){
		if(node == null) return null;
		if(depth > key.length()) return node;
		else if(depth == key.length()){
			if(node.hasValue) size --;
			node.hasValue = false;
			node.value = null;
		}else
			node.next[key.charAt(depth)] = remove(node.next[key.charAt(depth)], key, depth+1);
		if(!node.hasValue){
			for(int i=0; i<node.next.length; i++)
				if(node.next[i] != null)
					return node;
			return null;
		}else
			return node;
	}
	
	@Override
	public V remove(String key) {
		if(!contains(key)) return null;
		V ret = get(key);
		remove(root, key, 0);
		return ret;
	}

	@Override
	public boolean contains(String key) {
		if(key == null) return false;
		TrieNode node = root;
		for(int i=0; i<key.length(); i++){
			node = node.next[key.charAt(i)];
			if(node == null) return false;
		}
		return node.hasValue;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	private void collectKeys(StringBuilder key, TrieNode cur, LinkedList<String> keys){
		if(cur == null) return;
		if(cur.hasValue) keys.add(key.toString());
		for(int i=0; i<cur.next.length; i++){
			if(cur.next[i] == null) continue;
			key.append((char)i);
			collectKeys(key, cur.next[i], keys);
			key.deleteCharAt(key.length()-1);
		}
	}

	@Override
	public Iterable<String> keys() {
		LinkedList<String> keys = new LinkedList<String>();
		collectKeys(new StringBuilder(), root, keys);
		return keys;
	}
	
	public static class TrieNode{
		
		public TrieNode[] next;
		public Object value;
		public boolean hasValue;
		
		public TrieNode(){
			this.next = new TrieNode[256];
		}
		
	}

}
