
public class WeightedEdge extends Edge implements Comparable<WeightedEdge>{
	
	private static final double EPSILON = 0.0000000001;
	public final double weight;
	
	public WeightedEdge(int v, int w, double weight) {
		super(v, w);
		this.weight = weight;
	}
	
	@Override
	public Edge revert(){
		return new WeightedEdge(w, v, weight);
	}

	@Override
	public int compareTo(WeightedEdge o) {
		if(Math.abs(weight - o.weight) <= EPSILON ) return 0;
		else if(weight > o.weight) return 1;
		else return -1;
	}

}
