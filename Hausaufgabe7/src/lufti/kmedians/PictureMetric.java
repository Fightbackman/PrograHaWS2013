package lufti.kmedians;

import java.awt.image.BufferedImage;

/**
 *
 * @author ubik
 */
public class PictureMetric implements Metric<BufferedImage> {

	@Override
	public double distance(BufferedImage a, BufferedImage b) {
		return pixelWiseSimple(a, b) * colorHist(a, b);
	}

	private static double colorHist(BufferedImage a, BufferedImage b) {
		double[] rgbA = getRGBPerc(a);
		double[] rgbB = getRGBPerc(b);
		double dr = rgbA[0]-rgbB[0];
		double dg = rgbA[1]-rgbB[1];
		double db = rgbA[2]-rgbB[2];
		return dr*dr + dg*dg + db*db;
	}
	
	private static double[] getRGBPerc(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		double[] perc = new double[3];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int col = img.getRGB(x, y);
				double alpha = ((col>>24) & 0xff)/255.0;
				perc[0] += ((col >> 16) & 0xff)/255.0 * alpha; 
				perc[1] += ((col >> 8) & 0xff)/255.0 * alpha; 
				perc[2] += (col & 0xff)/255.0 *alpha; 
			}
		}
		perc[0] /= w*h;
		perc[1] /= w*h;
		perc[2] /= w*h;
		return perc;
	}
	
	private static double pixelWiseSimple(BufferedImage a, BufferedImage b) {
		double dist = 0;
		int width = Math.min(a.getWidth(), b.getWidth());
		int height = Math.min(a.getHeight(), b.getHeight());
		int[] labA = new int[3];
		int[] labB = new int[3];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgbA = a.getRGB(x, y);
				int rgbB = b.getRGB(x, y);

				dist += rgbA == rgbB ? 0 : 1;
			}
		}

		return dist/(width*height);
	}
	
	private static double pixelWise(BufferedImage a, BufferedImage b) {
		double dist = 0;
		int width = Math.min(a.getWidth(), b.getWidth());
		int height = Math.min(a.getHeight(), b.getHeight());
		int[] labA = new int[3];
		int[] labB = new int[3];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgbA = a.getRGB(x, y);
				int rgbB = b.getRGB(x, y);
				rgb2lab((rgbA >> 16) & 0xff, (rgbA >> 8) & 0xff, rgbA & 0xff, labA);
				rgb2lab((rgbB >> 16) & 0xff, (rgbB >> 8) & 0xff, rgbB & 0xff, labB);

				dist += distance(labA, labB);
			}
		}

		return dist;
	}

	private static double distance(int[] labA, int[] labB) {
		int dl = labA[0] - labB[0];
		int da = labA[1] - labB[1];
		int db = labA[2] - labB[2];
		return Math.sqrt(dl * dl + da * da + db * db);
	}

	private static void rgb2lab(int R, int G, int B, int[] lab) {
		//Taken from http://www.brucelindbloom.com

		float r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
		float Ls, as, bs;
		float eps = 216.f / 24389.f;
		float k = 24389.f / 27.f;

		float Xr = 0.964221f;  // reference white D50
		float Yr = 1.0f;
		float Zr = 0.825211f;

		// RGB to XYZ
		r = R / 255.f; //R 0..1
		g = G / 255.f; //G 0..1
		b = B / 255.f; //B 0..1

		// assuming sRGB (D65)
		if (r <= 0.04045) {
			r = r / 12;
		} else {
			r = (float) Math.pow((r + 0.055) / 1.055, 2.4);
		}

		if (g <= 0.04045) {
			g = g / 12;
		} else {
			g = (float) Math.pow((g + 0.055) / 1.055, 2.4);
		}

		if (b <= 0.04045) {
			b = b / 12;
		} else {
			b = (float) Math.pow((b + 0.055) / 1.055, 2.4);
		}

		X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
		Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
		Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

		// XYZ to Lab
		xr = X / Xr;
		yr = Y / Yr;
		zr = Z / Zr;

		if (xr > eps) {
			fx = (float) Math.pow(xr, 1 / 3.);
		} else {
			fx = (float) ((k * xr + 16.) / 116.);
		}

		if (yr > eps) {
			fy = (float) Math.pow(yr, 1 / 3.);
		} else {
			fy = (float) ((k * yr + 16.) / 116.);
		}

		if (zr > eps) {
			fz = (float) Math.pow(zr, 1 / 3.);
		} else {
			fz = (float) ((k * zr + 16.) / 116);
		}

		Ls = (116 * fy) - 16;
		as = 500 * (fx - fy);
		bs = 200 * (fy - fz);

		lab[0] = (int) (2.55 * Ls + .5);
		lab[1] = (int) (as + .5);
		lab[2] = (int) (bs + .5);
	}
}
