import java.util.function.BiPredicate;

class BinomialHeap<K> {
    K key;
    int height;
    PList<BinomialHeap<K>> children;
    BiPredicate<K, K> lessEq;

    BinomialHeap(K k, int h, PList<BinomialHeap<K>> kids, BiPredicate<K, K> le) {
        this.key = k;
        this.height = h;
        this.children = kids;
        this.lessEq = le;
    }

    /*
     * @precondition this.height == other.height
     */
    BinomialHeap<K> link(BinomialHeap<K> other) {
        if (this.height != other.height)
            throw new UnsupportedOperationException("attempt to link trees of different height");
        if (lessEq.test(other.key, this.key)) {
            PList<BinomialHeap<K>> kids = PList.addFront(other, this.children);
            return new BinomialHeap<>(this.key, this.height + 1, kids, lessEq);
        } else {
            PList<BinomialHeap<K>> kids = PList.addFront(this, other.children);
            return new BinomialHeap<>(other.key, other.height + 1, kids, lessEq);
        }
    }

    /**
     * TODO
     * <p>
     * The isHeap method checks whether or not the subtree of this node
     * satisfies the heap property.
     */
    boolean isHeap()
    {
        PList<BinomialHeap<K>> curr_PList = children;
        boolean heap = true;

        while (curr_PList != null)
        {
            //Checks if the child's data is <= to the parent's key
            heap = heap && lessEq.test(PList.getFirst(curr_PList).key, this.key) && PList.getFirst(curr_PList).isHeap();

            curr_PList = PList.getNext(curr_PList);
        }

        return heap;
    }

    public String toString() {
        String ret = "(" + key.toString();
        if (children != null)
            ret = ret + " " + children.toString();
        return ret + ")";
    }

}

public class BinomialQueue<K> {
    PList<BinomialHeap<K>> forest;
    BiPredicate<K, K> lessEq;

    public BinomialQueue(BiPredicate<K, K> le) {
        forest = null;
        lessEq = le;
    }

    public void push(K key) {
        BinomialHeap<K> heap = new BinomialHeap<>(key, 0, null, lessEq);
        this.forest = insert(heap, this.forest);
    }

    public K pop() {
        BinomialHeap<K> max = PList.find_max(this.forest, (h1,h2) -> this.lessEq.test(h1.key, h2.key));
        this.forest = PList.remove(max, this.forest);
        PList<BinomialHeap<K>> kids = PList.reverse(max.children, null);
        this.forest = merge(this.forest, kids);
        return max.key;
    }

    public boolean isEmpty() {
        return forest == null;
    }

    /**
     * TODO
     * The isHeap method returns whether or not the Binomial Queue (a forest of Binomial Trees)
     * satisfies the heap property.
     */
    public boolean isHeap()
    {
        PList<BinomialHeap<K>> curr_PList = forest;
        boolean heap = true;

        while (curr_PList != null)
        {
            //Checks if the child's data is <= to the parent's key
            heap = heap && PList.getFirst(curr_PList).isHeap();

            curr_PList = PList.getNext(curr_PList);
        }

        return heap;
    }

    public String toString()
    {
        if (this.forest == null)
            return "";
        else
            return this.forest.toString();
    }

    /**********************************
     * Helper functions
     */

    /**
     * TODO
     * The insert method is analogous to binary addition. That is,
     * it inserts the node n into the list ns to produce a new list
     * that is still sorted by height.
     *
     * @param <K> The type of the keys.
     * @param n   The node to insert (must not be null).
     * @param ns  The binomial forest into which to insert, ordered by height. (may be null)
     * @return A new binomial forest that includes the new node.
     */
    static <K> PList<BinomialHeap<K>>
    insert(BinomialHeap<K> n, PList<BinomialHeap<K>> ns)
    {
        //The input heap is null, so return the original forest
        if (n == null)
            return ns;

        //The forest is empty, so create a new forest with the input heap
        if (ns == null)
            return PList.addFront(n, null);

        if (n.height < PList.getFirst(ns).height)
            return PList.addFront(n, ns);

        else if (n.height == PList.getFirst(ns).height)
        {
            return insert(n.link(PList.getFirst(ns)), PList.getNext(ns));
        }

        else
        {
            //Recursively insert the heap into the rest of the forest
            return PList.addFront(PList.getFirst(ns), insert(n, PList.getNext(ns)));
        }
    }

    /**
     * TODO
     * The merge method is analogous to the merge part of merge sort. That is,
     * it takes two lists that are sorted (by height) and returns a new list that
     * contains the elements of both lists, and the new list is sorted by height.
     *
     * @param ns1
     * @param ns2
     * @return A list that is sorted and contains all and only the elements in ns1 and ns2.
     */
    static <K> PList<BinomialHeap<K>>
    merge(PList<BinomialHeap<K>> ns1, PList<BinomialHeap<K>> ns2)
    {
        if (ns1 == null)
            return ns2;

        if (ns2 == null)
            return ns1;

        return merge(PList.getNext(ns1), insert(PList.getFirst(ns1), ns2));

//        while (ns1 != null && ns2 != null)
//        {
//            if (PList.getFirst(ns1).height < PList.getFirst(ns2).height)
//            {
//                if (result == null)
//                {
//                    result = ns1;
//                    tail = ns1;
//                }
//
//                else
//                {
//                    //PList.getNext(tail) = ns1;
//                    tail = PList.getNext(tail);
//                }
//
//                ns1 = PList.getNext(ns1);
//            }
//
//            else if (PList.getFirst(ns2).height < PList.getFirst(ns1).height)
//            {
//                if (result == null)
//                {
//                    result = ns2;
//                    tail = ns2;
//                }
//
//                else
//                {
//                    //PList.getNext(tail) = ns2;
//                    tail = PList.getNext(tail);
//                }
//
//                ns2 = PList.getNext(ns2);
//            }
//
//            else
//            {
//                BinomialHeap<>
//            }
//        }
    }
    
}