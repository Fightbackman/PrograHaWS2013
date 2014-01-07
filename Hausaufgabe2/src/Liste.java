class Liste {
	private final boolean empty; // ist diese Liste leer?
	private final int value; // das erste Element dieser Liste
	private final Liste rest; // die restliche Liste ohne das erste Element

	public Liste() { // erzeuge eine neue leere Liste
		empty = true;
		value = 0;
		rest = null;
	}

	private Liste(int elem, Liste rest) {
		this.empty = false;
		this.value = elem;
		this.rest = rest;
	}

	int size() {
		if (isEmpty()) {
			return 0;
		}
		return 1 + tail().size();
	}

	public boolean isEmpty() {
		return empty;
	}

	public int head() {
		return value;
	}

	public Liste tail() {
		return rest;
	}

	public Liste add(int elem) {
		return new Liste(elem, this);
	}

	public String toString() {
		if (isEmpty()) {
			return "";
		} else {
			return "" + head() + " " + tail().toString();
		}
	}

	public boolean contains(int element) {

		
		
		if (element == value) {
			return true;
		} else if (rest == null) {
			return false;
		} else {
			return rest.contains(element);
		}
	}


	public boolean isSorted() {
		Liste temp = this;
		int size = size();
		for (int i = 0; i <= size; i++) {
			if (temp.value > temp.rest.value) {
				return false;
			}
			temp = temp.rest;
		}
		return true;
	}
}