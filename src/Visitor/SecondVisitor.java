package Visitor;

import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.*;

import java.util.*;

/**
 * for whole class:
 * @author NiklasKoopmann
 *
 **/

public class SecondVisitor implements Visitor{

    /*

    Second visitor to parse every node of the syntax tree in order to store and calculate the node's index, its symbol
    and its followpos values inside a table

     */

    // Attributes
    private SortedMap<Integer, FollowPosTableEntry> followPosTableEntries;
    private DepthFirstIterator depthFirstIterator;

    // Constructor
    public SecondVisitor(){

        followPosTableEntries = new TreeMap<>();
        depthFirstIterator = new DepthFirstIterator();
    }

    // traverse method
    public void visitTreeNodes(Visitable root) {

        depthFirstIterator.traverse(root, this);
    }

    // visit method
    public void visit(OperandNode node){

        FollowPosTableEntry entry = new FollowPosTableEntry(node.position, node.symbol);

        entry.followpos.clear();

        followPosTableEntries.put(node.position, entry);
    }

    public void visit(UnaryOpNode node){

        Set<Integer> followPosValues = new HashSet<>();

        // if operation is Kleene star or Kleene plus
        if(node.operator.equals("*") || node.operator.equals("+")){

            // iterate through all nodes in lastpos
            for (int lastPosValue : node.lastpos) {

                // followpos(node at lastPosValue) += firstpos(node)
                // and update entry set
                followPosTableEntries.get(lastPosValue).followpos.addAll(node.firstpos);
            }
        }
    }

    public void visit(BinOpNode node){

        // if operation is concatenation
        if(node.operator.equals("°")){

            // iterate through all nodes in lastpos of this node's left child
            for (int lastPosValue : ((SyntaxNode)node.left).lastpos) {

                // followpos(node at lastPosValue) += lastpos(right child)
                // and update entry set
                followPosTableEntries.get(lastPosValue).followpos.addAll(((SyntaxNode)node.right).lastpos);
            }
        }
    }
}
