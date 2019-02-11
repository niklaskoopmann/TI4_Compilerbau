package Parser;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import SyntaxTree.Visitable;

/**
 * for whole class:
 *
 * @author Lars Roth (4102770)
 **/

public class TopDownParser {
    private String string;
    private char symbol;
    private int i;

    public TopDownParser(String string) {
        this.string = string;
        symbol = string.charAt(0);
        this.i = 0;
    }

    private void nextSymbol() {
        this.i++;
        if (this.i < string.length())
            symbol = string.charAt(i);
    }

    public Visitable start(Visitable node) {
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

    private Visitable regExp(Visitable node) {
        //nur bei 0-9, A-Z, a-z und (
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '('))
            return re(term(null));
        else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

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

    private Visitable factor(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            return hop(elem(null));
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

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

    private Visitable elem(Visitable node) {
        if (symbol != '(') {
            return alphanum(null);
        } else {
            nextSymbol();
            return regExp(null);
        }

    }

    private Visitable alphanum(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9')) {
            Visitable opNode = new OperandNode(String.valueOf(symbol));
            nextSymbol();
            return opNode;
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

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
