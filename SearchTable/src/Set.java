import java.util.Iterator;

public class Set<T> implements Iterable<T> {
    
    private SymbolTable<T, ?> st;

    public Set(SymbolTable<T, ?> symbolTable) {
        if (symbolTable == null) {
            throw new NullPointerException();
        }
        this.st = symbolTable;
    }
    
    public void add(T value) {
        st.put(value, null);
    }
    
    public boolean contains(T value) {
        return st.contains(value);
    }
    
    public void remove(T value) {
        st.remove(value);
    }
    
    public int size() {
        return st.size();
    }
    
    public boolean isEmpty() {
        return st.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return st.keys().iterator();
    }
    
}
