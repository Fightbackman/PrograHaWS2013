package lufti.battleship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author reidl
 */
public interface BattleshipAI {
	public Point calculateNextShot(Set<Point> hit, Set<Point> missed, int size);

	public ArrayList<Ship> placeShips(ArrayList<Ship> protoypes, int size);
}
