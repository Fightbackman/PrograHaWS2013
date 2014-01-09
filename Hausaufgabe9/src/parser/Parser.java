package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import parser.ast.AndExpression;
import parser.ast.EvaluationException;
import parser.ast.FalseExpression;
import parser.ast.LogicExpression;
import parser.ast.NegationExpression;
import parser.ast.OrExpression;
import parser.ast.TrueExpression;
import parser.ast.VariableExpression;
import parser.lexer.LexerException;
import parser.lexer.LogicLexer;
import parser.lexer.Token;

public class Parser {

    private final Stack<Token> tokens;

    private Parser(List<Token> tokens) {
        this.tokens = new Stack<Token>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            this.tokens.push(tokens.get(i));
        }
    }

    /**
     * Generates an AST from a boolean formula given as a String. The nodes of
     * the AST are instances of the classes in the package
     * <code>parser.ast</code>.
     *
     * @param input The string representing the boolean formula
     * @return An AST representing the boolean formula.
     * @throws LexerException Illegal characters in the input.
     * @throws ParserException The string is not possible to parse.
     */
    public static LogicExpression parse(String input)
            throws LexerException, ParserException {
        List<Token> tokens = LogicLexer.lex(input);
        Parser parser = new Parser(tokens);
        LogicExpression res = parser.parse();
        if (parser.tokens.isEmpty()) {
            return res;
        } else {
            Token unexpected = parser.tokens.peek();
            throw new ParserException("Unexpected input '"
                    + unexpected + "' at position " + unexpected.pos);
        }
    }

    private LogicExpression parse() throws ParserException {
        LogicExpression a = this.parseValue();
        Token op = null;
        LogicExpression b = null;
        LogicExpression res = a;

        while (!this.tokens.isEmpty()
                && this.tokens.peek().type != LogicLexer.TokenType.CLOSEPAREN) {
            Token nextOp = this.tokens.peek();
            if (nextOp.type != LogicLexer.TokenType.BINARYOP) {
                throw new ParserException("Unexpected input '" + nextOp
                        + "' at position " + nextOp.pos);
            } else {
                this.tokens.pop();
                if (b == null) {
                    // First operator.
                    op = nextOp;
                    b = parseValue();
                } else {
                    if (LogicLexer.isAnd(op) || LogicLexer.isOr(nextOp)) {
                        // current operator has precedence over next operator.
                        a = getBinaryOp(a, b, op);
                        op = nextOp;
                        b = parseValue();
                    } else {
                        // next operator has precedence over current operator.
                        b = getBinaryOp(b, parseValue(), nextOp);
                    }
                }
            }
            res = getBinaryOp(a, b, op);
        }
        return res;
    }

    private LogicExpression parseValue() throws ParserException {
        if (tokens.empty()) {
            throw new ParserException("Unexpected end of input.");
        }
        Token nextToken = tokens.pop();

        if (nextToken.type == LogicLexer.TokenType.TRUE) {
            return new TrueExpression();
        } else if (nextToken.type == LogicLexer.TokenType.FALSE) {
            return new FalseExpression();
        } else if (nextToken.type == LogicLexer.TokenType.VARIABLE) {
            return new VariableExpression(nextToken.data);
        } else if (nextToken.type == LogicLexer.TokenType.NEGATION) {
            LogicExpression negatedValue = this.parseValue();
            return new NegationExpression(negatedValue);
        } else if (nextToken.type == LogicLexer.TokenType.OPENPAREN) {
            LogicExpression value = this.parse();
            if (this.tokens.empty()) {
                throw new ParserException("Unexpected end of input, "
                        + "close paren expected.");
            } else if (this.tokens.peek().type == LogicLexer.TokenType.CLOSEPAREN) {
                this.tokens.pop();
                return value;
            } else {
                throw new ParserException("Close parent expected, '"
                        + this.tokens.peek() + "' found at position "
                        + this.tokens.peek().pos);
            }
        } else {
            throw new ParserException("Unexpected operator '" + nextToken
                    + "' at position " + nextToken.pos);
        }
    }

    private LogicExpression getBinaryOp(LogicExpression a, LogicExpression b,
            Token token) throws ParserException {
        if (LogicLexer.isAnd(token)) {
            return new AndExpression(a, b);
        } else if (LogicLexer.isOr(token)) {
            return new OrExpression(a, b);
        } else {
            throw new ParserException("No operation defined for operator: "
                    + token.data);
        }
    }
}
