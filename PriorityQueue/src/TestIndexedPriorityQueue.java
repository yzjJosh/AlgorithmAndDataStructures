import static org.junit.Assert.*;

import org.junit.Test;

public class TestIndexedPriorityQueue{

	static class Node implements Comparable<Node>{
		public int key;
		public int val;
		
		public Node(int k, int v){
			key = k;
			val = v;
		}
		
		@Override
		public int compareTo(Node o) {
			if(val > o.val) return 1;
			else if(val < o.val) return -1;
			else return 0;
		}
	}
	
	private static void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	@Test public void test0(){
		IndexedPriorityQueue<Integer, Integer> ipq = new IndexedPriorityQueue<Integer, Integer>(true);
		PriorityQueue<Node> pq = new PriorityQueue<Node>(true);
		int len = 100000;
		int[] index = new int[len];
		for(int i=0; i<len; i++)
			index[i] = i;
		for(int i=0; i<len; i++){
			swap(index, i, i+(int)(Math.random()*(len-i)));
			int val = (int)(Math.random()*Integer.MAX_VALUE);
			pq.add(new Node(index[i], val));
			ipq.put(index[i], val);
		}
		for(int i=0; i<len; i++){
			assertEquals(pq.poll().key, ipq.peekKey().intValue());
			ipq.poll();
		}
	}
	
	@Test public void test1(){
		IndexedPriorityQueue<Integer, Integer> ipq = new IndexedPriorityQueue<Integer, Integer>(true);
		for(int i=0; i<100; i++)
			ipq.put(i, i);
		ipq.put(99, -1);
		assertEquals(ipq.peekKey().intValue(), 99);
	}
}
