
public class UndirectedGraph extends DirectedGraph {

	public UndirectedGraph(int V) {
		super(V);
	}
	
	@Override
	public void addEdge(int v, int w){
		super.addEdge(v, w);
		super.addEdge(w, v);
	}
	
	@Override
	public int E(){
		return super.E()/2;
	}

}
