
public class BFSTest extends SearchTest {

	@Override
	protected Search getSearch(Graph g, int v) {
		return new BreathFirstSearch(g, v);
	}

}
