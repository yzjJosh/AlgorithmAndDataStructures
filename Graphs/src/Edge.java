
public class Edge{

	public final int v;
	public final int w;
	
	public Edge(int v, int w){
		this.v = v;
		this.w = w;
	}
	
	public int other(int v){
		if(v != this.v && v != this.w)
			throw new IllegalArgumentException("End "+v+" does not belong to edge "+this);
		if(v == this.v) return w;
		else return v;
	}
	
	public Edge revert(){
		return new Edge(w, v);
	}
	
	@Override
	public String toString(){
		return v+"->"+w;
	}

}
