package parser.ast;

import parser.lexer.LogicLexer;

public class AndExpression implements LogicExpression {

    private final LogicExpression a;
    private final LogicExpression b;

    public AndExpression(LogicExpression a, LogicExpression b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String string() {
        return "(" + this.a.string() + " " + LogicLexer.AND_REPRESENTATION
                + " " + this.b.string() + ")";
    }

    @Override
    public LogicExpression copy() {
        return new AndExpression(a, b);
    }

}
