package Visitor;

import SyntaxTree.*;

public class FirstVisitor implements Visitor {
    private DepthFirstIterator firstIterator;

    //todo: durchwanderung in Tiefensuche L-R-W den Ast
    // todo: f. blattknoten und inneren knoten nullable, firstpos und lastpos am aktuellen Knoten vermerken


    public void visit(OperandNode node) {


    }

    public void visit(BinOpNode node) {

    }

    public void visit(UnaryOpNode node) {
        // isNullable(node)

    }


    // if (isLeaf(node) &&
    //todo: blatt mit position i -> nicht nullable
    // todo: oder-knoten -> nullable c1 or c2
    // todo: konkatenationsknoten and
    // todo: plusknoten -> nullable c1
    // todo: optionsknoten = true


    // internal node = any node that is not a leaf
    // checks if node is a leaf
    // leaf = node without descendant

    private boolean isLeaf(Visitable node) {
        if (node instanceof BinOpNode) {
            if (node == null)
                return false;
            if (((BinOpNode) node).right == null && ((BinOpNode) node).left == null)
                return true;
            return false;
        } else if (node instanceof UnaryOpNode) {
            if (node == null)
                return false;
            if (((UnaryOpNode) node).subNode == null)
                return true;
            return false;
        }
        return false;
    }


    private boolean isNullable(Visitable node) {
        // todo: change
        // convert depending on type and call method for that type


        return false;
    }

    // todo: nullable setzen
    // check if node is nullable
    private boolean isUnaryNullable(UnaryOpNode node) {
        if (isLeaf(node)) {
            // leaf marked with epsilon
            if (node.operator.equals("epsilon")) {
                return true;
            }
            // leaf with position i
            // todo: position vermerken
            else {
                return false;
            }
        }
        // has one descendant
        else {
            // todo: has one descendant
            if (node.operator.equals("*") || node.operator.equals("?")) {
                return true;
            } else if (node.operator.equals("?")) {
                // todo: test if subnode is binop or uanryop and test for nullable
                if (isNullable(node.subNode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBinOpNullable(BinOpNode node) {
        if (isLeaf(node)) {
            // leaf marked with epsilon
            if (node.operator.equals("epsilon")) {
                return true;
            }
            // leaf with position i
            // todo: position vermerken
            else {
                return false;
            }
        }
        // has descendants
        else {
            if (node.operator.equals("|")) {
                if (isNullable(node.left) || isNullable(node.right)) {
                    return true;
                } else {
                    return false;
                }
            }
            //concatenation
            else if (node.operator.equals("°")) {
                if (isNullable(node.left) && isNullable(node.right)) {
                    return true;
                } else {
                    return false;
                }
            } else if (node.operator.equals("?")) {
                return true;
            }
        }
        return false;
    }
}