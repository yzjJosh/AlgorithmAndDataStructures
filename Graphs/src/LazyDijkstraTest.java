
public class LazyDijkstraTest extends DijkstraTest {
    
    @Override
    protected ShortestPathFinder getShortestPathFinder(DirectedGraph<? extends WeightedEdge> g, int v) {
        return new LazyDijkstraShortestPathFinder(g, v);
    }
}
