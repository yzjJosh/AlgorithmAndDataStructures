
public abstract class StringSymbolTable<V> extends SymbolTable<String, V> {

	public abstract String longestPrefixOf(String s);
	
	public abstract Iterable<String> keysWithPrefix(String s);
	
	/**
	 * all the keys that match s (where . matches any character)
	 * @param s query string
	 * @return Matched keys
	 */
	public abstract Iterable<String> keysThatMatch(String s);

}
