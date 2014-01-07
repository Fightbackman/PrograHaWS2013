package h16;

import java.util.*;

public class Element<E> {
	public static class Parity {
		private int value;

		public Parity(int v) {
			this.value = v;
		}

		public boolean equals(Object o) {
			return o instanceof Parity
					&& ((Parity) o).value % 2 == this.value % 2;
		}

		public int getCreationValue() {
			return this.value;
		}

		public int hashCode() {
			return this.value % 3;
		}

		public boolean isEven() {
			return this.value % 2 == 0;
		}

		public String toString() {
			return "" + this.value;
		}
	}

	public static void main(String[] args) {
		Element<Parity> e = new Element<Parity>(new Parity(1));
		e.add(new Parity(2));
		e.add(new Parity(3));
		System.out.println(e.contains(new Parity(2)));
		Set<Parity> set = new LinkedHashSet<Parity>();
		set.add(new Parity(1));
		set.add(new Parity(2));
		Parity p = new Parity(3);
		if (!set.contains(p)) {
			for (Parity o : set) {
				if (o.equals(p)) {
					System.out.println("This must not happen!");
				}
			}
		}
		set.add(p);
		set.add(new Parity(0));
		set.add(new Parity(6));
		System.out.println(set);
	}

	private Element<E> next;
	private final E value;

	public Element(E v) {
		assert (v != null) : "Value must not be null!";
		this.value = v;
		this.next = this;
	}

	public void add(E v) {
		Element<E> e = new Element<E>(v);
		e.next = this.next;
		this.next = e;
	}

	public boolean contains(Object o) {
		return this.value.equals(o) || this.next.contains(this, o);
	}

	public boolean equals(Object o) {
		return o instanceof Element
				&& this.value.equals(((Element<?>) o).value);
	}

	public Element<E> getNext() {
		return this.next;
	}

	public E getValue() {
		return this.value;
	}

	public int hashCode() {
		return 3 * this.value.hashCode();
	}

	private boolean contains(Element<E> origin, Object o) {
		return !this.equals(origin)
				&& (this.value.equals(o) || this.next.contains(origin, o));
	}
}
