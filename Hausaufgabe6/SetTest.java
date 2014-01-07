import java.util.*;

/**
 * @author Thomas Stroeder Class for testing sets.
 */
public abstract class SetTest {

	/**
	 * @author Thomas Stroeder Test objects for the functional set
	 *         implementation.
	 */
	private static class TestObject {

		/**
		 * The value.
		 */
		private final int value;

		/**
		 * @param v
		 *            The value.
		 */
		public TestObject(final int v) {
			this.value = v;
		}

		@Override
		public boolean equals(final Object o) {
			if (o instanceof TestObject) {
				return ((TestObject) o).value % 2 == this.value % 2;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return this.value % 2;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}

	}

	/**
	 * @param args
	 *            The unused arguments.
	 */
	public static void main(final String[] args) {
		final Set<Integer> intSet = new FlagSet<Integer>();
		SetTest.outputSet(intSet);
		intSet.add(1);
		SetTest.outputSet(intSet);
		intSet.add(2);
		SetTest.outputSet(intSet);
		intSet.add(3);
		SetTest.outputSet(intSet);
		intSet.add(2);
		SetTest.outputSet(intSet);
		intSet.remove(1);
		SetTest.outputSet(intSet);
		intSet.remove(2);
		SetTest.outputSet(intSet);
		intSet.remove("foo");
		SetTest.outputSet(intSet);
		intSet.clear();
		SetTest.outputSet(intSet);
		intSet.add(null);
		SetTest.outputSet(intSet);
		final Set<TestObject> testSet = new FlagSet<TestObject>();
		testSet.add(new TestObject(1));
		SetTest.outputSet(testSet);
		testSet.add(new TestObject(2));
		SetTest.outputSet(testSet);
		testSet.add(new TestObject(3));
		SetTest.outputSet(testSet);
		testSet.add(new TestObject(4));
		SetTest.outputSet(testSet);
		testSet.remove(new TestObject(3));
		SetTest.outputSet(testSet);
		testSet.remove(2);
		SetTest.outputSet(testSet);
		intSet.add(3);
		intSet.add(2);
		intSet.add(5);
		intSet.add(null);
		final Iterator<Integer> it = intSet.iterator();
		while (it.hasNext()) {
			SetTest.outputSet(intSet);
			final Integer v = it.next();
			if (v == null || v % 2 == 0) {
				it.remove();
			}
		}
		SetTest.outputSet(intSet);
	}

	/**
	 * Outputs a functional set to the console.
	 * 
	 * @param set
	 *            The set to output.
	 */
	private static void outputSet(final Set<?> set) {
		final StringBuilder out = new StringBuilder("{");
		boolean first = true;
		for (final Object o : set) {
			if (first) {
				first = false;
			} else {
				out.append(",");
			}
			out.append(o);
		}
		out.append("}");
		System.out.println(out.toString());
	}

}
