import java.util.*;

/**
 * @author Thomas Stroeder
 * @param <E> Element type.
 * Abstract implementation of sets implementing all methods of the interface Set<E> except 
 * add, clear, contains, iterator, and remove.
 */
public abstract class AbstractIterableSet<E> implements Set<E> {

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean res = false;
        for (final E elem : c) {
            res |= this.add(elem);
        }
        return res;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (o.getClass() != this.getClass()) {
            return false;
        } else {
            final AbstractIterableSet<?> set = (AbstractIterableSet<?>) o;
            return this.containsAll(set) && set.containsAll(this);
        }
    }

    @Override
    public int hashCode() {
        int res = 5;
        final int prime = 7;
        for (final E elem : this) {
            res += prime * elem.hashCode();
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean res = false;
        for (final Object o : c) {
            res |= this.remove(o);
        }
        return res;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        final List<E> store = new ArrayList<E>();
        boolean change = false;
        for (final E elem : this) {
            if (c.contains(elem)) {
                store.add(elem);
            } else {
                change = true;
            }
        }
        if (change) {
            this.clear();
            for (final E elem : store) {
                this.add(elem);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        int res = 0;
        for (final Iterator<E> it = this.iterator(); it.hasNext(); it.next()) {
            res++;
        }
        return res;
    }

    @Override
    public Object[] toArray() {
        final Object[] res = new Object[this.size()];
        int i = 0;
        for (final E elem : this) {
            res[i] = elem;
            i++;
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(final T[] a) {
        final int size = this.size();
        final T[] res;
        if (a.length < size) {
            res = Arrays.copyOf(a, size);
        } else {
            res = a;
        }
        int i = 0;
        for (final E elem : this) {
            try {
                res[i] = (T) elem;
            } catch (final ClassCastException e) {
                throw new ArrayStoreException(
                    "Element " + elem + " cannot be cast to type of specified array!");
            }
            i++;
        }
        return res;
    }

}
