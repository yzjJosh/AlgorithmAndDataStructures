
public class ShellSort<T extends Comparable<T>> extends Sort<T> {

	@Override
	public void sort(T[] array) {
		int h = 1;
		while(h < array.length/3) h = h*3+1;
		while(h > 0){
			for(int i=h; i<array.length; i++)
				for(int j=i; j>=h; j-=h)
					if(array[j].compareTo(array[j-h]) < 0)
						exchange(array, j, j-h);
					else
						break;
			h /= 3;
		}
	}
	
	private void exchange(T[] array, int i1, int i2){
		T temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}

}
