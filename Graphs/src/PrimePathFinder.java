import java.util.*;

public class PrimePathFinder {
	
	private static final long Q = Integer.MAX_VALUE;
	private List<Iterable<Integer>> primePaths;
	
	public PrimePathFinder(DirectedGraph<? extends Edge> graph){
		if(graph == null)
			throw new NullPointerException();
		primePaths = new LinkedList<Iterable<Integer>>();
		List<List<Integer>> paths = new LinkedList<List<Integer>>();
		for(int i=0; i<graph.V(); i++)
			collect(i, new ArrayList<Integer>(), graph, new boolean[graph.V()], paths);
		for(int i=0; i<paths.size()-1; i++){
			if(paths.get(i) == null) continue;
			for(int j=i+1; j<paths.size(); j++){
				List<Integer> path1 = paths.get(i);
				List<Integer> path2 = paths.get(j);
				if(path1 == null || path2 == null) 
					continue;
				if(isSubstring(path1, path2, graph.V())) paths.set(j, null);
				if(isSubstring(path2, path1, graph.V())) paths.set(i, null);
			}
		}
		for(List<Integer> path: paths)
			if(path != null) primePaths.add(path);
	}
	
	@SuppressWarnings("unchecked")
	private static void collect(int cur, ArrayList<Integer> path, DirectedGraph<? extends Edge> graph, boolean[] inCurPath, List<List<Integer>> result){
		if(inCurPath[cur]){
			if(cur == path.get(0)){
				path.add(cur);
				result.add((List<Integer>)path.clone());
				path.remove(path.size()-1);
			}else
				result.add((List<Integer>)path.clone());
		}else{
			inCurPath[cur] = true;
			path.add(cur);
			int outDegree = 0;
			for(Edge e: graph.adj(cur)){
				collect(e.w, path, graph, inCurPath, result);
				outDegree ++;
			}
			if(outDegree == 0)
				result.add((List<Integer>)path.clone());
			inCurPath[cur] = false;
			path.remove(path.size()-1);
		}
	}
	
	private long hash(List<Integer> list, int radix){
		long hash = 0;
		for(int v: list)
			hash = (hash*radix + v)%Q;
		return hash;
	}
	
	private boolean equals(List<Integer> l, List<Integer> sub, int offset){
		if(l.size()-offset < sub.size()) return false;
		for(int i=0; i<sub.size(); i++)
			if(l.get(i+offset).intValue() != sub.get(i).intValue())
				return false;
		return true;
	}
	
	private boolean isSubstring(List<Integer> l, List<Integer> sub, int radix){
		if(sub.size() > l.size()) return false;
		long target = hash(sub, radix);
		long RN = 1;
		for(int i=0; i<sub.size()-1; i++)
			RN = (RN*radix)%Q;
		long hash = 0;
		for(int i=0; i<sub.size(); i++)
			hash = (hash*radix + l.get(i))%Q;
		if(hash == target && equals(l, sub, 0)) return true;
		for(int i=sub.size(); i<l.size(); i++){
			hash = (hash+Q-l.get(i-sub.size())*RN%Q)%Q;
			hash = (hash*radix + l.get(i))%Q;
			if(hash == target && equals(l, sub, i-sub.size()+1)) return true;
		}
		return false;
	}
	
	public Iterable<Iterable<Integer>> primePaths(){
		return primePaths;
	}
	
	public static void main(String[] args){
		DirectedGraph<Edge> g = new DirectedGraph<Edge>(7);
		g.addEdge(new Edge(0, 1));
		g.addEdge(new Edge(1, 2));
		g.addEdge(new Edge(1, 5));
		g.addEdge(new Edge(2, 3));
		g.addEdge(new Edge(3, 1));
		g.addEdge(new Edge(0, 4));
		g.addEdge(new Edge(4, 4));
		g.addEdge(new Edge(4, 6));
		g.addEdge(new Edge(5, 6));
		PrimePathFinder f = new PrimePathFinder(g);
		for(Iterable<Integer> i: f.primePaths()){
			List<Integer> path = new LinkedList<Integer>();
			for(int n: i) path.add(n);
			System.out.println(path);
		}
			
	}

}
