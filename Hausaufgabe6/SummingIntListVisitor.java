
public class SummingIntListVisitor implements IntListVisitor {
	int sum;
public SummingIntListVisitor() {
	sum = 0;
} 

public void visit(IntList List){
	sum = sum + List.getValue();
}
}
