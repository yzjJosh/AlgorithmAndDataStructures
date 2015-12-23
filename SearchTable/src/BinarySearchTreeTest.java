
public class BinarySearchTreeTest extends OrderedSymbolTableTest {

	@Override
	protected OrderedSymbolTable<Integer, Integer> getTable() {
		return new BinarySearchTree<Integer, Integer>();
	}

}
