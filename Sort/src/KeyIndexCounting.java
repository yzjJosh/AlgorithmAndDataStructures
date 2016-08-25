
public class KeyIndexCounting implements Sort<Character> {

	@Override
	public void sort(Character[] array) {
		int[] count = new int[257];
		for(int i=0; i<array.length; i++)
			count[array[i]+1]++;
		for(int i=1; i<count.length; i++)
			count[i] += count[i-1];
		Character[] aux = new Character[array.length];
		for(int i=0; i<array.length; i++)
			aux[count[array[i]]++] = array[i];
		for(int i=0; i<array.length; i++)
			array[i] = aux[i];
	}

}
