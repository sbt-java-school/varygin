package home.work;

import java.util.NoSuchElementException;

/**
 * Interface to iterate some array of objects
 */
public interface MyIterator<E> {

    /**
     * Returns {@code true} if the iteration has more elements.
     *
     * @return {@code true} if the iteration has more elements
     */
    boolean hasNext();


    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    E next();

}
