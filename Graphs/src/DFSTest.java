
public class DFSTest extends SearchTest {

	@Override
	protected Search getSearch(Graph<Edge> g, int v) {
		return new DepthFirstSearch(g, v);
	}

}
