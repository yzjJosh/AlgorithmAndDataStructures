
public class SplayTreeTest extends OrderedSymbolTableTest {

	@Override
	protected OrderedSymbolTable<Integer, Integer> getTable() {
		return new SplayTree<Integer, Integer>();
	}

}
