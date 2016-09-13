package ru.sbt;

import java.util.Objects;
import java.util.function.Consumer;

interface Wrap<T> extends Consumer<T> {
    default void accept(T item) {
        throw new IllegalStateException("called wrong accept method");
    }

    static abstract class Chain<T, OUT> implements Wrap<T> {
        protected final Wrap<? super OUT> stream;

        public Chain() {
            this.stream = null;
        }

        public Chain(Wrap<? super OUT> stream) {
            this.stream = Objects.requireNonNull(stream);
        }
    }
}
