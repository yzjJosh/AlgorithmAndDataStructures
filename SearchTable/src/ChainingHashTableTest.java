
public class ChainingHashTableTest extends SymbolTableTest {

	@Override
	protected SymbolTable<Integer, Integer> getTable() {
		return new ChainingHashTable<Integer, Integer>();
	}

}
