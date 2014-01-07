package h6;

public class betrag {

	int x = 0;

	public betrag() {

	}

	public void absolutwert(int x_) { //effizientere Methode
		x = Math.abs(x_);
	}
	
	public int absolutwert2(int x_){ //einfacher zu beweisende Methode
		if(x_>=0){
			return x_;
		}
		else {
			return -(x_);
		}
	}
}
