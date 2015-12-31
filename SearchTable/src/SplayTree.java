
@SuppressWarnings("unchecked")
public class SplayTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {
	
	private int size(Node node){
		return node==null? 0: node.size;
	}
	
	private Node parent(Node node){
		return node == null? null: ((SplayNode)node).parent;
	}
	
	private void setParent(Node node, Node parent){
		if(node == null) return;
		((SplayNode)node).parent = (SplayNode)parent;
	}
	
	private Node rotateLeft(Node node){
		if(node.right == null) 
			return node;
		Node ret = node.right;
		node.right = ret.left;
		ret.left = node;
		ret.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		if(parent(node) != null){
			Node parent = parent(node);
			if(parent.left == node)
				parent.left = ret;
			else
				parent.right = ret;
		}
		setParent(ret, parent(node));
		setParent(node, ret);
		setParent(node.right, node);
		return ret;
	}
	
	private Node rotateRight(Node node){
		if(node.left == null)
			return node;
		Node ret = node.left;
		node.left = ret.right;
		ret.right = node;
		ret.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		if(parent(node) != null){
			Node parent = parent(node);
			if(parent.left == node)
				parent.left = ret;
			else
				parent.right = ret;
		}
		setParent(ret, parent(node));
		setParent(node, ret);
		setParent(node.left, node);
		return ret;
	}
	
	private Node splay(Node node){
		Node parent = null;
		while((parent = parent(node)) != null){
			Node grandParent = parent(parent);
			if(grandParent == null){
				if(parent.left == node)
					rotateRight(parent);
				else
					rotateLeft(parent);
			}else{
				if(grandParent.left == parent && parent.left == node)
					rotateRight(rotateRight(grandParent));
				else if(grandParent.right == parent && parent.right == node)
					rotateLeft(rotateLeft(grandParent));
				else if(parent.left == node){
					rotateRight(parent);
					rotateLeft(grandParent);
				}else{
					rotateLeft(parent);
					rotateRight(grandParent);
				}
			}
		}
		return node;
	}
	
	private Node getNode(K key){
		if(key == null) return null;
		Node node = root;
		Node splay = null;
		while(node != null){
			splay = node;
			int comp = key.compareTo((K) node.key);
			if(comp < 0) node = node.left;
			else if(comp == 0) break;
			else node = node.right;
		}
		if(splay != null)
			root = splay(splay);
		return node;
	}
	
	@Override
	public K max(){
		K ret = super.max();
		getNode(ret);
		return ret;
	}
	
	@Override
	public K min(){
		K ret = super.min();
		getNode(ret);
		return ret;
	}
	
	@Override
	public K floor(K key){
		K ret = super.floor(key);
		getNode(ret);
		return ret;
	}
	
	@Override
	public K ceiling(K key){
		K ret = super.ceiling(key);
		getNode(ret);
		return ret;
	}
	
	@Override
	public int rank(K key){
		getNode(key);
		return super.rank(key);
	}
	
	@Override
	public V get(K key){
		Node node = getNode(key);
		return node == null? null: (V) node.value;
	}
	
	@Override
	public boolean contains(K key){
		return getNode(key) != null;
	}
	
	private Node put(Node node, K key, V value){
		if(node == null)
			return new SplayNode(key, value, null, 1);
		int comp = key.compareTo((K)node.key);
		if(comp == 0) node.value = value;
		else if(comp < 0){
			node.left = put(node.left, key, value);
			setParent(node.left, node);
		}else{
			node.right = put(node.right, key, value);
			setParent(node.right, node);
		}
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}
	
	@Override
	public void put(K key, V value){
		if(key == null) return;
		root = put(root, key, value);
		getNode(key);
	}
	
	private Node nodeToSplay = null;
	
	private Node removeMin(Node setValueNode, Node node, K key){
		if(node == null) return null;
		if(node.left == null){
			setValueNode.key = node.key;
			setValueNode.value = node.value;
			nodeToSplay = parent(node);
			return node.right;
		}else{
			node.left = removeMin(setValueNode, node.left, key);
			setParent(node.left, node);
			node.size = size(node.left) + size(node.right) + 1;
			return node;
		}
	}
	
	private Node remove(Node node, K key){
		if(node == null) return null;
		int comp = key.compareTo((K)node.key);
		if(comp == 0 && (node.left == null || node.right == null)){
			nodeToSplay = parent(node);
			if(node.left == null && node.right == null)
				return null;
			else if(node.left == null)
				return node.right;
			else
				return node.left;
		}else if(comp < 0){
			node.left = remove(node.left, key);
			setParent(node.left, node);
			node.size = size(node.left) + size(node.right) + 1;
			if(node.left == null) nodeToSplay = node;
		}else{
			if(comp > 0)
				node.right = remove(node.right, key);
			else
				node.right = removeMin(node, node.right, key);
			setParent(node.right, node);
			node.size = size(node.left) + size(node.right) + 1;
		}
		return node;
	}
	
	@Override
	public V remove(K key){
		Node node = getNode(key);
		if(node == null) return null;
		V ret = (V)node.value;
		nodeToSplay = null;
		root = remove(root, key);
		setParent(root, null);
		if(nodeToSplay != null)
			root = splay(nodeToSplay);
		return ret;
	}
	
	private class SplayNode extends Node{

		public SplayNode parent;
		
		public SplayNode(Object key, Object value, SplayNode parent, int size) {
			super(key, value, size);
			this.parent = parent;
		}
		
	}
	
	public static void main(String[] args){
		SplayTree<Integer, Integer> tree = new SplayTree<Integer, Integer>();
		for(int i=0; i<1000 ; i++){
			int v = (int)(Math.random()*10000);
			tree.put(v, v);
		}
		System.out.println(tree);
		tree.min();
		System.out.println(tree);
	}

}
