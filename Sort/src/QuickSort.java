
public class QuickSort<T extends Comparable<T>> implements Sort<T> {

	@Override
	public void sort(T[] array) {
		sort(array, 0, array.length-1);
	}
	
	private void sort(T[] array, int lo, int hi){
		if(lo >= hi) return;
		int pivot = lo + (int)(Math.random()*(hi-lo+1));
		exchange(array, pivot, lo);
		int i = lo+1;
		int j = hi;
		while(i <= j){
			int comp = array[i].compareTo(array[lo]);
			if(comp > 0) exchange(array, i, j--);
			else i++;
		}
		exchange(array, j, lo);
		sort(array, lo, j-1);
		sort(array, i, hi);
	}
	
	private void exchange(T[] array, int i1, int i2){
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}

}
