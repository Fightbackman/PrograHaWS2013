package lufti.kmedians;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lufti.game.AbstractGame;
import lufti.game.PlayerInput;
import lufti.ui.Canvas.CanvasPainter;

/**
 *
 * @author reidl
 */
public class KMediansSignsApp extends AbstractGame {

	private int width, height;
	private int dataWidth, dataHeight;
	private static final int MARGIN = 50;
	
	private final KMedian<BufferedImage> kMedian;
	
	private ArrayList<BufferedImage> images;
	private ArrayList<BufferedImage> centers;
	private Map<BufferedImage,Set<BufferedImage>> clustering;
	
	private int countDown;
	private final int updateDelay;
	
	public static Color[] palette;
	static {
		ArrayList<Color> res = new ArrayList<Color>();
		for (String color : "141, 211, 199; 255, 255, 179; 190, 186, 218; 251, 128, 114; 128, 177, 211; 253, 180, 98; 179, 222, 105; 252, 205, 229; 217, 217, 217; 188, 128, 189;".split(";") ) {
			String[] rgb = color.split(",");
			res.add(new Color(Integer.parseInt(rgb[0].trim()),Integer.parseInt(rgb[1].trim()),Integer.parseInt(rgb[2].trim())));
		}
		palette = res.toArray(new Color[0]);
	}

	public KMediansSignsApp(int width, int height, int k, int num, ArrayList<BufferedImage> imgs) {
		this.width = width;
		this.height = height;
		this.images = imgs;

		this.dataWidth = width - 2 * MARGIN;
		this.dataHeight = height - 2 * MARGIN;

		countDown = updateDelay = 5;
		
		Random rnd = new Random();
		kMedian = new KMedian<BufferedImage>(new PictureMetric());

		// Choose centers
		centers = new ArrayList<BufferedImage>();
		for (int i = 0; i < k; i++) {
			centers.add(images.remove(rnd.nextInt(images.size())));
		}
		images.addAll(centers);
		
		assert centers.size() == k;
		clustering = kMedian.cluster(centers, images);
	}

	@Override
	public void update(PlayerInput input) {
		if( countDown > 0) {
			countDown--;
			return;
		}
		
		System.out.println("Start clustering...");
		centers = kMedian.findCenters(clustering);
		clustering = kMedian.cluster(centers, images);
		countDown = updateDelay;
		System.out.println("...endClustering");
	}

	@Override
	public void render(CanvasPainter pntr) {
		int numClusters = clustering.entrySet().size();
		int w = width / numClusters;
		int x = 0;
		
		// Sort clusters
		ArrayList<Set<BufferedImage>> sortedClusters = new ArrayList<Set<BufferedImage>>();
		for (Map.Entry<BufferedImage, Set<BufferedImage>> cluster : clustering.entrySet()) {
			sortedClusters.add(cluster.getValue());
		}
		Collections.sort(sortedClusters, new Comparator<Set<BufferedImage>>() {
			@Override
			public int compare(Set<BufferedImage> o1, Set<BufferedImage> o2) {
				return o1.size() - o2.size();
			}
		});
		
		for (Set<BufferedImage> cluster : sortedClusters) {
			renderCluster(x, x+w, pntr.getGraphhics(), cluster);
			x += w;
		}
	}
	
	private void renderCluster(int left, int right, Graphics2D gr, Set<BufferedImage> cluster) {
		int vertDist = 5;
		int horDist = 5;
		int x = left;
		int y = 0;
		int maxHeight = 0;
		for (BufferedImage img : cluster) {
			int w = img.getWidth() + horDist;
			if( x+w > right) {
				x = left;
				y += maxHeight + vertDist;
				maxHeight = 0;
			}
			
			gr.drawImage(img, x, y, null);
				
			maxHeight = Math.max(maxHeight, img.getHeight());
			x += w;
		}
	}
}
