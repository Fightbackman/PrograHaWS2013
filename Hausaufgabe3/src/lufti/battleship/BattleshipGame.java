package lufti.battleship;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
import lufti.ui.Canvas.CanvasPainter;

/**
 *
 * @author reidl
 */
public class BattleshipGame extends AbstractGame {

	private static final Color COLOR_BOARD = new Color(0x331177);
	private static final Color COLOR_BOARD_BG = new Color(0x662299);
	private static final Color COLOR_SHIP = new Color(0x777777);
	private static final Color COLOR_SHIP_PLACE = new Color(0x999999);
	private static final Color COLOR_SHIP_PLACE_WRONG = new Color(0xee5555);
	
	private static final Color TEXT_COLOR = new Color(0xffffff);
	private static final Color COLOR_SHOT_MISSED = new Color(0xffffff);
	private static final Color COLOR_SHOT_HIT = new Color(0xff0000);
	private static final int SHOT_RADIUS = 8;
	private static final Font UI_FONT = new Font("Dialog", Font.BOLD, 25);
	private int numHitsToWin;
	private int textX = 40;
	private int textY = 30;
	private int fieldSize;
	private int fieldMargin;
	private int leftBoardX, leftBoardY;
	private int rightBoardX, rightBoardY;
	private int boardSize;
	private int size = 10; // 10 x 10 board
	private ArrayList<Ship> shipsToPlace = new ArrayList<Ship>();
	private ArrayList<Ship> shipsLeftBoard = new ArrayList<Ship>();
	private ArrayList<Ship> shipsRightBoard = new ArrayList<Ship>();
	private HashSet<Point> shotsPlayerMissed = new HashSet<Point>();
	private HashSet<Point> shotsPlayerHit = new HashSet<Point>();
	private HashSet<Point> shotsAIMissed = new HashSet<Point>();
	private HashSet<Point> shotsAIHit = new HashSet<Point>();
	private BattleshipAI ai;
	private boolean playersTurn = true;
	private BufferedImage boardField;

	private BattleshipGame() {
		fieldMargin = 3;
		fieldSize = 42;
		boardSize = size * (fieldSize + fieldMargin) - fieldMargin;

		rightBoardY = leftBoardY = 60;
		leftBoardX = 40;
		rightBoardX = 520;

		ai = (new Random().nextDouble() < .01) ? AIFactory.create(getClass().toString()) : AIFactory.create("Random");

		shipsToPlace.add(new Ship(5));
		shipsToPlace.add(new Ship(4));
		shipsToPlace.add(new Ship(3));
		shipsToPlace.add(new Ship(3));
		shipsToPlace.add(new Ship(2));

		numHitsToWin = 0;
		for (Ship ship : shipsToPlace) {
			numHitsToWin += ship.getSize();
		}

		shipsRightBoard = ai.placeShips(shipsToPlace, size);

		// Pre-render board field
		boardField = new BufferedImage(fieldSize, fieldSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = (Graphics2D) boardField.getGraphics();
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		gr.setColor(COLOR_BOARD);
		gr.fillRect(0, 0, fieldSize, fieldSize);
		gr.setColor(COLOR_BOARD.darker());
		gr.fillOval(fieldSize / 2 - SHOT_RADIUS, fieldSize / 2 - SHOT_RADIUS, 2 * SHOT_RADIUS, 2 * SHOT_RADIUS);

		Delay.create("flip", 10);
		Delay.create("place", 10);
		Delay.create("ai", 15);
	}

	public static BattleshipGame create() {
		return new BattleshipGame();
	}

	@Override
	public void update(PlayerInput input) {
		Delay.update();

		Point mousePos = input.getMousePosition();

		Point leftFieldCoords = getLeftCoords(mousePos);
		Point rightFieldCoords = getRightCoords(mousePos);

		if (!shipsToPlace.isEmpty()) {
			Ship currentShip = shipsToPlace.get(0);
			if (input.containsCommand(PlayerInput.Command.SPACE) && Delay.isDone("flip")) {
				currentShip.flip();
				Delay.setDelay("flip");
			}

			if (leftFieldCoords != null) {
				currentShip.setPosition(leftFieldCoords);
			}

			if (input.mouseIsDown() && Delay.isDone("place") && isValid(currentShip, shipsLeftBoard, size)) {
				shipsToPlace.remove(0);
				shipsLeftBoard.add(currentShip);
				if (ai instanceof AIFactory.Clairvoyant) {
					((AIFactory.Clairvoyant) ai).tell(shipsLeftBoard);
				}
				Delay.setDelay("place");
			}

			return;
		}

		if (gameEnded() != 0) {
			return;
		}

		if (playersTurn) {
			if (rightFieldCoords != null && input.mouseIsDown()
					&& !shotsPlayerHit.contains(rightFieldCoords) && !shotsPlayerMissed.contains(rightFieldCoords)) {
				Point shot = new Point(rightFieldCoords);
				if (shotHit(shot, shipsRightBoard)) {
					shotsPlayerHit.add(shot);
				} else {
					shotsPlayerMissed.add(shot);
				}

				playersTurn = false;
			}
		} else {
			if (Delay.isDone("ai")) {
				Point shot = ai.calculateNextShot(shotsAIHit, shotsAIMissed, size);
				assert !shotsAIHit.contains(shot) && !shotsAIMissed.contains(shot);

				if (shotHit(shot, shipsLeftBoard)) {
					shotsAIHit.add(shot);
				} else {
					shotsAIMissed.add(shot);
				}

				playersTurn = true;
				Delay.setDelay("ai");
			}
		}
	}

	private int gameEnded() {
		if (shotsPlayerHit.size() == numHitsToWin) {
			return 1;
		} else if (shotsAIHit.size() == numHitsToWin) {
			return 2;
		}
		return 0;
	}

	@Override
	public void render(CanvasPainter pntr) {
		Graphics2D gr = pntr.getGraphhics();

		gr.setColor(COLOR_BOARD);
		renderBoard(gr, leftBoardX, leftBoardY);

		gr.setColor(COLOR_BOARD);
		renderBoard(gr, rightBoardX, rightBoardY);

		gr.setColor(COLOR_SHIP);
		for (Ship ship : shipsLeftBoard) {
			renderShip(gr, ship, true);
		}

		gr.setColor(COLOR_SHOT_HIT);
		for (Point point : shotsPlayerHit) {
			renderShot(gr, point, false);
		}
		for (Point point : shotsAIHit) {
			renderShot(gr, point, true);
		}

		gr.setColor(COLOR_SHOT_MISSED);
		for (Point point : shotsPlayerMissed) {
			renderShot(gr, point, false);
		}
		for (Point point : shotsAIMissed) {
			renderShot(gr, point, true);
		}

		if (!shipsToPlace.isEmpty()) {
			Ship currentShip = shipsToPlace.get(0);
			gr.setColor(COLOR_SHIP_PLACE);
			if (!isValid(currentShip, shipsLeftBoard, size)) {
				gr.setColor(COLOR_SHIP_PLACE_WRONG);
			}
			renderShip(gr, currentShip, true);

			gr.setFont(UI_FONT);
			gr.setColor(TEXT_COLOR);
			gr.drawString("Bitte Schiffe platzieren", textX, textY);
		} else if (gameEnded() == 0) {
			gr.setFont(UI_FONT);
			gr.setColor(TEXT_COLOR);
			if (playersTurn) {
				gr.drawString("Spieler ist am Zug", textX, textY);
			} else {
				gr.drawString("Computer ist am Zug", textX, textY);
			}
		} else {
			int res = gameEnded();
			gr.setFont(UI_FONT);
			gr.setColor(TEXT_COLOR);
			if (res == 1) {
				gr.drawString("Spieler hat gewonnen!", textX, textY);
			} else {
				gr.drawString("Computer hat gewonnen!", textX, textY);
			}
		}
	}

	private void renderBoard(Graphics2D gr, int boardOffX, int boardOffY) {
		int brd = 5;
		gr.setColor(COLOR_BOARD_BG);
		gr.fillRect(boardOffX - brd, boardOffY - brd, boardSize + 2 * brd, boardSize + 2 * brd);

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				Rectangle rect = getField(x, y);
				gr.drawImage(boardField, boardOffX + rect.x, boardOffY + rect.y, null);
			}
		}
	}

	private Rectangle getField(Point p, boolean leftField) {
		return getField(p.x, p.y, leftField);
	}

	private Rectangle getField(int x, int y, boolean leftField) {
		Rectangle res = getField(x, y);
		if (leftField) {
			res.x += leftBoardX;
			res.y += leftBoardY;
		} else {
			res.x += rightBoardX;
			res.y += rightBoardY;
		}
		return res;
	}

	private Rectangle getField(int x, int y) {
		return new Rectangle(x * (fieldSize + fieldMargin), y * (fieldSize + fieldMargin), fieldSize, fieldSize);
	}

	private Point getLeftCoords(Point screenPos) {
		if (!isInLeftField(screenPos)) {
			return null;
		}
		return new Point((screenPos.x - leftBoardX) / (fieldSize + fieldMargin), (screenPos.y - leftBoardY) / (fieldSize + fieldMargin));
	}

	private Point getRightCoords(Point screenPos) {
		if (!isInRightField(screenPos)) {
			return null;
		}
		return new Point((screenPos.x - rightBoardX) / (fieldSize + fieldMargin), (screenPos.y - rightBoardY) / (fieldSize + fieldMargin));
	}

	private boolean isInLeftField(Point p) {
		return p.x >= leftBoardX && p.x < leftBoardX + boardSize
				&& p.y >= leftBoardY && p.y < leftBoardY + boardSize;
	}

	private boolean isInRightField(Point p) {
		return p.x >= rightBoardX && p.x < rightBoardX + boardSize
				&& p.y >= rightBoardY && p.y < rightBoardY + boardSize;
	}

	/*
	 * Renders a ship on the _left_ board (our board)
	 */
	private void renderShip(Graphics2D gr, Ship ship, boolean leftField) {
		int thinner = 6;
		int shorter = 8;
		
		Color col = gr.getColor();
		Color darker = col.darker().darker();

		Point pos = ship.getPosition();
		Rectangle orig = getField(pos, leftField);
		if (ship.isHorizontal()) {
			orig.width = (fieldSize + fieldMargin) * ship.getSize() - fieldMargin;
			orig.y += thinner;
			orig.x += shorter;
			orig.height -= 2 * thinner;
			orig.width -= 2 * shorter;
		} else {
			orig.height = (fieldSize + fieldMargin) * ship.getSize() - fieldMargin;
			orig.y += shorter;
			orig.x += thinner;
			orig.height -= 2 * shorter;
			orig.width -= 2 * thinner;
		}

		gr.setColor(new Color(0x88111155, true));
		gr.fillRoundRect(orig.x+5, orig.y+5, orig.width, orig.height, 5, 5);		
		
		gr.setColor(col);
		gr.fillRoundRect(orig.x, orig.y, orig.width, orig.height, 5, 5);		
		
		for (Point point : ship.getCoordinates()) {
			Rectangle field = getField(point, leftField);
			int midX = field.x + field.width / 2;
			int midY = field.y + field.height / 2;
		
			int r = SHOT_RADIUS;
			gr.setColor(darker);
			gr.fillOval(midX - r, midY - r, 2 * r, 2 * r);

			// r = (int) (r * .65);
			int off = (int) (r * .25);
			gr.setColor(col);
			gr.fillOval(midX - r + off, midY - r + off, 2 * r, 2 * r);

			gr.setColor(col);
			//gr.fillOval(midX - r, midY - r, 2 * r, 2 * r);
		}
	}

	private void renderShot(Graphics2D gr, Point shot, boolean leftField) {
		Rectangle field = getField(shot.x, shot.y, leftField);
		int midX = field.x + field.width / 2;
		int midY = field.y + field.height / 2;

		Color col = gr.getColor();

		int r = SHOT_RADIUS;
		gr.fillOval(midX - r, midY - r, 2 * r, 2 * r);


		r = (int) (r * .65);
		int off = (int) (r * .5);
		gr.setColor(col.darker().darker());
		gr.fillOval(midX - r + off, midY - r + off, 2 * r, 2 * r);

		gr.setColor(col);
		gr.fillOval(midX - r, midY - r, 2 * r, 2 * r);
	}

	private boolean shotHit(Point shot, ArrayList<Ship> ships) {
		for (Ship ship : ships) {
			if (ship.isHitBy(shot)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValid(Ship ship, Collection<Ship> otherShips, int size) {
		for (Point point : ship.getCoordinates()) {
			if (!isValid(point, size)) {
				return false;
			}
		}

		for (Ship otherShip : otherShips) {
			if (ship.toCloseTo(otherShip)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValid(Point p, int size) {
		return p.x >= 0 && p.x < size && p.y >= 0 && p.y < size;
	}
	
	public static ArrayList<Point> getAdjacent(Point p, int size) {
		ArrayList<Point> candidates = new ArrayList<Point>();
		candidates.add(new Point(p.x+1,p.y));
		candidates.add(new Point(p.x-1,p.y));
		candidates.add(new Point(p.x,p.y+1));
		candidates.add(new Point(p.x,p.y-1));
		
		ArrayList<Point> res = new ArrayList<Point>();
		for (Point cand : candidates) {
			if(isValid(cand, size)) {
				res.add(cand);
			}
		}
		return res;
	}
}
