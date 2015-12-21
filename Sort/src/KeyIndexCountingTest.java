import java.util.LinkedList;
import java.util.List;

public class KeyIndexCountingTest extends SortTest<Character> {

	@Override
	protected Sort<Character> getSort() {
		return new KeyIndexCounting();
	}

	@Override
	protected List<Character[]> getTestCases() {
		LinkedList<Character[]> arrays = new LinkedList<Character[]>();
		for(int i=0; i<100; i++){
			int len = i*100;
			Character[] array = new Character[len];
			for(int j=0; j<len; j++){
				char value = (char)(Math.random()*256);
				array[j] = value;
			}
			arrays.add(array);
		}
		Character[] equal = new Character[1000];
		char value = (char)(Math.random()*256);
		for(int i=0; i<equal.length; i++)
			equal[i] = value;
		arrays.add(equal);
		return arrays;
	}

}
