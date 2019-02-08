package Visitor;

import SyntaxTree.*;

public class FirstVisitor implements Visitor {
    private DepthFirstIterator firstIterator;
    private int positionCounter = 1;

    public void visit(OperandNode node) {
        node.position = positionCounter;
        positionCounter++;
        setOperandNullable(node);
    }

    public void visit(BinOpNode node) {
        setBinOpNullable(node);
    }

    public void visit(UnaryOpNode node) {
        setUnaryNullable(node);
    }



    // internal node = any node that is not a leaf
    // checks if node is a leaf
    // leaf = node without descendant = operand node


    // check if node is nullable
    public boolean setUnaryNullable(UnaryOpNode node) {
        // has one descendant
        if (node.operator.equals("*") || node.operator.equals("?")) {
            node.nullable = true;
            return true;
        } else if (node.operator.equals("?")) {
            // is automatically a leaf -> no children
            if (((OperandNode) node.subNode).symbol.equals("epsilon")) {
                node.nullable = true;
                return true;
            } else {
                node.nullable = false;
                return false;
            }
        }
        return false;
    }

    public boolean setBinOpNullable(BinOpNode node) {
        // has descendants
        if (node.operator.equals("|")) {

            // two operands or UnaryOp and operand or binaryop and operand
            if ((((SyntaxNode) node.left).nullable) || ((SyntaxNode) node.right).nullable) {
                node.nullable = true;
                return true;
            } else {
                node.nullable = false;
                return false;
            }
        }
        //concatenation
        else if (node.operator.equals("°")) {
            if ((((SyntaxNode) node.left).nullable) && ((SyntaxNode) node.right).nullable) {
                node.nullable = true;
                return true;
            } else {
                node.nullable = false;
                return false;
            }
        } else if (node.operator.equals("?")) {
            node.nullable = true;
            return true;
        }
        else if (node.operator.equals("*")){
            return true;
        }
        return false;
    }

    // tested
    public boolean setOperandNullable(OperandNode node){
        // operands are always leafs
        if (node.symbol.equals("epsilon")){
            node.nullable = true;
            return true;
        }
        else {
            node.nullable = false;
            return false;
        }
    }
}
// todo: firstpos + junit tests
// todo: lastpos + junit tests