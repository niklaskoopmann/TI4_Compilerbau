package Parser;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.SyntaxNode;

public class TopDownParser {

    // Attributes
    private String regex;

    // Constructor
    public TopDownParser(String regex) {
        this.regex = regex;
    }

    // Getter methods
    public String getRegex() {
        return regex;
    }

    // Setter methods
    public void setRegex(String regex) {
        this.regex = regex;
    }

    // logic
    private SyntaxNode start(){

        if(regex.charAt(0) == '#') return new OperandNode("#");

        else if(regex.charAt(0) == '('){

            OperandNode leaf = new OperandNode("#");

            return new BinOpNode("°", regExp(null, 1), leaf);
        }

        else return null;
    }

    private SyntaxNode regExp(SyntaxNode parent, int pos){

        int charAtPosASCII = (int)regex.charAt(pos);

        if((charAtPosASCII >= 48 && charAtPosASCII <= 57) || (charAtPosASCII >= 65 && charAtPosASCII <= 90) || (charAtPosASCII >= 97 && charAtPosASCII <= 122)){ // 0 ... 9 | a ... z | A ... Z
            // todo logic
        }

        else return null;
    }

    private SyntaxNode reApostrophe(SyntaxNode parent, int pos){

    }

    private SyntaxNode term(SyntaxNode parent, int pos){

    }

    private SyntaxNode factor(SyntaxNode parent, int pos){

    }

    private SyntaxNode hOp(SyntaxNode parent, int pos){

    }

    private SyntaxNode elem(SyntaxNode parent, int pos){

    }

    private SyntaxNode alphaNum(SyntaxNode parent, int pos){

    }

}
