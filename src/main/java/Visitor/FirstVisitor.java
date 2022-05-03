package Visitor;

import SyntaxTree.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Student_3
 */

public class FirstVisitor implements Visitor {

    private int positionCounter = 0;

    private int leafPositionCounter = 1;

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(OperandNode node) {
        node.position = leafPositionCounter;
        leafPositionCounter++;

        node.nullable = isNullable(node);

        node.firstpos.add(node.position);
        node.lastpos.add(node.position);
    }

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(BinOpNode node) {
        node.nullable = isNullable(node);

        node.firstpos.addAll(setFirstPos(node));
        node.lastpos.addAll(setLastPos(node));
    }

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(UnaryOpNode node) {
        node.nullable = isNullable(node);

        node.firstpos.addAll(setFirstPos(node));
        node.lastpos.addAll(setLastPos(node));
    }

    /**
     * Converts a node to its IntegerSet
     * @param node the node to be converted
     * @return the positions of the converted node
     */
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

    /**
     * Converts a node to its IntegerSet
     * @param node the node to be converted
     * @return the positions of the converted node
     */
    public Set<Integer> setFirstPos(UnaryOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))) {
            set = ((SyntaxNode) node.subNode).firstpos;
        }
        return set;
    }

    /**
     * Converts a node to its IntegerSet
     * @param node the node to be converted
     * @return the positions of the converted node
     */
    public Set<Integer> setLastPos(UnaryOpNode node) {
        Set<Integer> set = new HashSet<>();
        if (node.operator.equals("*") || (node.operator.equals("+") || (node.operator.equals("?")))) {
            set = ((SyntaxNode) node.subNode).lastpos;
        }
        return set;
    }

    /**
     * Converts a node to its IntegerSet
     * @param node the node to be converted
     * @return the positions of the converted node
     */
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

    /**
     * Converts a node to its IntegerSet
     * @param node the node to be converted
     * @return the positions of the converted node
     */
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

    /**
     * Checks if the node is nullable
     * @param node the node to be checked
     */
    public boolean isNullable(UnaryOpNode node) {
        return node.operator.equals("*") || node.operator.equals("?");
    }

    /**
     * Checks if the node is nullable
     * @param node the node to be checked
     */
    public boolean isNullable(BinOpNode node) {
        if (node.operator.equals("|")) {
            return (((SyntaxNode) node.left).nullable) || ((SyntaxNode) node.right).nullable;
        }
        else if (node.operator.equals("°")) {
            return (((SyntaxNode) node.left).nullable) && ((SyntaxNode) node.right).nullable;
        } else {
            return false;
        }
    }

    /**
     * Checks if the node is nullable
     * @param node the node to be checked
     */
    public boolean isNullable(OperandNode node) {
        return node.symbol.equals("epsilon");
    }

    /**
     * Traverses provided Node
     * @param root the node to be traversed
     */
    public void visitTreeNodes(Visitable root) {
        DepthFirstIterator.traverse(root, this);
    }

}
