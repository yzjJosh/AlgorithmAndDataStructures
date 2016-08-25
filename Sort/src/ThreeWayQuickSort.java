
public class ThreeWayQuickSort<T extends Comparable<T>> implements Sort<T> {

	@Override
	public void sort(T[] array) {
		sort(array, 0, array.length-1);
	}
	
	private void sort(T[] array, int start, int end){
		if(start >= end) return;
		int pivot = start + (int)(Math.random()*(end-start+1));
		exchange(array, pivot, start);
		int lo = start;
		int i = lo+1;
		int hi = end;
		while(i <= hi){
			int comp = array[i].compareTo(array[lo]);
			if(comp > 0) exchange(array, i, hi--);
			else if(comp == 0) i++;
			else exchange(array, lo++, i++);
		}
		sort(array, start, lo-1);
		sort(array, i, end);
	}
	
	private void exchange(T[] array, int i1, int i2){
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}

}
