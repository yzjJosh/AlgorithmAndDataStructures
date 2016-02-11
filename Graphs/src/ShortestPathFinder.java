public interface ShortestPathFinder {
	
	public Iterable<Integer> pathTo(int w);
	
	public double distanceTo(int w);
	
	public boolean hasPathTo(int w);

}
