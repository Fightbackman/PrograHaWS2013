public class Foo {
	abstract class A {
		void f() {
			System.out.println("A.f");
		}

		void h() {
			System.out.println("A.h");
		}
	}

	abstract class B extends A {
		void f() {
			System.out.println("B.f ");
		}

		abstract void f(int i);

		abstract void h();
	}

	class C extends A {
		void f(int i) {
			System.out.println("C.f(int)");
		}

		void h() {
			super.h();
		}
	}

	class D extends B {
		void f(int i) {
			System.out.println("D.f (int)");
		}

		void h() {
			System.out.println("D.h");
		}
	}

	public void bar() {
		// B b = new A(); Kann kein Objekt einer Abstrakten Klasse erzeugen
		/*
		 * b.f(); b.h(); b = new B(); Kann die abstrakten Methoden nicht direkt
		 * aufrufen. b.f(0); b.h();
		 */
		A c = new C();
		// c.f(0); Will die Methode void f() in class A aufrufen. Diese verfügt
		// über keine Argumente von Typ int
		// A ist abstract
		c.h();
		B a = new D();
		a.f();
		a.h();
	}

	public static void main(String[] args) {
		Foo prog = new Foo();
		prog.bar();

		
	}
}

/*
 * 1. Siehe Kommentare oben 
 * 2. 
 * In A: void f void h 
 * In B: void h void f 1mal void f ist überladen und einmal zusätzlich abstract
 * In C: void f void h wobei nur auf die Methode von A verwiesen wird
 * In D: void h void f
 * 
 * 
 * 
 * 3. Ausgabe nach Entfernen der Fehler: A.h B.f und D.h
 */
