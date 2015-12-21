import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class TestPriorityQueue extends TestCase{

	public void testPQ(){
		List<Integer> values = new ArrayList<Integer>();
		for(int i=0; i<100000; i++)
			values.add((int)(Math.random()*Integer.MAX_VALUE));
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(true, values);
		assertEquals(pq.size(), values.size());
		Collections.sort(values);
		for(Integer value: values)
			assertEquals(value, pq.poll());
		values = new ArrayList<Integer>();
		for(int i=0; i<100000; i++){
			int val = (int)(Math.random()*Integer.MAX_VALUE);
			values.add(val);
			pq.add(val);
		}
		Collections.sort(values);
		for(Integer value: values)
			assertEquals(value, pq.poll());
	}
	
}
