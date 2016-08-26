import java.util.Scanner;

public class LFUCache<K, V> implements SymbolTable<K, V>{
    
    private class Node {
        public final long frequency;
        public final Set<K> keys;
        public Node prev;
        public Node next;
        
        public Node(long frequency) {
            this.frequency = frequency;
            this.keys = new Set<>(new ChainingHashTable<>());
        }
    }
    
    private final SymbolTable<K, V> values;
    private final SymbolTable<K, Node> frequencies;
    private final Node head;
    private final int capacity;
    
    public LFUCache(int capacity) {
        if(capacity == 0) {
            throw new IllegalArgumentException("capacity is "+ capacity);
        }
        this.capacity = capacity;
        this.values = new ChainingHashTable<>();
        this.frequencies = new ChainingHashTable<>();
        this.head = new Node(0);
        head.prev = head.next = head;
    }
    
    private void insert(Node node, Node prev, Node next) {
        node.prev = prev;
        node.next = next;
        prev.next = next.prev = node;
    }
    
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = node.next = null;
    }

    private void hit(K key) {
        Node node = frequencies.get(key);
        if (node == null) {
            node = head;
            node.keys.add(key);
        }
        long frequency = node.frequency;
        if (node.prev.frequency == frequency + 1) {
            node.prev.keys.add(key);
            frequencies.put(key, node.prev);
        } else {
            Node newNode = new Node(frequency + 1);
            newNode.keys.add(key);
            insert(newNode, node.prev, node);
            frequencies.put(key, newNode);
        }
        node.keys.remove(key);
        if (node.keys.isEmpty() && node != head) {
            remove(node);
        }
//        System.out.println("key " + key +" has frequency " + frequencies.get(key).frequency);
    }

    @Override
    public void put(K key, V value) {
        if(values.size() == capacity && !values.contains(key)) {
            remove(head.prev.keys.iterator().next());
        }
        values.put(key, value);
        hit(key);
    }

    @Override
    public V get(K key) {
        if(values.contains(key)) {
            hit(key);
        }
        return values.get(key);
    }

    @Override
    public V remove(K key) {
        Node node = frequencies.get(key);
        if(node == null) {
            return null;
        } else {
    //        System.out.println("Removing " + key);
            node.keys.remove(key);
            if(node.keys.isEmpty()) {
                remove(node);
            }
            frequencies.remove(key);
            return values.remove(key);
        }
    }

    @Override
    public boolean contains(K key) {
        boolean res = values.contains(key);
        if(res) {
            hit(key);
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public Iterable<K> keys() {
        return values.keys();
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LFUCache<Integer, Integer> cache = new LFUCache<>(5);
        while(true) {
            String line = sc.nextLine();
            String[] parts = line.trim().split(" +");
            cache.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            System.out.println(cache.keys());
        }
    }

}
