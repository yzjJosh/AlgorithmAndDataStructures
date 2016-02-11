
public class UndirectedGraph<E extends Edge> extends DirectedGraph<E> {

	public UndirectedGraph(int V) {
		super(V);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addEdge(E e){
		super.addEdge(e);
		super.addEdge((E) e.revert());
	}
	
	@Override
	public int E(){
		return super.E()/2;
	}

}
