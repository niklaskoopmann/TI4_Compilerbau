package SyntaxTree;

import Visitor.Visitor;

/**
 * @author Student_4
 */

public class UnaryOpNode extends SyntaxNode implements Visitable {

    public String operator;
    public Visitable subNode;

    public UnaryOpNode(String operator, Visitable subNode) {
        this.operator = operator;
        this.subNode = subNode;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "UnaryOpNode{" +
                "operator='" + operator + '\'' +
                ", subNode=" + subNode.toString() +
                '}';
    }
}
