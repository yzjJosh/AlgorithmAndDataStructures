
public class WeightedQuickUnion {

	private int[] tree;
	private int[] weight;
	private int parts;
	
	public WeightedQuickUnion(int n){
		tree = new int[n];
		weight = new int[n];
		for(int i=0; i<n; i++){
			tree[i] = i;
			weight[i] = 1;
		}
		parts = n;
	}
	
	private int root(int node){
		if(tree[node] == node) return node;
		return tree[node] = root(tree[node]);
	}
	
	public boolean isConnected(int i, int j){
		return root(i) == root(j);
	}
	
	public void connect(int i, int j){
		i = root(i);
		j = root(j);
		if(i == j) return;
		if(weight[i] < weight[j]){
			tree[i] = j;
			weight[j] += weight[i];
		}else{
			tree[j] = i;
			weight[i] += weight[j];
		}
		parts--;
	}
	
	public int partsNum(){
		return parts;
	}
	
	
}
