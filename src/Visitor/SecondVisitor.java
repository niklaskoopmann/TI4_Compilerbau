package Visitor;

import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private ArrayList<FollowPosTableEntry> followPosTableEntryList;
    private DepthFirstIterator depthFirstIterator;

    // Constructor
    public SecondVisitor(){

        followPosTableEntryList = new ArrayList<>();
        depthFirstIterator = new DepthFirstIterator();
    }

    // traverse method
    public ArrayList<FollowPosTableEntry> visitTreeNodes(Visitable root){

        depthFirstIterator.traverse(root, this);

        return followPosTableEntryList;
    }

    // visit method
    public void visit(OperandNode node){

        FollowPosTableEntry entry = new FollowPosTableEntry(node.position, node.symbol);

        Set<Integer> emptyFollowPosValues = new HashSet<>();

        // todo something with the followpos set

        followPosTableEntryList.add(entry);
    }

    public void visit(UnaryOpNode node){

        Set<Integer> followPosValues = new HashSet<>();

        // if operation is Kleene star or Kleene plus
        if(node.operator.equals("*") || node.operator.equals("+")){

            // iterate through all nodes in lastpos
            for (int lastPosValue : node.lastpos) {

                // followpos(node at lastPosValue) += firstpos(node)
                // and update entry set
                followPosTableEntryList.get(lastPosValue).followpos.addAll(node.firstpos);
            }
        }
    }

    public void visit(BinOpNode node){

        // if operation is concatenation
        if(node.operator.equals("°")){

            for (int lastPosValue : node) { // todo how to access left child's lastpos set?

                // followpos(node at lastPosValue) += firstpos(node)
                // and update entry set
                followPosTableEntryList.get(lastPosValue).followpos.addAll(node.firstpos);
            }
        }
    }
}
