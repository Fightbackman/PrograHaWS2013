import java.util.*;

/** There must not be two different countries with the same name! */
public class Country implements Comparable<Country> {
    private String name; // must not be null
    /** number of inhabitants */
    private int numInhabitants;
    /** size in square meters */
    private int size;
    public String getName() {
        return this.name;
    }
    public int getNumInhabitants() {
        return this.numInhabitants;
    }
    public int getSize() {
        return this.size;
    }
    public Country (String name, int numInhabitants, int size) {
        this.name = name;
        this.numInhabitants = numInhabitants;
        this.size = size;
    }
    public String toString() {
        return this.name + ": " +
            this.numInhabitants + " inhabitants, " +
            this.size + " square meters";
    }
    public static void main(String[] args) {
        Country czechRepublic = new Country("Tschechien", 10500000, 78867);
        Country tajikistan = new Country("Tadschikistan", 7100000, 143100);
        Country tonga = new Country("Tonga", 104000, 747);
        Country transnistria = new Country("Transnistrien", 555347, 3567);
        Country tuvalu = new Country("Tuvalu", 10000, 26);
        Set<Country> countries = new TreeSet<Country>();
        countries.add(czechRepublic);
        countries.add(tajikistan);
        countries.add(tonga);
        countries.add(transnistria);
        countries.add(tuvalu);
        // FIXME sollte die Laender in der alphabetischen
        //       Reihenfolge ihrer Namen ausgeben
        System.out.println(countries);
        Country[] sortedByInhabitants =
            countries.toArray(new Country[countries.size()]);
        // TODO sortiere sortedByInhabitants aufsteigend
        //      nach der Einwohnerzahl der Laender
        System.out.println(Arrays.toString(sortedByInhabitants));
        List<Country> sortedBySize = new LinkedList<Country>(countries);
        // TODO Sortiere sortedBySize aufsteigend nach der Flaeche der Laender.
        //      Falls zwei Laender die gleiche Flaeche haben, sortiere sie nach der
        //      Einwohnerzahl.
        System.out.println(sortedBySize);
    }
	@Override
	public int compareTo(Country o) {
		// TODO Auto-generated method stub
		return 0;
	}
}