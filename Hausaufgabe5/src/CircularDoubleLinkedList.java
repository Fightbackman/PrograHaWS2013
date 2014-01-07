class CircularDoubleLinkedList {
	public CircularDoubleLinkedList previous, next;
	public int value;
//main methode zum testen
/*	public static void main(String[] args){
		CircularDoubleLinkedList test = new CircularDoubleLinkedList(10);
		test.testen(test);
		test.insert(10);
		test.insert(9);
		test.insert(8);
		test.insert(7);
		test.insert(6);
		test.insert(5);
		test.insert(4);
		test.insert(3);
		test.insert(2);
		test.insert(1);
		test.insert(11);
	}
*/
	
	public CircularDoubleLinkedList(int value) {
		this.value = value;
		this.previous = this;
		this.next = this;
		assert (this.checkStructure());
	}

	private CircularDoubleLinkedList(int value,
			CircularDoubleLinkedList previous, CircularDoubleLinkedList next) {
		this.value = value;
		this.previous = previous;
		this.next = next;
		this.next.previous = this;
		this.previous.next = this;
		assert (this.checkStructure());
	}

	public CircularDoubleLinkedList insert(int value) {
		assert (this.checkStructure());
		assert (this.next.checkStructure());
		CircularDoubleLinkedList elem = new CircularDoubleLinkedList(value,
				this, this.next);
		assert (this.checkStructure());
		assert (this.next.checkStructure());
		return elem;

	}

	public int size() {
		CircularDoubleLinkedList temp = this;
		int anzahl = 1;
		while (temp.next != this) {
			anzahl++;
			temp = temp.next;
		}
		return anzahl;
	}

	public boolean checkStructure() {
		try {
			if (this.previous.next != this | this.next.previous != this) {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}
