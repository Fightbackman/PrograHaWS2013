package parser.ast;

public class VariableExpression implements LogicExpression {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }
    
    @Override
    public String string() {
        return this.variableName;
    }

    @Override
    public LogicExpression copy() {
        return new VariableExpression(variableName);
    }
    
}
