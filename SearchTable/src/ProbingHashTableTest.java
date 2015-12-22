
public class ProbingHashTableTest extends SymbolTableTest {

	@Override
	protected SymbolTable<Integer, Integer> getTable() {
		return new ProbingHashTable<Integer, Integer>();
	}

}
