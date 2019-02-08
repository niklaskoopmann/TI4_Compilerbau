package Visitor;

import SyntaxTree.*;

public class FirstVisitor implements Visitor {
    private DepthFirstIterator firstIterator;

    //todo: durchwanderung in Tiefensuche L-R-W den Ast
    // todo: f. blattknoten und inneren knoten nullable, firstpos und lastpos am aktuellen Knoten vermerken



    public void visit(OperandNode node)
    {



    }
    public void visit(BinOpNode node){


       // if (isLeaf(node) &&
        //todo: blatt mit position i -> nicht nullable
        // todo: oder-knoten -> nullable c1 or c2
        // todo: konkatenationsknoten and
        // todo: plusknoten -> nullable c1
        // todo: optionsknoten = true
    }
    public void visit(UnaryOpNode node){
       // isNullable(node)
    }

    // internal node = any node that is not a leaf
    // checks if node is a leaf
    // leaf = node without descendant

    private <T extends SyntaxNode> boolean isLeaf (T node){
        if (node instanceof BinOpNode){
            if(node == null)
                return false;
            if(((BinOpNode) node).right == null && ((BinOpNode) node).left == null)
                return true;
            return false;
        }
        else if (node instanceof UnaryOpNode){
            if (node == null)
                return false;
            if (((UnaryOpNode) node).subNode == null)
                return true;
            return false;
        }
    }


    // check if node is nullable
    private <T extends SyntaxNode> void isUnaryNullable( T node ) {

        //  You can use Convert.ChangeType

        //SomeClass obj2 = (SomeClass)Convert.ChangeType(t, typeof(SomeClass));


        if (isLeaf(node) && (node instanceof BinOpNode || node instanceof  BinOpNode)){
            // leaf marked with epsilon
            if (node.equals("epsilon")){
                node.nullable = true;
            }
            // leaf with position i
            // todo: position vermerken
            else{
                node.nullable = false;
            }
        }

    }


/*
        if (isLeaf(node))
        {

        }
        // has descendants
        else{
            if (node.operator.equals("|")){
                if (isNullable(node.left) || isNullable(node.right))
                {
                    node.nullable = true;
                }
                else{
                    node.nullable = false;
                }
            }
            //concatenation
            else if (node.operator.equals("°")){
                if (isNullable(node.left) && isNullable(node.right)){
                    node.nullable = true;
                }
                else{
                    node.nullable = false;
                }
            }
            else if (node.operator.equals("?")){
                node.nullable = true;
            }
        }











        // one child
            if (node.operator.equals("+") && isNullable(node.subNode) {
                return true;
            }
        }
        if (node instanceof UnaryOpNode || node instanceof BinOpNode) {


            if (((BinOpNode) node).operator.equals("*") || ((UnaryOpNode) node).operator.equals("*")) {
                return true;
            }
        }
        return false;
    }*/


}
