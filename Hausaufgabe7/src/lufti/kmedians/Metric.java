package lufti.kmedians;

/**
 * A metric over some class P
 * @author reidl
 */
public interface Metric<P> {
	public double distance(P a, P b);
}
