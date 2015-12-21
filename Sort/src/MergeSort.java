
public class MergeSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	public void sort(T[] array) {
		sort(array, array.clone(), 0, array.length-1);
	}
	
	private void sort(T[] array, T[] aux, int lo, int hi){
		if(lo >= hi) return;
		int mid = lo+(hi-lo)/2;
		sort(aux, array,lo, mid);
		sort(aux, array, mid+1, hi);
		merge(aux, array, lo, hi, mid);
	}
	
	private void merge(T[] input, T[] output, int lo, int hi, int mid){
		int pointer1 = lo;
		int pointer2 = mid+1;
		int pointer = lo;
		while(pointer1<=mid || pointer2<=hi){
			if(pointer1 > mid)
				output[pointer++] = input[pointer2++];
			else if(pointer2 > hi)
				output[pointer++] = input[pointer1++];
			else if(input[pointer2].compareTo(input[pointer1]) < 0)
				output[pointer++] = input[pointer2++];
			else
				output[pointer++] = input[pointer1++];
		}
	}

}
