import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.BiPredicate;

public class StudentTest {

    BiPredicate<Integer, Integer> lessEq = (Integer x, Integer y) -> x <= y;

    @Test
    public void testEmptyQueue() {
        BinomialQueue<Integer> queue = new BinomialQueue<Integer>(lessEq);

        assertEquals("", queue.toString());
    }

    @Test
    public void testSingleElementQueue()
    {
        BinomialQueue<Integer> queue = new BinomialQueue<Integer>(lessEq);

        queue.push(5);
        assertTrue(queue.isHeap());
        assertEquals(0, PList.getFirst(queue.forest).height);

        assertEquals("(5)", queue.toString());
        assertEquals(5, queue.pop());
        assertEquals("", queue.toString());
    }

    @Test
    public void testMultipleElementsQueue()
    {
        BinomialQueue<Integer> queue = new BinomialQueue<Integer>(lessEq);

        queue.push(5);
        assertTrue(queue.isHeap());
        assertEquals(0, PList.getFirst(queue.forest).height);

        queue.push(3);
        assertTrue(queue.isHeap());
        assertEquals(1, PList.getFirst(queue.forest).height);

        queue.push(7);
        assertTrue(queue.isHeap());
        assertEquals(0, PList.getFirst(queue.forest).height);

        assertEquals("(7), (5 (3))", queue.toString());
        assertEquals(7, queue.pop());
        assertEquals(5, queue.pop());
        assertEquals(3, queue.pop());
        assertEquals("", queue.toString());
    }

    @Test
    public void testDuplicatePushes()
    {
        BinomialQueue<Integer> queue = new BinomialQueue<Integer>(lessEq);

        queue.push(5);
        assertTrue(queue.isHeap());
        queue.push(5);
        assertTrue(queue.isHeap());

        assertEquals("(5 (5))", queue.toString());
        assertTrue(queue.isHeap());

    }

    @Test
    public void testMergeEmptyForests()
    {
        BinomialQueue<Integer> queue1 = new BinomialQueue<>(lessEq);
        BinomialQueue<Integer> queue2 = new BinomialQueue<>(lessEq);

        PList<BinomialHeap<Integer>> merged = BinomialQueue.merge(queue1.forest, queue2.forest);
        assertNull(merged);
    }

    @Test
    public void testMergeOneEmptyForest()
    {
        BinomialQueue<Integer> queue1 = new BinomialQueue<>(lessEq);

        queue1.push(5);
        assertTrue(queue1.isHeap());

        BinomialQueue<Integer> queue2 = new BinomialQueue<>(lessEq);

        PList<BinomialHeap<Integer>> merged = BinomialQueue.merge(queue1.forest, queue2.forest);
        assertEquals("(5)", merged.toString());
    }

    @Test
    public void testMergeDifferentHeightForests()
    {
        BinomialQueue<Integer> queue1 = new BinomialQueue<>(lessEq);
        queue1.push(5);
        assertTrue(queue1.isHeap());
        assertEquals(0, PList.getFirst(queue1.forest).height);

        queue1.push(3);
        assertEquals(1, PList.getFirst(queue1.forest).height);
        assertTrue(queue1.isHeap());

        BinomialQueue<Integer> queue2 = new BinomialQueue<>(lessEq);
        queue2.push(7);
        assertTrue(queue2.isHeap());
        assertEquals(0, PList.getFirst(queue2.forest).height);

        queue2.push(8);
        assertTrue(queue2.isHeap());
        assertEquals(1, PList.getFirst(queue2.forest).height);

        PList<BinomialHeap<Integer>> merged = BinomialQueue.merge(queue1.forest, queue2.forest);
        assertEquals("(8 (5 (3)), (7))", merged.toString());

    }

    @Test
    public void testInsertEmptyForest()
    {
        BinomialHeap<Integer> n = new BinomialHeap<>(5, 0, null, lessEq);

        PList children = null;

        PList<BinomialHeap<Integer>> inserted = BinomialQueue.insert(n, children);

        assertEquals("(5)", BinomialQueue.insert(n, children).toString());
        assertNull(children);
    }

    @Test
    public void testInsertLowerHeightNode()
    {
        BinomialHeap<Integer> n = new BinomialHeap<>(2, 0, null, lessEq);

        BinomialHeap Lchild = new BinomialHeap(5, 1, null, lessEq);
        BinomialHeap Rchild = new BinomialHeap(4, 0, null, lessEq);

        PList Rlist = new PList(Rchild, null);

        PList children = new PList(Lchild, Rlist);

        PList<BinomialHeap<Integer>> inserted = BinomialQueue.insert(n, children);

        assertEquals("(2), (5), (4)", inserted.toString());
    }

    @Test
    public void testPushandPop()
    {
        BinomialQueue<Integer> queue = new BinomialQueue<>(lessEq);

        for (int i = 1000; i >= 1; --i)
        {
            queue.push(i);
        }

        for (int i = 1000; i >= 1; --i)
        {
            assertEquals(i, queue.pop());
        }
    }

    @Test
    public void test() throws Exception
    {
        testEmptyQueue();
        testSingleElementQueue();
        testMultipleElementsQueue();

        testMergeEmptyForests();
        testMergeOneEmptyForest();
        testMergeDifferentHeightForests();

        testInsertEmptyForest();
        testInsertLowerHeightNode();
        testPushandPop();
    }

}
