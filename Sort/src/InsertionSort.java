
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {

	@Override
	public void sort(T[] array) {
		for(int i=1; i<array.length; i++){
			for(int j=i; j>0; j--)
				if(array[j].compareTo(array[j-1]) < 0)
					exchange(array, j, j-1);
				else
					break;
		}
	}
	
	private void exchange(T[] array, int i1, int i2){
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}

}
