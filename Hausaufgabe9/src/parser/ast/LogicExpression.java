package parser.ast;

import java.util.Map;

public interface LogicExpression {

    /**
     * Returns the value of the expression given the values of the variables.
     *
     * @param variableValues The values of the variables. The key is the name
     * of the variable.
     * @return <code>true</code> if the expression is true, <code>false</code>
     * otherwise.
     * @throws EvaluationException The expression could not be evaluated.
     */
    public boolean evaluate(Map<String, Boolean> variableValues)
            throws EvaluationException;
    

    /**
     * Returns a string representation of the expression.
     *
     * @return A string representation of the expression.
     */
    public String string();

    /**
     * Returns a copy of the expression, where there are no consecutive 
     * negations. This is always possible. An even number of negations can be
     * removed. An uneven number of negations can be replaced by a single
     * negation.
     * @return A copy of the expression with unnecessary negations removed.
     */
    public LogicExpression removeUnnecessaryNegations();
    
    /**
     * Returns a formula where only variables are negated. This can be
     * achieved by applying De Morgan's laws exhaustively and using the fact 
     * that <code>!true = false</code> and <code>!false = true</code>.
     * @return The formula simplified by using De Morgan's laws.
     */
    public LogicExpression negateByDeMorgan();
    
    /**
     * Returns a shallow copy of the object. This means that a new instance of
     * the class it is called on is instantiated, but this copy will contain the
     * same references as the object being copied.
     * @return 
     */
    public LogicExpression copy();
}
