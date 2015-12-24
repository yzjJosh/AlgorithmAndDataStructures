
public class RedBlackTreeTest extends OrderedSymbolTableTest {

	@Override
	protected OrderedSymbolTable<Integer, Integer> getTable() {
		return new RedBlackTree<Integer, Integer>();
	}

}
