
public class TestMaxSparseTable extends TestSparseTable {

    @Override
    protected Operation<Long> getOperation() {
        return new Max<Long>();
    }

    @Override
    protected RangeQuery<Long> getRangeQuery(Long[] array) {
        return new MaxSparseTable<Long>(array);
    }

}
