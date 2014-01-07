package lufti.battleship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
//import sun.nio.cs.ext.ISCII91;
import sun.nio.cs.ext.*; //Verwende Java7 die Class ISCII91 ist nicht importierbar. Umgehe dies durch importieren aller Oberklassen. 

/**
 *
 * @author reidl
 */
public class AIFactory {

	public static BattleshipAI create(String name) {
		if ("random".equalsIgnoreCase(name)) {
			return new AIRandom();
		}
		return new AIDefault();
	}

	public interface Clairvoyant {
		public void tell(ArrayList<Ship> ships);
	}

	public static class AIRandom implements BattleshipAI {

		private Random random;

		public AIRandom() {
			random = new Random();
		}

		@Override
		public Point calculateNextShot(Set<Point> hit, Set<Point> missed, int size) {
			// Find a smart move
			ArrayList<Point> goodMoves = new ArrayList<Point>();
			for (Point hitPos : hit) {
				ArrayList<Point> cands = BattleshipGame.getAdjacent(hitPos, size);
				for (Point point : cands) {
					if(!hit.contains(point) && !missed.contains(point)) {
						goodMoves.add(point);
					}
				}
			}
			
			if( !goodMoves.isEmpty() ) {
				Collections.shuffle(goodMoves);
				return goodMoves.get(0);
			}
			
			// Do a random move
			Point next = randomPoint(size);
			while (hit.contains(next) || missed.contains(next)) {
				next = randomPoint(size);
			}
			return next;
		}

		private Point randomPoint(int size) {
			return new Point(random.nextInt(size), random.nextInt(size));
		}

		@Override
		public ArrayList<Ship> placeShips(ArrayList<Ship> protoypes, int size) {
			// Copy list
			ArrayList<Ship> toPlace = new ArrayList<Ship>();
			for (Ship ship : protoypes) {
				toPlace.add(ship.copy());
			}

			ArrayList<Ship> placed = new ArrayList<Ship>();
			
			// Place on right board, randomly
			Random rnd = new Random();
			while (!toPlace.isEmpty()) {
				Ship ship = toPlace.remove(0);
				ship.setPosition(new Point(rnd.nextInt(size), rnd.nextInt(size)));
				if (rnd.nextBoolean()) {
					ship.flip();
				}

				if (BattleshipGame.isValid(ship, placed, size)) {
					placed.add(ship);
				} else {
					toPlace.add(ship);
				}
			}

			return placed;
		}
	}

	public static class AIDefault implements BattleshipAI, Clairvoyant {

		private ArrayList<Ship> ships;

		@Override
		public void tell(ArrayList<Ship> ships) {
			this.ships = ships;
		}

		@Override
		public Point calculateNextShot(Set<Point> hit, Set<Point> missed, int size) {
			for (Ship ship : ships) {
				for (Point point : ship.getCoordinates()) {
					if (!hit.contains(point) && !missed.contains(point)) {
						return new Point(point);
					}
				}
			}

			// Should not even be necessary
			return new AIRandom().calculateNextShot(hit, missed, size);
		}

		@Override
		public ArrayList<Ship> placeShips(ArrayList<Ship> protoypes, int size) {
			return new AIRandom().placeShips(protoypes, size);
		}
	}
}
