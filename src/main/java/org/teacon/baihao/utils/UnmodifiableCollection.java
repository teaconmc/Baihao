package org.teacon.baihao.utils;

import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class UnmodifiableCollection<E> implements Collection<E> {
    private final Collection<E> c;

    public UnmodifiableCollection(Collection<E> c) {
        this.c = c;
    }

    public int size() {
        return c.size();
    }

    public boolean isEmpty() {
        return c.isEmpty();
    }

    public boolean contains(Object o) {
        return c.contains(o);
    }

    public Object @NonNull [] toArray() {
        return c.toArray();
    }

    public <T> T @NonNull [] toArray(T @NonNull [] a) {
        return c.toArray(a);
    }

    public <T> T[] toArray(@NonNull IntFunction<T[]> f) {
        return c.toArray(f);
    }

    public String toString() {
        return c.toString();
    }

    public @NonNull Iterator<E> iterator() {
        return new Iterator<>() {
            private final Iterator<? extends E> i = c.iterator();

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                i.forEachRemaining(action);
            }
        };
    }

    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(@NonNull Collection<?> coll) {
        return c.containsAll(coll);
    }

    public boolean addAll(@NonNull Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(@NonNull Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(@NonNull Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        c.forEach(action);
    }

    @Override
    public boolean removeIf(@NonNull Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NonNull Spliterator<E> spliterator() {
        return c.spliterator();
    }

    @Override
    public @NonNull Stream<E> stream() {
        return c.stream();
    }

    @Override
    public @NonNull Stream<E> parallelStream() {
        return c.parallelStream();
    }

    @Override
    public int hashCode() {
        return c.hashCode();
    }
}
