package ru.sbt;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<IN, OUT> extends PipeLine<IN, OUT> {

    Streams(List<OUT> source) {
        super(source);
    }

    Streams(PipeLine<?, IN> upstream) {
        super(upstream);
    }

    public static <E> Streams<?, E> of(List<E> list) {
        return new Streams<>(Objects.requireNonNull(list));
    }

    @SafeVarargs
    public static <E> Streams<?, E> of(E... list) {
        List<E> items = Arrays.asList(Objects.requireNonNull(list));
        return new Streams<>(items);
    }

    public Streams<OUT, OUT> filter(Predicate<? super OUT> predicate) {
        Objects.requireNonNull(predicate);
        return new Streams<OUT, OUT>(this) {
            Wrap<OUT> unWrap(Wrap<OUT> wrap) {
                return new Wrap.Chain<OUT, OUT>(wrap) {
                    @Override
                    public void accept(OUT item) {
                        if (predicate.test(item)) {
                            stream.accept(item);
                        }
                    }
                };
            }
        };
    }

    public void forEach(Consumer<? super OUT> action) {
        Objects.requireNonNull(action);
        for (OUT in : this.getSource()) {
            wrapWrap(new Wrap.Chain<OUT, OUT>() {
                         public void accept(OUT item) {
                             action.accept(item);
                         }
                     }
            ).accept(in);
        }
    }

    public <R> Streams<OUT, R> transform(Function<? super OUT, ? extends R> mapper) {
        return new Streams<OUT, R>(this) {
            Wrap<OUT> unWrap(Wrap<R> wrap) {
                return new Wrap.Chain<OUT, R>(wrap) {
                    @Override
                    public void accept(OUT item) {
                        stream.accept(mapper.apply(item));
                    }
                };
            }
        };
    }

    public <K, V> Map<K, V> toMap(Function<? super OUT, ? extends K> keyMapper,
                                  Function<? super OUT, ? extends V> valueMapper) {
        Objects.requireNonNull(keyMapper);
        Objects.requireNonNull(valueMapper);
        HashMap<K, V> kvHashMap = new HashMap<>();
        for (OUT in : this.getSource()) {
            wrapWrap(new Wrap.Chain<OUT, OUT>() {
                         @Override
                         public void accept(OUT item) {
                             kvHashMap.put(keyMapper.apply(item), valueMapper.apply(item));
                         }
                     }
            ).accept(in);
        }
        return kvHashMap;
    }
}
