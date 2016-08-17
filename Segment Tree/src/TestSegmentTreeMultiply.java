
public class TestSegmentTreeMultiply extends TestSegmentTree {

    @Override
    protected Operation<Long> getOperation() {
        return new Operation<Long>(){
            @Override
            public Long apply(Long a, Long b) {
                if(a == null && b == null) {
                    return null;
                } else if(a == null) {
                    return b;
                } else if(b == null) {
                    return a;
                } else {
                    return a * b;
                }
            }
        };
    }
    
}
