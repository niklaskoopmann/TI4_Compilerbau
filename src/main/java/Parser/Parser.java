package Parser;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import SyntaxTree.Visitable;

/**
 * @author Student_4
*/

public class Parser {
    private final String input;
    private char symbol;
    private int position;

    /**
     * Initialize the Parser
     * @param string the regular expression
     */
    public Parser(String string) {
        this.input = string;
        symbol = string.charAt(0);
        this.position = 0;
    }

    /**
     * Iterator of the Symbols
     */
    private void nextSymbol() {
        this.position++;
        if (this.position < input.length())
            symbol = input.charAt(position);
    }

    /**
     * Starts the Parsing process
     * @return the parsed expression as a {@link Visitable}
     */
    public Visitable start() {
        switch (symbol) {
            case '#':
                return new OperandNode("#");
            case '(':
                nextSymbol();
                return new BinOpNode("°", regExp(null), new OperandNode("#"));
            default:
                throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * Validates Regular expressions
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable regExp(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            return re(term(null));
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * FactorTerm or epsilon
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable term(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            Visitable termReturn;
            if (node != null) {
                termReturn = term(new BinOpNode("°", node, factor(null)));
            } else {
                termReturn = term(factor(null));
            }
            return termReturn;
        } else if (symbol == '|' || symbol == ')') {
            return node;
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * Elem HOp
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable factor(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            return hop(elem(null));
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * HOp rules
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable hop(Visitable node) {
        switch (symbol) {
            case '*':
                nextSymbol();
                return new UnaryOpNode("*", node);
            case '+':
                nextSymbol();
                return new UnaryOpNode("+", node);
            case '?':
                nextSymbol();
                return new UnaryOpNode("?", node);
            default:
                return node;
        }
    }

    /**
     * Elem rules
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable elem(Visitable node) {
        if (symbol != '(') {
            return alphanum(null);
        } else {
            nextSymbol();
            return regExp(null);
        }

    }

    /**
     * Numbers and Chars
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable alphanum(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9')) {
            Visitable opNode = new OperandNode(String.valueOf(symbol));
            nextSymbol();
            return opNode;
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * TermRE or epsilon
     * @param node parent node
     * @return parsed object as {@link Visitable}
     */
    private Visitable re(Visitable node) {
        switch (symbol) {
            case '|':
                nextSymbol();
                return re(new BinOpNode("|", node, term(null)));
            case ')':
                nextSymbol();
                return node;
            default:
                throw new RuntimeException("The expression is not a regular expression!");
        }
    }
}
