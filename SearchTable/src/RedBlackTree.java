
@SuppressWarnings("unchecked")
public class RedBlackTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {
	
	private int size(Node node){
		return node==null? 0: node.size;
	}
	
	private boolean color(Node node){
		return ((RedBlackNode)node).color;
	}
	
	private void setColor(Node node, boolean color){
		((RedBlackNode)node).color = color;
	}
	
	private Node rotateLeft(Node node){
		Node ret = node.right;
		node.right = ret.left;
		ret.left = node;
		boolean color = color(node);
		setColor(node, color(ret));
		setColor(ret, color);
		ret.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return ret;
	}
	
	private Node rotateRight(Node node){
		Node ret = node.left;
		node.left = ret.right;
		ret.right = node;
		boolean color = color(node);
		setColor(node, color(ret));
		setColor(ret, color);
		ret.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return ret;
	}
	
	private void flipColor(Node node){
		boolean color = color(node);
		setColor(node, color(node.left));
		setColor(node.left, color);
		setColor(node.right, color);
	}
	
	private boolean isRed(Node node){
		if(node == null) return false;
		return color(node) == RedBlackNode.RED;
	}
	
	private boolean isBlack(Node node){
		if(node == null) return false;
		return color(node) == RedBlackNode.BLACK;
	}
	
	private Node moveRedToLeft(Node node){
		if(isRed(node.right.left)){
			//Right child has more than 1 key, borrow key from right child
			node.right = rotateRight(node.right);
			setColor(node.right, RedBlackNode.RED);
			setColor(node.right.right, RedBlackNode.BLACK);
			node = rotateLeft(node);
			setColor(node.left, RedBlackNode.BLACK);
			setColor(node.left.left, RedBlackNode.RED);
			return node;
		}else{
			//Right child has only 1 key, merge with parent
			flipColor(node);
			return node;
		}
	}
	
	private Node moveRedToRight(Node node){
		if(isRed(node.left)){
			//Make its left sibling closer
			node = rotateRight(node);
			node.right = moveRedToRight(node.right);
			return node;
		}
		if(isRed(node.left.left)){
			//Left child has more than 1 key, borrow key from left child
			setColor(node.left, RedBlackNode.RED);
			setColor(node.left.left, RedBlackNode.BLACK);
			node = rotateRight(node);
			setColor(node.right, RedBlackNode.BLACK);
			setColor(node.right.right, RedBlackNode.RED);
			return node;
		}else{
			//Left child has only 1 key, merge with parent
			flipColor(node);
			return node;
		}
	}
	
	private Node fixTree(Node node){
		if(!isRed(node.left) && isRed(node.right))	
			node = rotateLeft(node);
		if(isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if(isRed(node.left) && isRed(node.right))
			flipColor(node);
		return node;
	}
	
	private Node put(Node node, K key, V value){
		if(node == null) return new RedBlackNode(key, value, 1, RedBlackNode.RED);
		int comp = key.compareTo((K)node.key);
		if(comp == 0)
			node.value = value;
		else if(comp < 0)
			node.left = put(node.left, key, value);
		else
			node.right = put(node.right, key, value);
		node.size = size(node.left) + size(node.right) + 1;
		return fixTree(node);
	}
	
	@Override
	public void put(K key, V value){
		if(key == null) return;
		root = put(root, key, value);
		if(isRed(root))
			setColor(root, RedBlackNode.BLACK);
	}
	
	private Node removeMin(Node setValueNode, Node node){
		if(node == null) return null;
		if(node.left == null) {
			setValueNode.key = node.key;
			setValueNode.value = node.value;
			return null;
		}
		if(isBlack(node.left) && !isRed(node.left.left))
			node = moveRedToLeft(node);
		node.left = removeMin(setValueNode, node);
		node.size = size(node.left) + size(node.right) + 1;
		return fixTree(node);
	}
	
	private Node remove(Node node, K key){
		if(node == null) return null;
		int comp = key.compareTo((K)node.key);
		if(comp < 0){
			if(isBlack(node.left) && !isRed(node.left.left))
				node = moveRedToLeft(node);
			node.left = remove(node.left, key);
		}else{ 
			if(comp == 0 && node.right == null){
				if(isRed(node.left))
					setColor(node.left, RedBlackNode.BLACK);
				return node.left;
			}
			if(isBlack(node.right) && !isRed(node.right.left))
				node = moveRedToRight(node);
			comp = key.compareTo((K)node.key);
			if(comp == 0)
				node.right = removeMin(node, node.right);
			else
				node.right = remove(node.right, key);
		}
		node.size = size(node.left) + size(node.right) + 1;
		return fixTree(node);
	}
	
	@Override
	public V remove(K key){
		if(!contains(key)) return null;
		V ret = get(key);
		setColor(root, RedBlackNode.RED);
		root = remove(root, key);
		if(isRed(root))
			setColor(root, RedBlackNode.BLACK);
		return ret;
	}
	
	private static class RedBlackNode extends BinarySearchTree.Node{
		
		public static final boolean RED = true;
		public static final boolean BLACK = false;
		
		public boolean color;

		public RedBlackNode(Object key, Object value, int size, boolean color) {
			super(key, value, size);
			this.color = color;
		}
		
		@Override
		public String toString(){
			String color = this.color? "RED": "BLACK";
			return color + ": "+key+"="+value;
		}
		
	}
	
	public static void main(String[] args){
		RedBlackTree<Integer, Integer> BST = new RedBlackTree<Integer, Integer>();
		for(int i=0; i<10; i++)
			BST.put(i, i);
		System.out.println(BST);
		BST.remove(1);
		System.out.println(BST);
	}

}
