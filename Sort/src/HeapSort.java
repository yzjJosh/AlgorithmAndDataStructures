import java.util.Arrays;

public class HeapSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	public void sort(T[] array) {
		heapify(array, 1, array.length);
		for(int i=array.length; i>0; i--){
			exchange(array, 1, i);
			sink(array, 1, i-1);
		}
	}
	
	private int left(int index){
		return index<<1;
	}
	
	private int right(int index){
		return (index<<1) + 1;
	}
	
	private T get(T[] array, int index){
		return array[index-1];
	}
	
	private void exchange(T[] array, int i1, int i2){
		i1--;
		i2--;
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}
	
	private void sink(T[] array, int index, int size){
		while(index <= size){
			T node = get(array, index);
			T left = left(index) <= size? get(array, left(index)): null;
			T right = right(index) <= size? get(array, right(index)): null;
			if(greater(node, left) && greater(node, right)) break;
			if(greater(left, right)){
				exchange(array, left(index), index);
				index = left(index);
			}else{
				exchange(array, right(index), index);
				index = right(index);
			}
		}
	}
	
	private boolean greater(T a, T b){
		if(a == null && b == null) return false;
		if(a == null) return false;
		if(b == null) return true;
		return a.compareTo(b) > 0;
	}
	
	private void heapify(T[] array, int index, int size){
		if(index > size) return;
		heapify(array, left(index), size);
		heapify(array, right(index), size);
		sink(array, index, size);
	}
	
	public static void main(String[] args){
		HeapSort<Integer> sort = new HeapSort<Integer>();
		Integer[] a = new Integer[]{1,3,-2,4,-5,6,-2,0,4,1};
		sort.sort(a);
		System.out.println(Arrays.toString(a));
	}

}
