package home.work;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Realisation of interface MyIterator
 * which iterate array of some objects
 */
public class MyIteratorImpl<E> implements MyIterator<E> {
    private List<E> objects;
    private E next;
    private int nextIndex;

    public MyIteratorImpl(List<E> objects) {
        this.objects = objects;
        this.next = (this.objects != null && this.objects.size() > 0) ? this.objects.get(0) : null;
        nextIndex = 0;
    }

    public boolean hasNext() {
        return (objects != null && nextIndex < objects.size());
    }

    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        next = objects.get(nextIndex);
        nextIndex++;
        return next;
    }

}
