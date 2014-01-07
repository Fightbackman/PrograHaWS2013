package lufti.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;

/**
 * This class represents the different commands that the player performed during
 * one iteration of the main loop of the game. Every command can only be
 * performed once and thus it is modeled as a set, i.e. The only information
 * that can be gathered is if the command was sent, not how often.
 */
public class PlayerInput implements KeyListener, MouseListener, MouseMotionListener {

	private final HashSet<Command> commands = new HashSet<Command>();
	private boolean mouseDown = false;
	private int mouseX, mouseY;


	/**
	 * This enum represents every possible command the player can perform.
	 */
	public enum Command {
		LEFT, RIGHT, SPACE
	}

	/**
	 * Returns true if the given command was performed.
	 */
	public boolean containsCommand(Command cmd) {
		return commands.contains(cmd);
	}
	
	public boolean mouseIsDown() {
		return mouseDown;
	}
	
	public Point getMousePosition() {
		return new Point(mouseX, mouseY);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int vk = e.getKeyCode();
		if (vk == KeyEvent.VK_LEFT) {
			commands.add(Command.LEFT);
		} else if (vk == KeyEvent.VK_RIGHT) {
			commands.add(Command.RIGHT);
		} else if (vk == KeyEvent.VK_SPACE) {
			commands.add(Command.SPACE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int vk = e.getKeyCode();
		if (vk == KeyEvent.VK_LEFT) {
			commands.remove(Command.LEFT);
		} else if (vk == KeyEvent.VK_RIGHT) {
			commands.remove(Command.RIGHT);
		} else if (vk == KeyEvent.VK_SPACE) {
			commands.remove(Command.SPACE);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not needed
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseDown = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}	
}
