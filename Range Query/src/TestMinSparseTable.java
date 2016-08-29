
public class TestMinSparseTable extends TestSparseTable {

    @Override
    protected Operation<Long> getOperation() {
        return new Min<Long>();
    }

    @Override
    protected RangeQuery<Long> getRangeQuery(Long[] array) {
        return new MinSparseTable<Long>(array);
    }

}
