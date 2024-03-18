import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.BiPredicate;

public class StudentTest
{

    BiPredicate<Integer,Integer> lessEq = (Integer x, Integer y) -> x <= y;


    @Test
    public void binomialheap_isHeap_Test()
    {
        BinomialHeap Lchild = new BinomialHeap(2, 0, null, lessEq);
        BinomialHeap Rchild = new BinomialHeap(4, 0, null, lessEq);
        PList Rlist = new PList(Rchild, null);
        PList children = new PList(Lchild, Rlist);

        BinomialHeap heap = new BinomialHeap(5, 1, children, lessEq);

        assertTrue(heap.isHeap());

        BinomialHeap Lchild2 = new BinomialHeap(7, 0, null, lessEq);
        BinomialHeap Rchild2 = new BinomialHeap(10, 0, null, lessEq);
        PList Rlist2 = new PList(Rchild2, null);
        PList children2 = new PList(Lchild2, Rlist2);

        BinomialHeap heap2 = new BinomialHeap(5, 1, children2, lessEq);

        assertFalse(heap2.isHeap());
    }

    @Test
    public void binomialqueue_isHeap_Test()
    {

    }

    @Test
    public void test() throws Exception
    {
        binomialheap_isHeap_Test();
    }

}
