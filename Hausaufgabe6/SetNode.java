public class SetNode<E> {
	boolean active;
	private E element;
	private SetNode<E> next;

	public SetNode(E element_, SetNode<E> next_) {
		active = true;
		element = element_;
		next = next_;
	}

	public boolean getActive() {
	return this.active;
	}
	
	public E getElement(){
		return this.element;
	}
	
	public SetNode<E> getNext(){
		return this.next;
	}
	
	public void setActive(){
		this.active = true;
	}
	
	public void setInActive(){
		this.active = false;
	}
}
