package Parser;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import SyntaxTree.Visitable;

/**
 * for whole class:
 *
 * @author Lars Roth
 **/

public class TopDownParser {
    private final String string;
    private char symbol;
    private int i;

    /**
     * Constructer initalizes parser with regular expression given by parameter
     *
     * @param string the regular expression
     */
    public TopDownParser(String string) {
        this.string = string;
        symbol = string.charAt(0);
        this.i = 0;
    }

    /**
     * This function iterates over the regular expression
     */
    private void nextSymbol() {
        this.i++;
        if (this.i < string.length())
            symbol = string.charAt(i);
    }

    /**
     * This function starts the evaluation of the regular expression and sets the end node or throws an exception
     *
     * @return Visitable object, which is the final syntax tree
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
     * This function realizes the RegExp -> Term RE in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
     */
    private Visitable regExp(Visitable node) {
        //nur bei 0-9, A-Z, a-z und (
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '('))
            return re(term(null));
        else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * This function realizes the Term -> FactorTerm or epsilon in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
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
     * This function realizes the Factor -> Elem HOp in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
     */
    private Visitable factor(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            return hop(elem(null));
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    /**
     * This function realizes the HOp rules in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
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
     * This function realizes the Elem rules in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
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
     * This function realizes the Alphanum rules in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
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
     * This function realizes the RE -> | TermRE or epsilon in the parser rules
     *
     * @param node the parent node
     * @return Visitable object, which is the syntax tree with the new node
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
