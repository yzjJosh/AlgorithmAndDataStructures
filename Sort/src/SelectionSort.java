
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	public void sort(T[] array) {
		for(int i=0; i<array.length; i++){
			int min = i;
			for(int j=i; j<array.length; j++)
				if(array[j].compareTo(array[min]) < 0)
					min = j;
			exchange(array, i, min);
		}
	}
	
	private void exchange(T[] array, int i1, int i2){
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}

}
