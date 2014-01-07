package lufti.battleship;

import java.awt.Point;

/**
 * Diese Klasse repräsentiert eine Schiff auf dem Spielfeld.
 * 
 * @author reidl
 */
public class Ship {

	private int size;
	private boolean horizontal = true;
	private Point position = new Point(-1, -1);

	public Ship(int size) {
		this.size = size;
	}

	/**
	 * Wechselt Orientierung des Schiffs
	 */
	public void flip() {
		horizontal = !horizontal;
	}

	/**
	 * Setzt das Schiff an die Position pos
	 * 
	 * @param pos
	 */
	public void setPosition(Point pos) {
		position.x = pos.x;
		position.y = pos.y;
	}

	/**
	 * @return Die Position des Schiffs als Point
	 */
	public Point getPosition() {
		return new Point(position);
	}

	/**
	 * @return Alle Felder, die das Schiff belegt, als Point-Array
	 */
	public Point[] getCoordinates() {
		Point[] res = new Point[size];
		int x = position.x;
		int y = position.y;
		if (horizontal) {
			for (int i = 0; i < size; i++) {
				res[i] = new Point(x++, y);
			}
		} else {
			for (int i = 0; i < size; i++) {
				res[i] = new Point(x, y++);
			}
		}

		return res;
	}

	/**
	 * @return True wenn das Schiff horizontal orientiert ist
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * Gibt true zurück, wenn diese Schiff zu nahe am Schiff otherShip liegt.
	 * Dies ist dann der Fall, wenn nicht mindestens ein Feld zwischen den
	 * Schiffen liegt---allerdings dürfen sich die Schiffe "diagonal"
	 * berühren.
	 * 
	 * @param otherShip
	 * @return True wenn beide Schiffe zu nah aneinander liegen
	 */
	public boolean toCloseTo(Ship otherShip) {
		Point[] resShip1 = getCoordinates();
		Point[] resShip2 = otherShip.getCoordinates();
		for (int i = 0; i < resShip1.length; i++) {
			for (int j = 0; j < resShip2.length; j++) {
				if (((resShip1[i].x + 1 == resShip2[j].x) || (resShip1[i].x - 1 == resShip2[j].x))
						&& resShip1[i].y == resShip2[j].y) {
					return true;
				} else if (resShip1[i].x == resShip2[j].x
						&& ((resShip1[i].y + 1 == resShip2[j].y) || (resShip1[i].y - 1 == resShip2[j].y))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Testet, ob dieses Schiff von dem Schuß auf die Position shot getroffen
	 * wird.
	 * 
	 * @param shot
	 *            Die Position des Schusses als Point
	 * @return True wenn das Schiff getroffen wird
	 */
	public boolean isHitBy(Point shot) {
		if (shot.x == this.position.x && shot.y == this.position.y) {
			return true;
		}
		if (shot.x - this.position.x <= size - 1
				&& shot.y - this.position.y == 0 && horizontal
				&& shot.x - this.position.x > 0) {
			return true;
		} else if (shot.x - this.position.x == 0
				&& shot.y - this.position.y <= size - 1 && !horizontal
				&& shot.y - this.position.y > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return eine Kopie des Schiffs.
	 */
	public Ship copy() {
		Ship res = new Ship(size);
		res.horizontal = horizontal;
		res.position = new Point(position);
		return res;
	}

	@Override
	public String toString() {
		return "Ship{" + "size=" + size + ", horizontal=" + horizontal
				+ ", position=" + position + '}';
	}

	/**
	 * @return Die Länge des Schiffs in Feldern
	 */
	public int getSize() {
		return size;
	}
}
