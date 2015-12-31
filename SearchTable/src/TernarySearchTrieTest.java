
public class TernarySearchTrieTest extends StringSymbolTableTest {

	@Override
	protected StringSymbolTable<Integer> getStringSymbolTable() {
		return new TernarySearchTrie<Integer>();
	}

}
