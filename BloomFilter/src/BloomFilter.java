import java.util.HashSet;
import java.util.Set;

public class BloomFilter<T> {

	private int[] bits;
	
	/**
	 * Initialize this bloom filter
	 * @param size the size of this BloomFilter, the actual size is 4*size bytes
	 */
	public BloomFilter(int size){
		bits = new int[size];
	}
	
	private long hash(T object){
		return object == null? 0: (object.hashCode() & 0x7FFFFFFF);
	}
	
	private int map1(long hash){
		return (int)(hash % (bits.length*32));
	}
	
	private int map2(long hash){
		return (int)((hash*3+7) % (bits.length*32));
	}
	
	private int map3(long hash){
		return (int)((hash*5+11) % (bits.length*32));
	}
	
	private void setBit(int index){
		bits[index/32] |= 1<<(index%32);
	}
	
	private boolean isBitSet(int index){
		return (bits[index/32] & (1<<(index%32))) != 0;
	}
	
	public void add(T object){
		long hash = hash(object);
		setBit(map1(hash));
		setBit(map2(hash));
		setBit(map3(hash));
	}
	
	public boolean contains(T object){
		long hash = hash(object);
		return isBitSet(map1(hash)) && isBitSet(map2(hash)) && isBitSet(map3(hash));
	}
	
	public static void main(String[] args){
		int size = Integer.parseInt(args[0]);
		int number = Integer.parseInt(args[1]);
		int range = 1<<20;
		BloomFilter<Integer> bf = new BloomFilter<Integer>(size);
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0; i<number; i++){
			int value = (int)(Math.random()*range);
			set.add(value);
			bf.add(value);
		}
		number = set.size();
		int correct = 0;
		for(int i=0; i<range; i++){
			if(set.contains(i) == bf.contains(i))
				correct ++;
		}
		System.out.println("Size of bloom filter is "+(size*4)+" bytes ("+(size*32)+" bits)");
		System.out.println("Number of numbers added to bloom filter is "+number);
		System.out.println("Correctness ratio is "+correct+"/"+range+", which is "+((double)correct/range*100)+"%");
			
	}
	
	
}
