package parser.lexer;

import parser.lexer.LogicLexer.TokenType;

public class Token {

	public TokenType type;
	public String data;
	public int pos;

	public Token(TokenType type, String data, int pos) {
		this.type = type;
		this.data = data;
		this.pos = pos;
	}

	@Override
	public String toString() {
		return String.format("(%s '%s')", type.name(), data);
	}
}
