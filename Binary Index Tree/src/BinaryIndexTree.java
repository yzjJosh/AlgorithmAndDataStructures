
public class BinaryIndexTree {
	
	private int tree[];
	private int data[];
	
	public BinaryIndexTree(int size){
		if(size < 0)
			throw new IllegalArgumentException();
		tree = new int[size+1];
		data = new int[size];
	}
	
	public BinaryIndexTree(int[] array){
		if(array == null)
			throw new NullPointerException();
		tree = new int[array.length+1];
		data = new int[array.length];
		int[] sum = new int[array.length+1];
		for(int i=0; i<array.length; i++){
			data[i] = array[i];
			sum[i+1] = sum[i] + array[i];
			tree[i+1] = sum[i+1] - sum[i+1-((i+1)&(-i-1))];
		}
	}
	
	private int cumulativeSum(int i){
		if(i<-1 || i>=data.length)
			throw new IndexOutOfBoundsException(i+"");
		i++;
		int sum = 0;
		while(i > 0){
			sum += tree[i];
			i -= i&-i;
		}
		return sum;
	}
	
	//Sum[i, j], inclusive
	public int rangeSum(int i, int j){
		if(i > j)
			throw new IllegalArgumentException();
		return cumulativeSum(j) - cumulativeSum(i-1);
	}
	
	public void update(int i, int value){
		if(i<0 || i>=data.length)
			throw new IndexOutOfBoundsException();
		int inc = value - data[i];
		data[i] = value;
		i++;
		while(i < tree.length){
			tree[i] += inc;
			i += i&-i;
		}
	}
	
	public static void main(String[] args){
		int[] num = new int[10000];
		for(int i=0; i<num.length; i++)
			num[i] =(int) (Math.random()*(Integer.MAX_VALUE/20000));
		BinaryIndexTree bit = new BinaryIndexTree(num);
		for(int i=0; i<1000; i++){
			int start = (int)(Math.random()*10000);
			int end = start + (int)(Math.random()*(10000-start-1));
			int sum = 0;
			for(int j=start; j<=end; j++) sum += num[j];
			assert(sum == bit.rangeSum(start, end)): sum+" "+bit.rangeSum(start, end)+" "+i;
		}
		for(int i=0; i<num.length; i++){
			num[i] =(int) (Math.random()*(Integer.MAX_VALUE/20000));
			bit.update(i, num[i]);
		}
		for(int i=0; i<1000; i++){
			int start = (int)(Math.random()*10000);
			int end = start + (int)(Math.random()*(10000-start-1));
			int sum = 0;
			for(int j=start; j<=end; j++) sum += num[j];
			assert(sum == bit.rangeSum(start, end)): sum+" "+bit.rangeSum(start, end)+" "+i;
		}
		bit = new BinaryIndexTree(10000);
		for(int i=0; i<10000; i++)
			bit.update(i, num[i]);
		for(int i=0; i<1000; i++){
			int start = (int)(Math.random()*10000);
			int end = start + (int)(Math.random()*(10000-start-1));
			int sum = 0;
			for(int j=start; j<=end; j++) sum += num[j];
			assert(sum == bit.rangeSum(start, end)): sum+" "+bit.rangeSum(start, end)+" "+i;
		}
		System.out.println("Pass!");
	}
	
}
