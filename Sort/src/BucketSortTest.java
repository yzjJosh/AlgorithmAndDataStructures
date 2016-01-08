import java.util.LinkedList;
import java.util.List;

public class BucketSortTest extends SortTest<Integer> {

	@Override
	protected Sort<Integer> getSort() {
		return new BucketSort();
	}

	@Override
	protected List<Integer[]> getTestCases() {
		LinkedList<Integer[]> arrays = new LinkedList<Integer[]>();
		for(int i=0; i<100; i++){
			int len = i*100;
			Integer[] array = new Integer[len];
			for(int j=0; j<len; j++){
				int value = (int)(Math.random()*Integer.MAX_VALUE);
				array[j] = value;
			}
			arrays.add(array);
		}
		Integer[] equal = new Integer[1000];
		int value = (int)(Math.random()*Integer.MAX_VALUE);
		for(int i=0; i<equal.length; i++)
			equal[i] = value;
		arrays.add(equal);
		return arrays;
	}

}
