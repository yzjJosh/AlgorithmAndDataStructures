import java.util.*;

public class IndexedPriorityQueue<K, V extends Comparable<V>>{

	private Map<Integer, K> key = new HashMap<Integer, K>();
	private Map<K, Integer> index = new HashMap<K, Integer>();
	private Inner inner;
	
	public IndexedPriorityQueue(boolean min){
		inner = new Inner(min);
	}
	
	public IndexedPriorityQueue(boolean min, List<V> values) {
		inner = new Inner(min, values);
	}
	
	public void put(K k, V v){
		if(!contains(k)){
			index.put(k, inner.size()+1);
			key.put(inner.size()+1, k);
			inner.add(v);
		}else{
			inner.heap[index.get(k)] = v;
			inner.swim(index.get(k));
			inner.sink(index.get(k));
		}
	}
	
	public boolean contains(K k){
		return index.containsKey(k);
	}
	
	public K peekKey(){
		return key.get(1);
	}
	
	public V peekValue(){
		return inner.peek();
	}
	
	public void poll(){
		K k = peekKey();
		inner.poll();
		key.remove(inner.size()+1);
		index.remove(k);
	}
	
	public int size(){
		return inner.size();
	}
	
	public boolean isEmpty(){
		return inner.isEmpty();
	}
	
	
	private class Inner extends PriorityQueue<V>{

		public Inner(boolean min) {
			super(min);
		}
		
		public Inner(boolean min, List<V> values){
			super(min, values);
		}
		
		@Override
		protected void exchange(int i, int j){
			super.exchange(i, j);
			K temp = key.get(i);
			key.put(i, key.get(j));
			key.put(j, temp);
			index.put(key.get(i), i);
			index.put(key.get(j), j);
		}
		
	}
	
	public static void main(String[] args){
		IndexedPriorityQueue<Integer, Integer> pq = new IndexedPriorityQueue<Integer, Integer>(true);
		pq.put(3,  0);
		pq.put(9, -4);
		pq.put(2, 6);
		pq.put(7, 2);
		pq.put(7, 100);
		while(!pq.isEmpty()){
			System.out.println(pq.peekKey()+" "+pq.peekValue());
			pq.poll();
		}
	}

}
