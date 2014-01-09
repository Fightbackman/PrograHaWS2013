package parser.ast;

public class NegationExpression implements LogicExpression {
    private final LogicExpression negatedValue;

    public NegationExpression(LogicExpression negatedValue) {
        this.negatedValue = negatedValue;
    }

    @Override
    public String string() {
        return "!" + this.negatedValue.string();
    }

    @Override
    public LogicExpression copy() {
        return new NegationExpression(negatedValue);
    }
    
}
