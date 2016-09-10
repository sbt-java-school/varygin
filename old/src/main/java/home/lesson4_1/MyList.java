package home.lesson4_1;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Interface for Linked List
 */
interface MyList <E> {

    /**
     * Append the specified element to the end of the list
     * @param e element to be appended to this list
     * @return <code>true</code>
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     */
    boolean add(E e);

    /**
     * Insert the specified element at the specified position of this list
     *
     * @param index
     * @param e
     * @return <code>true</code>
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    boolean add(int  index, E e);

    /**
     * Return the element at the specified position of this list
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E get(int index);

    /**
     * Remove the element with the specified index from this list
     * and return it
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt; size()</tt>)
     */
    E remove(int index);

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     *         sequence)
     */
    ListIterator<E> listIterator();
    Iterator<E> iterator();

    boolean addAll(Collection<? extends E> c);
    boolean copy(Collection<? extends E> c);
}
