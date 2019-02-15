/**
 * @author Lisa Krauß
 */

package Visitor;

import SyntaxTree.*;

import java.util.HashSet;
import java.util.Set;

public class FirstVisitor implements Visitor {
    // all objects share same variable
    private static int positionCounter = 0;
    private int leafPositionCounter = 1;


    public void visit(OperandNode node) {
        node.position = leafPositionCounter;
        leafPositionCounter++;

        node.nullable = isOperandNullable(node);

        // node.firstpos.addAll(setFirstAndLastPos(node));
        // node.lastpos.addAll(setFirstAndLastPos(node));

        node.firstpos.add(node.position);
        node.lastpos.add(node.position);
    }

    public void visit(BinOpNode node) {
        node.nullable = isBinOpNullable(node);

        node.firstpos.addAll(setFirstPos(node));
        node.lastpos.addAll(setLastPos(node));
    }

    public void visit(UnaryOpNode node) {
        node.nullable = isUnaryNullable(node);

        node.firstpos.addAll(setFirstPos(node));
        node.lastpos.addAll(setLastPos(node));
    }


    public Set<Integer> setFirstAndLastPos(OperandNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.symbol.equals("epsilon")) {
            set.clear();
        } else {
            positionCounter++;
            set.add(positionCounter);
        }
        return set;
    }

    public Set<Integer> setFirstPos(UnaryOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))) {
            set = ((SyntaxNode) node.subNode).firstpos;
        }
        return set;
    }

    public Set<Integer> setLastPos(UnaryOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))) {
            set = ((SyntaxNode) node.subNode).lastpos;
        }
        return set;
    }

    public Set<Integer> setFirstPos(BinOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("|")) {
            set.addAll(((SyntaxNode) node.left).firstpos);
            set.addAll(((SyntaxNode) node.right).firstpos);
        } else if (node.operator.equals("°")) {
            if (((SyntaxNode) node.left).nullable) {
                set.addAll(((SyntaxNode) node.left).firstpos);
                set.addAll(((SyntaxNode) node.right).firstpos);
            } else {
                set.addAll(((SyntaxNode) node.left).firstpos);
            }
        }
        return set;
    }

    public Set<Integer> setLastPos(BinOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("|")) {
            set = ((SyntaxNode) node.left).lastpos;
            set.addAll(((SyntaxNode) node.right).lastpos);
        } else if (node.operator.equals("°")) {
            if (((SyntaxNode) node.right).nullable) {
                set = ((SyntaxNode) node.left).lastpos;
                set.addAll(((SyntaxNode) node.right).lastpos);
            } else {
                set = (((SyntaxNode) node.right).lastpos);
            }
        }
        return set;
    }

    // check if node is nullable
    public boolean isUnaryNullable(UnaryOpNode node) {
        // has one descendant
        return node.operator.equals("*") || node.operator.equals("?");
    }

    public boolean isBinOpNullable(BinOpNode node) {
        // has descendants
        if (node.operator.equals("|")) {

            // two operands or UnaryOp and operand or binaryop and operand
            return (((SyntaxNode) node.left).nullable) || ((SyntaxNode) node.right).nullable;
        }
        // concatenation
        else if (node.operator.equals("°")) {
            return (((SyntaxNode) node.left).nullable) && ((SyntaxNode) node.right).nullable;
        } else {
            return false;
        }
    }

    public boolean isOperandNullable(OperandNode node) {
        // operands are always leafs
        return node.symbol.equals("epsilon");
    }

    // traverse method
    public void visitTreeNodes(Visitable root) {

        DepthFirstIterator.traverse(root, this);
    }
}
