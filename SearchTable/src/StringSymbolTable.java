
public interface StringSymbolTable<V> extends SymbolTable<String, V> {

	public String longestPrefixOf(String s);
	
	public Iterable<String> keysWithPrefix(String s);
	
	/**
	 * all the keys that match s (where . matches any character)
	 * @param s query string
	 * @return Matched keys
	 */
	public Iterable<String> keysThatMatch(String s);

}
