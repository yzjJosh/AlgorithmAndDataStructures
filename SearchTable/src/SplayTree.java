
@SuppressWarnings("unchecked")
public class SplayTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    private Node parent(Node node) {
        return node == null ? null : ((SplayNode) node).parent;
    }

    private void setParent(Node node, Node parent) {
        if (node == null)
            return;
        ((SplayNode) node).parent = (SplayNode) parent;
    }

    private Node rotateLeft(Node node) {
        if (node.right == null)
            return node;
        Node ret = node.right;
        node.right = ret.left;
        ret.left = node;
        ret.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        if (parent(node) != null) {
            Node parent = parent(node);
            if (parent.left == node)
                parent.left = ret;
            else
                parent.right = ret;
        }
        setParent(ret, parent(node));
        setParent(node, ret);
        setParent(node.right, node);
        return ret;
    }

    private Node rotateRight(Node node) {
        if (node.left == null)
            return node;
        Node ret = node.left;
        node.left = ret.right;
        ret.right = node;
        ret.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        if (parent(node) != null) {
            Node parent = parent(node);
            if (parent.left == node)
                parent.left = ret;
            else
                parent.right = ret;
        }
        setParent(ret, parent(node));
        setParent(node, ret);
        setParent(node.left, node);
        return ret;
    }

    private Node splay(Node node) {
        Node parent = null;
        while ((parent = parent(node)) != null) {
            Node grandParent = parent(parent);
            if (grandParent == null) {
                if (parent.left == node)
                    rotateRight(parent);
                else
                    rotateLeft(parent);
            } else {
                if (grandParent.left == parent && parent.left == node)
                    rotateRight(rotateRight(grandParent));
                else if (grandParent.right == parent && parent.right == node)
                    rotateLeft(rotateLeft(grandParent));
                else if (parent.left == node) {
                    rotateRight(parent);
                    rotateLeft(grandParent);
                } else {
                    rotateLeft(parent);
                    rotateRight(grandParent);
                }
            }
        }
        return node;
    }

    private Node getNode(K key) {
        if (key == null)
            return null;
        Node node = root;
        Node splay = null;
        while (node != null) {
            splay = node;
            int comp = key.compareTo((K) node.key);
            if (comp < 0)
                node = node.left;
            else if (comp == 0)
                break;
            else
                node = node.right;
        }
        if (splay != null)
            root = splay(splay);
        return node;
    }
    
    private Node max(Node node) {
        while(node != null && node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    private Node min(Node node) {
        while(node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public K max() {
        root = splay(max(root));
        return root == null? null: (K)root.key;
    }

    @Override
    public K min() {
        root = splay(min(root));
        return root == null? null: (K)root.key;
    }

    @Override
    public K floor(K key) {
        K ret = super.floor(key);
        getNode(ret);
        return ret;
    }

    @Override
    public K ceiling(K key) {
        K ret = super.ceiling(key);
        getNode(ret);
        return ret;
    }

    @Override
    public int rank(K key) {
        getNode(key);
        return super.rank(key);
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : (V) node.value;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    private Node put(Node node, K key, V value) {
        if (node == null)
            return new SplayNode(key, value, null, 1);
        int comp = key.compareTo((K) node.key);
        if (comp == 0)
            node.value = value;
        else if (comp < 0) {
            node.left = put(node.left, key, value);
            setParent(node.left, node);
        } else {
            node.right = put(node.right, key, value);
            setParent(node.right, node);
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public void put(K key, V value) {
        if (key == null)
            return;
        root = put(root, key, value);
        getNode(key);
    }

    private Node join(Node left, Node right) {
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            left = splay(max(left));
            left.right = right;
            setParent(right, left);
            left.size = size(left.left) + size(left.right) + 1;
            return left;
        }
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if(node == null) {
            return null;
        }
        V ret = (V)node.value;
        setParent(root.left, null);
        setParent(root.right, null);
        root = join(root.left, root.right);
        return ret;
    }

    private class SplayNode extends Node {

        public SplayNode parent;

        public SplayNode(Object key, Object value, SplayNode parent, int size) {
            super(key, value, size);
            this.parent = parent;
        }

    }

    public static void main(String[] args) {
        SplayTree<Integer, Integer> a = new SplayTree<Integer, Integer>();
        SplayTree<Integer, Integer> b = new SplayTree<Integer, Integer>();
        a.put(3, 1);
        a.put(2, 1);
        a.put(1, 1);
        b.put(6, 1);
        b.put(5, 1);
        b.put(4, 1);
        System.out.println(a);
        System.out.println(b);
        a.root = a.join(a.root, b.root);
        System.out.println(a);
        a.remove(3);
        System.out.println(a);
    }

}
