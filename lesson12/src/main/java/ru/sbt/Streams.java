package ru.sbt;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T_IN, T_OUT> extends PipeLine<T_IN, T_OUT> {

    private Streams(List<T_OUT> source) {
        super(source);
    }

    private Streams(PipeLine<?, T_IN> upstream) {
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

    public Streams<T_OUT, T_OUT> filter(Predicate<? super T_OUT> predicate) {
        Objects.requireNonNull(predicate);
        return new Streams<T_OUT, T_OUT>(this) {
            Wrap<T_OUT> unWrap(Wrap<T_OUT> wrap) {
                return new Wrap.Chain<T_OUT, T_OUT>(wrap) {
                    @Override
                    public void accept(T_OUT item) {
                        if (predicate.test(item)) {
                            stream.accept(item);
                        }
                    }
                };
            }
        };
    }

    public <R> Streams<T_OUT, R> transform(Function<? super T_OUT, ? extends R> mapper) {
        return new Streams<T_OUT, R>(this) {
            Wrap<T_OUT> unWrap(Wrap<R> wrap) {
                return new Wrap.Chain<T_OUT, R>(wrap) {
                    @Override
                    public void accept(T_OUT item) {
                        stream.accept(mapper.apply(item));
                    }
                };
            }
        };
    }

    /**
     * Terminate operation for creating Map from stream
     *
     * @param keyMapper   lambda for keys
     * @param valueMapper lambda for values
     * @param <K>         type of key
     * @param <V>         type of value param
     * @return HashMap<K,V>
     */
    public <K, V> Map<K, V> toMap(Function<? super T_OUT, ? extends K> keyMapper,
                                  Function<? super T_OUT, ? extends V> valueMapper) {
        Objects.requireNonNull(keyMapper);
        Objects.requireNonNull(valueMapper);
        HashMap<K, V> hashMap = new HashMap<>();
        acceptAll(processWrap(new Wrap.Chain<T_OUT, T_OUT>() {
            @Override
            public void accept(T_OUT item) {
                hashMap.put(keyMapper.apply(item), valueMapper.apply(item));
            }
        }));
        return hashMap;
    }

    /**
     * Terminate operation for iterate source of stream
     *
     * @param action - operation for apply to each element of stream source
     */
    public void forEach(Consumer<? super T_OUT> action) {
        Objects.requireNonNull(action);

        acceptAll(processWrap(new Wrap.Chain<T_OUT, T_OUT>() {
            public void accept(T_OUT item) {
                action.accept(item);
            }
        }));
    }
}
