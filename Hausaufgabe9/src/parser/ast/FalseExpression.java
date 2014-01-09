package parser.ast;

public class FalseExpression implements LogicExpression {

    @Override
    public String string() {
        return "FALSE";
    }

    @Override
    public LogicExpression copy() {
        return new FalseExpression();
    }

}
