package Parser;

import SyntaxTree.*;

/**
 *
 * @author NiklasKoopmann
 *
 * Feel free to correct this implementation (it has never been tested) and claim it as yours.
 *
 */

public class TopDownParser {

    // Attributes
    private String regex;
    private Visitable syntaxTreeRoot;

    // Constructor
    public TopDownParser(String regex) {
        this.regex = regex;
    }

    // Getter methods
    public String getRegex() {
        return regex;
    }

    public Visitable getSyntaxTreeRoot() {
        return syntaxTreeRoot;
    }

    // Setter methods
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setSyntaxTreeRoot(Visitable syntaxTreeRoot) {
        this.syntaxTreeRoot = syntaxTreeRoot;
    }

    // logic
    private Visitable start(){

        if(regex.charAt(0) == '#') return new OperandNode("#");

        else if(regex.charAt(0) == '('){

            OperandNode leaf = new OperandNode("#");

            return new BinOpNode("°", regExp(null, 1), leaf);
        }

        else return null;
    }

    private Visitable regExp(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z | (
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122) || regex.charAt(pos) == '('){
            return reApostrophe(term(null, pos + 1), pos + 1);
        }

        else return null;
    }

    private Visitable reApostrophe(Visitable parent, int pos){

        if(regex.charAt(pos) == '|'){

            BinOpNode root = new BinOpNode("|", parent, term(null, pos + 1));

            return reApostrophe(root, pos + 1);
        }

        else if(regex.charAt(pos) == ')') return parent;

        else return null;
    }

    private Visitable term(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z | (
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122) || regex.charAt(pos) == '('){

            if(parent != null){

                BinOpNode root = new BinOpNode("°", parent, factor(null, pos + 1));

                return term(root, pos + 1);
            }

            else return term(factor(null, pos + 1), pos + 1);
        }

        else if(regex.charAt(pos) == ')' || regex.charAt(pos) == '|') return parent;

        else return null;
    }

    private Visitable factor(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z | (
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122) || regex.charAt(pos) == '('){

            return hOp(elem(null, pos + 1), pos + 1);
        }

        else return null;
    }

    private Visitable hOp(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z | '(' | '|'
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122) || regex.charAt(pos) == '(' ||
                regex.charAt(pos) == '|'){

            return parent;
        }

        else if(regex.charAt(pos) == '*') return new UnaryOpNode("*", parent);
        else if(regex.charAt(pos) == '+') return new UnaryOpNode("+", parent);
        else if(regex.charAt(pos) == '?') return new UnaryOpNode("?", parent);

        else return null;
    }

    private Visitable elem(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122)){

            return alphaNum(null, pos + 1);
        }

        else if(regex.charAt(pos) == '(') return regExp(null, pos + 1);

        else return null;
    }

    private Visitable alphaNum(Visitable parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        // 0 ... 9 | a ... z | A ... Z | (
        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) ||
                (charAtPosASCII >= 97 && charAtPosASCII <= 122)){

            return new OperandNode(regex.charAt(pos) + "");
        }

        else return null;
    }
}