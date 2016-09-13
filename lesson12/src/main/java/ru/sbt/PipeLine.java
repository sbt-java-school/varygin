package ru.sbt;

import java.util.List;
import java.util.Objects;

abstract class PipeLine<P_IN, E_OUT> {
    private List<E_OUT> source;
    private PipeLine next;
    private PipeLine current;
    private PipeLine prev;
    private int depth;

    PipeLine(List<E_OUT> source) {
        this.source = source;
        this.prev = null;
        this.current = this;
        this.depth = 0;
    }

    PipeLine(PipeLine<?, P_IN> previous) {
        previous.next = this;

        this.prev = previous;
        this.current = previous.current;
        this.depth = previous.depth + 1;
    }

    @SuppressWarnings("unchecked")
    final <E_IN> Wrap<E_IN> wrapWrap(Wrap<E_OUT> wrap) {
        Objects.requireNonNull(wrap);

        for (@SuppressWarnings("rawtypes") PipeLine p = PipeLine.this; p.depth > 0; p = p.prev) {
            wrap = p.unWrap(wrap);
        }
        return (Wrap<E_IN>) wrap;
    }

    Wrap<P_IN> unWrap(Wrap<E_OUT> wrap) {
        throw new IllegalStateException();
    }

    public List<E_OUT> getSource() {
        return this.current.source;
    }
}
