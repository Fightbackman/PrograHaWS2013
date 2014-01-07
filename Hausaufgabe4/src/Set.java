public class Set {
	final int value;
	final boolean operation;
	final Set next;

	public Set(int newValue, boolean newOp) {
		value = newValue;
		operation = newOp;
		next = null;
	}

	public Set(int newValue, boolean newOp, Set newNext) {
		value = newValue;
		operation = newOp;
		next = newNext;
	}

	public Set add(int x) {
		return (new Set(x, true, this));
	}

	public Set remove(int x) {
		return (new Set(x, false, this));
	}

	public boolean contains(int x) {
		if (x == this.value && this.operation == true) {
			return true;
		} else if (x == this.value && this.operation == false) {
			return false;
		} else if (x != this.value) {
			if (this.next != null) {
				return this.next.contains(x);
			} else {
				return false;
			}
		}
		return false;
	}

	/*
	 * H9
	 * 
	 * Erweitern Sie die Klase Set aus Aufgabe T7 indem sie zu der Klasse die
	 * Methoden intersection(Set other) und union(Set other) hinzufuegen. Die
	 * Methode intersection(Set other) soll ein Set zurueckgeben was nur die
	 * Elemente enthaelt, die sowohl in der Menge sind worauf die Methode
	 * aufgerufen wurde, als auch die Menge other. Die Methode union(Set other)
	 * soll ein Set zurueckgeben was alle Elemente aus der Menge worauf die
	 * Methode aufgerufen wurde enthaelt, als auch die Element aus der Menge
	 * other. Dabei sollen die Attribute jeder Instanz der Klasse Set, wie davor
	 * unveraenderbar sein nachdem sie im Konstruktor gesetzt wurden. Eine leere
	 * Menge kann dabei auch durch den Wert null representiert werden.
	 */

	public Set intersection(Set other) {
		Set ret = null, tempT = this;
		while (tempT != null) {
			if (other.contains(tempT.value)) {
				if (ret == null) {
					ret = new Set(value, true);
				} else {
					ret = new Set(value, true, ret);
				}
			}
			tempT = tempT.next;
		}
		return ret;
	}

	public Set union(Set other) {
		Set ret = new Set(value, true), tempT = this;
		while(tempT.next != null){
			tempT = tempT.next;
			ret = new Set(tempT.value, tempT.operation, ret);
		}

		tempT = other;
		while (tempT.next != null) {
			tempT = tempT.next;
			if (!ret.contains(tempT.value)) {
				ret = new Set(tempT.value, tempT.operation, ret);
			}
		}

		return ret;
	}
}
