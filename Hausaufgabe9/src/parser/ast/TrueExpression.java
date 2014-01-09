package parser.ast;

public class TrueExpression implements LogicExpression {

    public TrueExpression() {
    }

    @Override
    public String string() {
        return "TRUE";
    }

    @Override
    public LogicExpression copy() {
        return new TrueExpression();
    }
    
}
