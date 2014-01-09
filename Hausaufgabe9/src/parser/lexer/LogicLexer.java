package parser.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicLexer {

    public static final String NEG_REPRESENTATION = "!";
    public static final String OR_REPRESENTATION = "|";
    public static final String AND_REPRESENTATION = "&";

    public enum TokenType {

        TRUE("(?i)\\btrue\\b"), FALSE("(?i)\\bfalse\\b"),
        BINARYOP(Pattern.quote(OR_REPRESENTATION) + "|"
                + Pattern.quote(AND_REPRESENTATION)),
        NEGATION(NEG_REPRESENTATION), OPENPAREN("[(]"), CLOSEPAREN("[)]"),
        VARIABLE("\\b[A-z0-9]+\\b"), ERROR("[^ \\t\\f\\r\\n]+");
        public final String pattern;

        private TokenType(String pattern) {
            this.pattern = pattern;
        }
    }

    public static List<Token> lex(String input) throws LexerException {
        ArrayList<Token> tokens = new ArrayList<Token>();

        // Lexer logic begins here
        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (TokenType tokenType : TokenType.values()) {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)",
                    tokenType.name(), tokenType.pattern));
        }
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1));

        // Begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);

        matchLoop:
        while (matcher.find()) {
            for (TokenType tokenType : TokenType.values()) {
                if (matcher.group(tokenType.name()) != null) {
                    if (tokenType == TokenType.ERROR) {
                        throw new LexerException("Unexpected character at position "
                                + matcher.start() + ": "
                                + matcher.group(tokenType.name()));
                    }
                    tokens.add(new Token(tokenType,
                            matcher.group(tokenType.name()),
                            matcher.start()));
                    continue matchLoop;
                }
            }
        }

        return tokens;
    }

    public static boolean isAnd(Token token) {
        return AND_REPRESENTATION.equals(token.data);
    }

    public static boolean isOr(Token token) {
        return OR_REPRESENTATION.equals(token.data);
    }
}
