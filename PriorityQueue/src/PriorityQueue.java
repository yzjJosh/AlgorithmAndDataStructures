import java.util.List;

@SuppressWarnings("unchecked")
public class PriorityQueue<T extends Comparable<T>> {
	
	private final static int initialSize = 16;
	private final static double thresholdHigh = 0.75;
	private final static double thresholdLow = 0.25;
	private boolean min;
	private int size;
	Object[] heap;
	
	public PriorityQueue(boolean min){
		this.min = min;
		this.size = 0;
		resize(initialSize);
	}
	
	public PriorityQueue(boolean min, List<T> values){
		this.min = min;
		int len = initialSize;
		while(len < values.size()*thresholdHigh) len*=2;
		resize(len);
		int i=1;
		for(T value: values)
			heap[i++] = value;
		size = values.size();
		heapify(1);
	}
	
	public int size(){
		return size;
	}
	
	public void add(T value){
		heap[++size] = value;
		swim(size);
		if(size >= heap.length*thresholdHigh)
			resize(heap.length*2);
	}
	
	public T poll(){
		T ret = (T)heap[1];
		exchange(1, size);
		heap[size--] = null;
		sink(1);
		if(size <= heap.length*thresholdLow && heap.length > initialSize)
			resize(heap.length/2);
		return ret;
	}
	
	public T peek(){
		return (T)heap[1];
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	private int parent(int index){
		return index >> 1;
	}
	
	private int left(int index){
		return index << 1;
	}
	
	private int right(int index){
		return left(index)+1;
	}
	
	private boolean hasPriorityOver(T a, T b){
		if(a == null && b == null) return false;
		if(a == null) return false;
		if(b == null) return true;
		if(min) return a.compareTo(b) < 0;
		else return a.compareTo(b) > 0;
	}
	
	private void heapify(int index){
		if(index < 1 || index > size) return;
		heapify(left(index));
		heapify(right(index));
		sink(index);
	}
	
	private void resize(int newSize){
		if(newSize < size+1) return;
		Object[] aux = new Object[newSize];
		for(int i=1; i<=size; i++)
			aux[i] = heap[i];
		heap = aux;
	}
	
	protected void exchange(int i, int j){
		if(i > size || j > size) return;
		T temp = (T)heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	void sink(int index){
		while(index <= size){
			T node = (T)heap[index];
			T left = left(index)<=size? (T)heap[left(index)]: null;
			T right = right(index)<=size? (T)heap[right(index)]: null;
			if(hasPriorityOver(node, left) && hasPriorityOver(node, right)) break;
			if(hasPriorityOver(left, right)){
				exchange(left(index), index);
				index = left(index);
			}else {
				exchange(right(index), index);
				index = right(index);
			}
		}
	}
	
	void swim(int index){
		if(index > size) return;
		while(index > 1 && hasPriorityOver((T)heap[index], (T)heap[parent(index)])){
			exchange(index, parent(index));
			index = parent(index);
		}
	}
	
	public static void main(String[] args){
		Integer[] values = new Integer[]{9, -4, 0, 43, -43, 5, 3};
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(true);
		for(int i=0; i<values.length; i++)
			pq.add(values[i]);
		while(pq.size() > 0)
			System.out.println(pq.poll());
	}
	
}
