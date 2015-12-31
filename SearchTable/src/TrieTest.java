
public class TrieTest extends StringSymbolTableTest {

	@Override
	protected StringSymbolTable<Integer> getStringSymbolTable() {
		return new Trie<Integer>();
	}

}
