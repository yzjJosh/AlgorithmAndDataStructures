
public class DFSTest extends SearchTest {

	@Override
	protected Search getSearch(Graph g, int v) {
		return new DepthFirstSearch(g, v);
	}

}
