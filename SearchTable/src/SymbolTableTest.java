import java.util.HashMap;

import junit.framework.TestCase;

public abstract class SymbolTableTest extends TestCase {

	protected abstract SymbolTable<Integer, Integer> getTable();
	
	public void testTable(){
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		SymbolTable<Integer, Integer> table = getTable();
		for(int i=0; i<1000000; i++){
			int key = (int)(Math.random()*Integer.MAX_VALUE);
			int val = (int)(Math.random()*Integer.MIN_VALUE);
			map.put(key, val);
			table.put(key, val);
		}
		map.put(null, 23333);
		table.put(null, 23333);
		map.put(-1, null);
		table.put(-1, null);
		assertFalse(table.isEmpty());
		assertEquals(table.size(), map.size());
		for(Integer key: map.keySet()){
			assertTrue(table.contains(key));
			assertEquals(map.get(key), table.get(key));
		}
		for(Integer key: table.keys())
			assertTrue(map.containsKey(key));
		for(Integer key: map.keySet()){
			assertEquals(map.get(key), table.remove(key));
		}
		assertEquals(table.size(), 0);
		assertTrue(table.isEmpty());
		for(Integer key: map.keySet()){
			assertEquals(null, table.remove(key));
			assertEquals(null, table.get(key));
			assertFalse(table.contains(key));
		}
	}
	
}

