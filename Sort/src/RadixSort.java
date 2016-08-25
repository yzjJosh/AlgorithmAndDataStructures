
public class RadixSort implements Sort<String> {

	@Override
	public void sort(String[] array) {
		sort(array, 0, array.length-1, 0);
	}
	
	private int charAt(String s, int pos){
		return pos<s.length()? s.charAt(pos): -1;
	}
	
	private void sort(String[] array, int start, int end, int pos){
		if(start >= end) return;
		int[] count = new int[258];
		for(int i=start; i<=end; i++)
			count[charAt(array[i], pos)+2]++;
		for(int i=1; i<count.length; i++)
			count[i] += count[i-1];
		String[] aux = new String[end-start+1];
		for(int i=start; i<=end; i++)
			aux[count[charAt(array[i], pos)+1]++] = array[i];
		for(int i=start; i<=end; i++)
			array[i] = aux[i-start];
		for(int i=0; i<256; i++)
			sort(array, start+count[i], start+count[i+1]-1, pos+1);
	}

}
