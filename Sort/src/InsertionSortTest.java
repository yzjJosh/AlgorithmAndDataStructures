import java.util.LinkedList;
import java.util.List;

public class InsertionSortTest extends SortTest<String> {

	@Override
	protected Sort<String> getSort() {
		return new InsertionSort<String>();
	}

	@Override
	protected List<String[]> getTestCases() {
		LinkedList<String[]> arrays = new LinkedList<String[]>();
		for(int i=0; i<100; i++){
			int len = i*100;
			String[] array = new String[len];
			for(int j=0; j<len; j++){
				long value = (long)(Math.random()*Long.MAX_VALUE);
				array[j] = String.valueOf(value);
			}
			arrays.add(array);
		}
		String[] equal = new String[1000];
		long value = (long)(Math.random()*Long.MAX_VALUE);
		for(int i=0; i<equal.length; i++)
			equal[i] = String.valueOf(value);
		arrays.add(equal);
		return arrays;
	}

}
