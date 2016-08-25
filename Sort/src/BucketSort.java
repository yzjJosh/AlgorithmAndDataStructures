import java.util.ArrayList;

public class BucketSort implements Sort<Integer>{

	@SuppressWarnings("unchecked")
	@Override
	public void sort(Integer[] array) {
		if(array.length < 2) return;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(Integer val: array){
			max = Math.max(max, val);
			min = Math.min(min, val);
		}
		int interval = Math.max(1, (int)Math.ceil((double)(max-min+1)/array.length));
		ArrayList<Integer>[] buckets = (ArrayList<Integer>[])new ArrayList[array.length];
		for(int i=0; i<buckets.length; i++)
			buckets[i] = new ArrayList<Integer>();
		for(int i=0; i<array.length; i++)
			add(buckets[(array[i]-min)/interval], array[i]);
		int p = 0;
		for(int i=0; i<buckets.length; i++)
			for(Integer v: buckets[i])
				array[p++] = v;
	}
	
	private void add(ArrayList<Integer> list, Integer val){
		list.add(val);
		for(int i=list.size()-1; i>0; i--)
			if(list.get(i) < list.get(i-1)){
				Integer temp = list.get(i);
				list.set(i, list.get(i-1));
				list.set(i-1, temp);
			}else
				break;
	}

}
