package Visitor;

import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author niklaskoopmann
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

        Set<Integer> thisEntryFollowPosValues = new HashSet<>();

        // todo calculate followpos shit

        followPosTableEntryList.add(entry);
    }

    public void visit(UnaryOpNode node){

        // if operation is Kleene star
        if(node.operator.equals("*")){

            for (int lastPosValue :
                    ) {
                
            }

        }

        // if operation is Kleene plus
        if(node.operator.equals("+")){

        }
    }

    public void visit(BinOpNode node){

        // if operation is concatenation
        if(node.operator.equals("°")){

        }
    }
}
