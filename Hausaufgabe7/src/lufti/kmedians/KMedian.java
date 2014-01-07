package lufti.kmedians;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author reidl
 */
public class KMedian<P> {
	
	private final Metric<P> metric;
	
	public KMedian(Metric<P> metric) {
		this.metric = metric;
	}
	
	public Map<P,Set<P>> cluster(Collection<P> e, int k, int n) throws Exception {
		// Create copy of set first
		ArrayList<P> elements = new ArrayList<P>();
		elements.addAll(e);
		
		// Randomness!
		Random rnd = new Random();
		
		// Check edge cases
		if(elements.size() < k) {
			throw new Exception("k="+k+" too large for collection of size " + e.size());
		}
		
		// Choose centers randomly, temporarily remove from elements
		ArrayList<P> centers = new ArrayList<P>();
		for (int i = 0; i < k; i++) {
			int num = rnd.nextInt(elements.size());
			centers.add( elements.remove(num) );
		}
		
		// Add back as elements
		elements.addAll(centers);		
		
		// Cluster
		Map<P, Set<P>> clustering = null;
		for (int i = 0; i < n; i++) {
			clustering = cluster(centers, elements);
			centers = findCenters(clustering);
		}
		
		return clustering;
	}
	
	public Map<P,Set<P>> cluster( Collection<P> centers, Collection<P> elements) {
		HashMap<P,Set<P>> clustering = new HashMap<P,Set<P>>();
		
		for (P c : centers) {
			clustering.put(c, new HashSet<P>());
		}
		
		for (P element : elements) {
			P center = findCenter(centers, element);
			clustering.get(center).add(element);
		}
		
		return clustering;
	}

	public ArrayList<P> findCenters(Map<P, Set<P>> clustering) {
		ArrayList<P> centers = new ArrayList<P>();
		for (Map.Entry<P, Set<P>> entry : clustering.entrySet()) {
			Set<P> cluster = entry.getValue();
			centers.add(findMedian(cluster));
		}
		
		assert clustering.keySet().size() == centers.size();
		
		return centers;
	}
	
	private P findCenter(Collection<P> centers, P element) {
		assert !centers.isEmpty();
		double minDistance = Double.MAX_VALUE;
		P center = null;
		for (P c : centers) {
			double dist = metric.distance(c, element);
			if( dist < minDistance ) {
				minDistance = dist;
				center = c;
			}
		}
		return center;
	}

	private P findMedian(Set<P> cluster) {
		P center = null;
		double minDistance = Double.MAX_VALUE;
		for (P candidate : cluster) {
			double dist = 0;
			for (P other : cluster) {
				dist += metric.distance(candidate, other);
				if( dist > minDistance ) {
					break;
				}
			}
			if( dist < minDistance ) {
				minDistance = dist;
				center = candidate;
			}
		}
		return center;
	}
}
