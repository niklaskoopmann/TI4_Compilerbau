package Visitor;

import SyntaxTree.*;

import java.util.HashSet;
import java.util.Set;

public class FirstVisitor implements Visitor {
    private DepthFirstIterator firstIterator;
    private int positionCounter = 1;

    public void visit(OperandNode node) {
        node.position = positionCounter;
        positionCounter++;
        isOperandNullable(node);

        setFirstAndLastPos(node);
    }

    public void visit(BinOpNode node) {
        isBinOpNullable(node);
        setFirstPos(node);
        setLastPos(node);

    }

    public void visit(UnaryOpNode node) {
        isUnaryNullable(node);
        setFirstPos(node);
        setLastPos(node);
    }



    // internal node = any node that is not a leaf
    // checks if node is a leaf
    // leaf = node without descendant = operand node

// todo: test all
    public Set<Integer> setFirstAndLastPos(OperandNode node){
        Set<Integer> set = new HashSet<>();
        if (node.symbol.equals("epsilon")){
            set.clear();
        }
        else{
            // todo: only for test
            set.add(positionCounter);
            positionCounter++;
        }
        return set;
    }

    public Set<Integer> setFirstPos (UnaryOpNode node){
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))){
            set = ((SyntaxNode)node.subNode).firstpos;
        }
        return set;
    }

    public Set<Integer> setLastPos (UnaryOpNode node){
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))){
            set = ((SyntaxNode)node.subNode).lastpos;
        }
        return set;
    }

    public Set<Integer> setFirstPos (BinOpNode node){
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("|")){
            set = ((SyntaxNode)node.left).firstpos;
            set.addAll(((SyntaxNode)node.right).firstpos);
        }
        else if(node.operator.equals("°")){
            // todo: war c1 das linke kind?
            if (((SyntaxNode)node.left).nullable){
                set = ((SyntaxNode)node.left).firstpos;
                set.addAll(((SyntaxNode)node.right).firstpos);
            }
            else{
                set = ((SyntaxNode)node.left).firstpos;
            }
        }
        return set;
    }

    public Set<Integer> setLastPos (BinOpNode node){
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("|")){
            set = ((SyntaxNode)node.left).lastpos;
            set.addAll(((SyntaxNode)node.right).lastpos);
        }
        else if (node.operator.equals("°")){
            // todo: war c2 rechts?
            if(((SyntaxNode)node.right).nullable){
                set = ((SyntaxNode)node.left).lastpos;
                set.addAll(((SyntaxNode)node.right).lastpos);
            }
            else{
                set = (((SyntaxNode)node.right).lastpos);
            }
        }
        return set;
    }





    // check if node is nullable
    public boolean isUnaryNullable(UnaryOpNode node) {
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

    public boolean isBinOpNullable(BinOpNode node) {
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

    public boolean isOperandNullable(OperandNode node){
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