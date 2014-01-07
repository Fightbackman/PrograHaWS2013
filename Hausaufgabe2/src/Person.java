public class Person {
	Person boss;
	String name;

	public static void main(String[] args) {
		Person joe = new Person("Joe");
		Person hamlet = new Person("Hamlet");
		Person maggie = new Person("Maggie");
		Person julia = new Person("Julia");
		Person oliver = new Person("Oliver");
		// Maggie’s department
		joe.setBoss(hamlet);
		hamlet.setBoss(maggie);
		// Oliver’s department
		julia.setBoss(oliver);
		System.out.println(joe.getDepartmentBoss().getName()); // Should be
																// Maggie
		System.out.println(oliver.getDepartmentBoss().getName()); // Should be
																	// Oliver
		// Merge
		joe.takeOverDepartmentOf(julia);
		System.out.println(oliver.getDepartmentBoss().getName()); // Should be
																	// Maggie
	}

	public Person(String name_) {
		name = name_;
		boss = null;
	}

	public void setBoss(Person boss_) {
		if (this.boss == null) {
			this.boss = boss_;
		}
	}

	public String getName() {
		return this.name;
	}

	public Person getBoss() {
		if (this.boss != null) {
			return this.boss;
		}
		return null;

	}

	public Person getDepartmentBoss() {
		Person temp = this;
		while (temp.boss != null) {
			temp = temp.boss;
		}
		return temp;
	}

	public void takeOverDepartmentOf(Person other) {
		other.boss.boss = this.boss;
	}
}
