import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public abstract class SortTest<T extends Comparable<T>> extends TestCase{
	
	protected abstract Sort<T> getSort();

	protected abstract List<T[]> getTestCases();

	public void testCorrectness(){
		System.out.println("Testing correctness...");
		Sort<T> sort = getSort();
		List<T[]> testCases = getTestCases();
		for(T[] array: testCases){
			sort.sort(array);
			for(int i=1; i<array.length; i++)
				assertTrue(array[i-1].compareTo(array[i]) <= 0);
		}
	}
	
	public void testTimeComplexity(){
		System.out.println("Testing time complexity...");
		Sort<T> sort = getSort();
		List<T[]> testCases = getTestCases();
		for(T[] array: testCases){
			long startTime = System.nanoTime();
			sort.sort(array);
			long endTime = System.nanoTime();
			double duration = (endTime - startTime)/1000;
			System.out.println("Array length: "+array.length+", time used to sort: "+duration+"us");
		}
	}
	
	public void testStable(){
		System.out.println("Testing stable...");
		Sort<T> sort = getSort();
		List<T[]> testCases = getTestCases();
		for(T[] array: testCases){
			T[] copy = array.clone();
			Arrays.sort(copy);
			sort.sort(array);
			for(int i=0; i<array.length; i++)
				assertTrue(array[i] == copy[i]);
		}
	}

}
