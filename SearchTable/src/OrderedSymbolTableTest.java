import java.util.Arrays;
import java.util.TreeMap;

import junit.framework.TestCase;

public abstract class OrderedSymbolTableTest extends TestCase {
	
protected abstract OrderedSymbolTable<Integer, Integer> getTable();
	
	public void testTable(){
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		OrderedSymbolTable<Integer, Integer> table = getTable();
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i=0; i<1000000; i++){
			int key = (int)(Math.random()*Integer.MAX_VALUE);
			int val = (int)(Math.random()*Integer.MIN_VALUE);
			map.put(key, val);
			table.put(key, val);
			max = Math.max(max, key);
			min = Math.min(min, key);
			assertEquals(max, table.max().intValue());
			assertEquals(min, table.min().intValue());
		}
		map.put(-1, null);
		table.put(-1, null);
		int[] sortedArray = new int[map.size()];
		int index = 0;
		for(Integer key: map.keySet())
			sortedArray[index++] = key;
		Arrays.sort(sortedArray);
		assertFalse(table.isEmpty());
		assertEquals(table.size(), map.size());
		for(Integer key: map.keySet()){
			assertTrue(table.contains(key));
			assertEquals(map.get(key), table.get(key));
		}
		index = 0;
		for(Integer key: table.keys()){
			assertTrue(map.containsKey(key));
			assertEquals(key.intValue(), sortedArray[index++]);
		}
		for(int i=0; i<10000; i++){
			int key = (int)(Math.random()*Integer.MAX_VALUE);
			assertEquals(map.floorKey(key), table.floor(key));
			assertEquals(map.ceilingKey(key), table.ceiling(key));
			int rank = Arrays.binarySearch(sortedArray, key);
			rank = rank<0? -(rank+1): rank;
			assertEquals(rank, table.rank(key));
		}
		for(int i=0; i<sortedArray.length; i++)
			assertEquals(sortedArray[i], table.select(i).intValue());
		for(int i=0; i<100; i++){
			int lo = (int)(Math.random()*Integer.MAX_VALUE/2);
			int hi = lo + (int)(Math.random()*Integer.MAX_VALUE/2);
			int indexLo = Arrays.binarySearch(sortedArray, lo);
			int indexHi = Arrays.binarySearch(sortedArray, hi);
			indexLo = indexLo<0? -(indexLo+1): indexLo;
			indexHi = indexHi<0? -(indexHi+1)-1: indexHi;
			assertEquals(indexHi-indexLo+1, table.size(lo, hi));
			index = indexLo;
			for(Integer key: table.keys(lo, hi))
				assertEquals(sortedArray[index++], key.intValue());
			assertEquals(index-indexLo, table.size(lo, hi));
		}
		index = 0;
		for(Integer key: table.keys()){
			assertEquals(map.remove(key), table.remove(key));
			if(++index == 500000) break;
		}
		for(Integer key: map.keySet()){
			assertTrue(table.contains(key));
			assertEquals(map.get(key), table.get(key));
		}
		for(Integer key: table.keys())
			assertTrue(map.containsKey(key));
		assertEquals(table.size(), map.size());
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
