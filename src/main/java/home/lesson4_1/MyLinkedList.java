package home.lesson4_1;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by LL on 15.08.2016.
 */
public class MyLinkedList<E> implements MyList<E> {

    transient int size = 0;

    transient Node<E> first;
    transient Node<E> last;

    /**
     * Constructs an empty list.
     */
    public MyLinkedList() {
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean add(int index, E e) {
        checkPositionIndex(index);

        if (index == size) {
            linkLast(e);
        } else {
            insertBefore(e, node(index));
        }

        return true;
    }

    @Override
    public E get(int index) {
        checkPositionIndex(index);
        return node(index).item;
    }

    @Override
    public E remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
    }

    private E unlink(Node<E> curr) {
        E element = curr.item;
        final Node<E> pred = curr.prev;
        final Node<E> nexd = curr.next;

        if (pred == null) {
            first = nexd;
        } else {
            pred.next = nexd;
            curr.prev = null;
        }

        if (nexd == null) {
            last = pred;
        } else {
            nexd.prev = pred;
            curr.next = null;
        }

        curr.item = null;
        size--;
        modCount++;
        return element;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        ListItr(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            checkForComodification();
            if (!hasPrevious())
                throw new NoSuchElementException();

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
            expectedModCount++;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            checkForComodification();
            lastReturned.item = e;
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                insertBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (modCount == expectedModCount && nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] buff = c.toArray();
        if (buff.length == 0) {
            return false;
        }

        Node<E> pred = last;

        for (Object o : buff) {
            E item = (E) o;
            Node<E> newNode = new Node<>(pred, item, null);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        size += buff.length;
        modCount++;
        return true;
    }

    @Override
    public boolean copy(Collection<? extends E> c) {
        if (c.toArray().length == 0) {
            return false;
        }

        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
        return addAll(c);
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private void insertBefore(E e, Node<E> node) {
        final Node<E> curr = node.prev;
        final Node<E> newNode = new Node<>(curr, e, node);
        node.prev = newNode;
        if (curr == null) {
            first = newNode;
        } else {
            curr.next = newNode;
        }
        size++;
        modCount++;
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) {
            Node<E> curr = first;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr;
        } else {
            Node<E> curr = last;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
            return curr;
        }
    }

    protected transient int modCount = 0;
}
