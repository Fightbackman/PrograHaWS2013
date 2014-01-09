package parser.ast;

import parser.lexer.LogicLexer;

public class OrExpression implements LogicExpression {

    private final LogicExpression a;
    private final LogicExpression b;

    public OrExpression(LogicExpression a, LogicExpression b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String string() {
        return "(" + this.a.string() + " " + LogicLexer.OR_REPRESENTATION
                + " " + this.b.string() + ")";
    }

    @Override
    public LogicExpression copy() {
        return new OrExpression(a, b);
    }

}
