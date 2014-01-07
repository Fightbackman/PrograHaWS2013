package lufti.kmedians;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import lufti.ui.SimpleWindow;

/**
 * @author reidl
 */
public class KMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		if( args.length != 2 ) {
			System.out.println("Usage: KMain <image folder> <k>");
			System.out.println("	image folder: the path to a folder containing images");
			System.out.println("	k: the number of clusters");
			return;
		}
		
		int k = 0;
		
		try {
			k = Integer.parseInt(args[1]);
		} catch(NumberFormatException ex) {
			System.out.println("The second argument '"+args[1]+"' is not positive a number.");
			return;
		}
		
		if( k < 1 ) {
			System.out.println("k must be greater than zero.");
			return;
		}
		
		ArrayList<BufferedImage> pictures = loadPictures(args[0]);
		
		System.out.println("Loaded " + pictures.size() + " pictures from " + args[0]);
		
		if( pictures.size() < k ) {
			System.out.println("The number of pictures must exceed k.");
			return;
		}
		
		int width = 1200;
		int height = 800;
		
		SimpleWindow win = SimpleWindow.create(width, height, 30, new Color(0x444455));
		KMediansSignsApp.attach(new KMediansSignsApp(width, height, k, 400, pictures), win, 5);
	}
	
	private static ArrayList<BufferedImage> loadPictures(String folder) {
		File picFolder = new File(folder);
		if( !picFolder.exists() || !picFolder.isDirectory() ) {
			throw new RuntimeException(folder + " is not a folder or does not exit.");
		}
		
		ArrayList<BufferedImage> res = new ArrayList<BufferedImage>();
		for (File pic : picFolder.listFiles()) {
			boolean success = false;
			try {
				BufferedImage image = ImageIO.read(pic);
				if( image != null ) {
					res.add(image);
					success = true;
				}
			} catch (IOException ex) {
			}
			
			if( !success) { 
				System.out.println("File " + pic + " could not be loaded: not an image.");
			}
		}
		return res;
	}
}
