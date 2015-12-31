import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import junit.framework.TestCase;

public abstract class StringSymbolTableTest extends TestCase {

	protected abstract StringSymbolTable<Integer> getStringSymbolTable();
	
	public void testTable(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		StringSymbolTable<Integer> table = getStringSymbolTable();
		for(int i=0; i<10000; i++){
			StringBuilder key = new StringBuilder();
			int len = (int)(Math.random() * 100);
			for(int j=0; j<len; j++){
				char c = (char)(Math.random()*256);
				key.append(c);
			}
			int value = (int)(Math.random() * Integer.MAX_VALUE);
			map.put(key.toString(), value);
			table.put(key.toString(), value);
		}
		map.put("", null);
		table.put("", null);
		assertFalse(table.isEmpty());
		assertEquals(table.size(), map.size());
		for(String key: map.keySet()){
			assertTrue(table.contains(key));
			assertEquals(map.get(key), table.get(key));
		}
		for(String key: table.keys())
			assertTrue(map.containsKey(key));
		StringBuilder testString = new StringBuilder();
		for(int i=0; i<100; i++)
			testString.append((char)(Math.random()*128));
		for(int i=0; i<100; i++){
			map.put(testString.substring(0, i+1), null);
			table.put(testString.substring(0, i+1), null);
			assertEquals(testString.substring(0, i+1), table.longestPrefixOf(testString.toString()));
		}
		LinkedList<String> keys = (LinkedList<String>)table.keysWithPrefix("");
		assertEquals(keys.size(), table.size());
		for(String key: keys)
			assertTrue(map.containsKey(key));
		testString = new StringBuilder();
		HashSet<String> expected = new HashSet<String>();
		for(int i=0; i<50; i++)
			testString.append((char)(Math.random()*128));
		expected.addAll((LinkedList<String>)table.keysWithPrefix(testString.toString()));
		for(int i=0; i<1000; i++){
			StringBuilder str = new StringBuilder(testString);
			int len = (int)(Math.random() * 100);
			for(int j=0; j<len; j++){
				char c = (char)(Math.random()*128+128);
				str.append(c);
			}
			expected.add(str.toString());
			table.put(str.toString(), null);
			map.put(str.toString(), null);
		}
		keys = (LinkedList<String>)table.keysWithPrefix(testString.toString());
		assertEquals(expected.size(), keys.size());
		for(String key: keys){
			assertEquals(key.substring(0, 50), testString.toString());
			assertTrue(expected.contains(key));
		}
		String pattern = "sdfdgf.df.f....dfgd..f..fg.fd.g.df.f.gdf.g.df.gd.gh..gfh.fg.hf.gj.ghj.gh.j.gh.e.rt.45. . .46.76.8.4.t3.12";
		expected = new HashSet<String>();
		expected.addAll((LinkedList<String>)table.keysThatMatch(pattern));
		for(int i=0; i<1000; i++){
			StringBuilder str = new StringBuilder();
			for(int j=0; j<pattern.length(); j++)
				if(pattern.charAt(j) == '.')
					str.append((char)(Math.random()*256));
				else
					str.append(pattern.charAt(j));
			expected.add(str.toString());
			table.put(str.toString(), null);
			map.put(str.toString(), null);
		}
		keys = (LinkedList<String>)table.keysThatMatch(pattern);
		assertEquals(keys.size(), expected.size());
		for(String key: keys)
			assertTrue(expected.contains(key));
		assertEquals(map.size(), table.size());
		for(String key: map.keySet()){
			assertEquals(map.get(key), table.remove(key));
		}
		assertEquals(table.size(), 0);
		assertTrue(table.isEmpty());
		for(String key: map.keySet()){
			assertEquals(null, table.remove(key));
			assertEquals(null, table.get(key));
			assertFalse(table.contains(key));
		}
	}
	
}
