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
    private SyntaxNode start(char c){

        if(c == '#') return new OperandNode("#");

        else if(c == '('){

            OperandNode leaf = new OperandNode("#");

            return new BinOpNode("°", regExp(null), leaf);
        }

        else return null;
    }

    private SyntaxNode regExp();
}
