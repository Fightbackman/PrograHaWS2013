public class IntList {
	private final boolean empty; // ist diese Liste leer?
	private final int value; // das erste Element dieser Liste
	private final IntList rest; // die restliche Liste ohne das erste Element

	public IntList() { // erzeuge eine neue leere Liste
		empty = true;
		value = 0;
		rest = null;
	}

	private IntList(int elem, IntList rest) {
		this.empty = false;
		this.value = elem;
		this.rest = rest;
	}

	public boolean isEmpty() {
		return empty;
	}

	public int head() {
		return getValue();
	}

	public IntList tail() {
		return rest;
	}

	public IntList add(int elem) {
		return new IntList(elem, this);
	}

	@Override
	public String toString() {
		if (isEmpty()) {
			return "";
		} else {
			return "" + head() + " " + tail().toString();
		}
	}
	
	public void accept(IntListVisitor visitor){
		visitor.visit(this);
		IntList temp;
		temp = this;
		while(temp.rest !=null){
			temp.rest.accept(visitor);
		}
	}

	public int getValue() {
		return value;
	}
}
/*
Sie finden eine leichte Abwandlung der Listen-Klasse aus der Vorlesung auf unserer Webseite
unter dem Namen IntList . Entwerfen Sie ein Interface IntListVisitor und erweitern
Sie die Klasse IntList um eine Methode void accept(IntListVisitor visitor ), um im Sinne
des Visitor-Patterns ein solches Objekt entgegenzunehmen.
Implementieren Sie anschlieﬂend eine Klasse SummingIntListVisitor, welche dieses Interface
implementiert, um die Summe einer Liste mit Hilfe der accept -Methode zu berechnen.

*/