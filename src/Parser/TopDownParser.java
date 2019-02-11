package Parser;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import SyntaxTree.Visitable;

/**
 * for whole class:
 *
 * @author Niklas Koopmann (9742503)
 **/

public class TopDownParser {
    public String string;
    public char symbol;
    public int i;

    public TopDownParser(String string) {
        this.string = string;
        symbol = string.charAt(0);
        i = 0;
    }

    private void nextSymbol() {
        i++;
        if (i < string.length())
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

    public Visitable regExp(Visitable node) {
        //nur bei 0-9, A-Z, a-z und (
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '('))
            return re(term(null));
        else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    public Visitable term(Visitable node) {
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

    public Visitable factor(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9') || (symbol == '(')) {
            return hop(elem(null));
        } else {
            throw new RuntimeException("The expression is not a regular expression!");
        }
    }

    public Visitable hop(Visitable node) {
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

    public Visitable elem(Visitable node) {
        if (symbol != '(') {
            return alphanum(null);
        } else {
            nextSymbol();
            return regExp(null);
        }

    }

    public Visitable alphanum(Visitable node) {
        if ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z') || (symbol >= '0' && symbol <= '9')) {
            Visitable opNode = new OperandNode(String.valueOf(symbol));
            nextSymbol();
            return opNode;
        }
        throw new RuntimeException("The expression is not a regular expression!");
    }

    public Visitable re(Visitable node) {
        switch (symbol) {
            case '|':
                nextSymbol();
                return re(new BinOpNode("|", node, term(null)));
            case ')':
                nextSymbol();
                return node;
        }
        throw new RuntimeException("The expression is not a regular expression!");
    }
}
