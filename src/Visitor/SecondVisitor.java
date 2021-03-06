package Visitor;

import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.*;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * for whole class:
 *
 * @author Niklas Koopmann
 **/

public class SecondVisitor implements Visitor {

    /*

    Second visitor to parse every node of the syntax tree in order to store and calculate the node's index, its symbol
    and its followpos values inside a table

     */

    // Attributes
    private SortedMap<Integer, FollowPosTableEntry> followPosTableEntries;
    private DepthFirstIterator depthFirstIterator;

    // Constructor
    public SecondVisitor() {

        followPosTableEntries = new TreeMap<>();
        depthFirstIterator = new DepthFirstIterator();
    }

    // traverse method
    public void visitTreeNodes(Visitable root) {

        DepthFirstIterator.traverse(root, this);
    }

    // visit methods

    public void visit(OperandNode node) {

        FollowPosTableEntry entry = new FollowPosTableEntry(node.position, node.symbol);

        entry.followpos.clear();

        followPosTableEntries.put(node.position, entry);
    }

    public void visit(UnaryOpNode node) {

        // if operation is Kleene star or Kleene plus
        if (node.operator.equals("*") || node.operator.equals("+")) {

            // iterate through all nodes in lastpos
            for (int lastPosValue : node.lastpos) {

                // followpos(node at lastPosValue) += firstpos(node)
                // and update entry set
                followPosTableEntries.get(lastPosValue).followpos.addAll(node.firstpos);
            }
        }
    }

    public void visit(BinOpNode node) {

        // if operation is concatenation
        if (node.operator.equals("°") && !(node.left == null || node.right == null)) {

            // iterate through all nodes in lastpos of this node's left child
            for (int lastPosValue : ((SyntaxNode) node.left).lastpos) {

                // followpos(node at lastPosValue) += lastpos(right child)
                // and update entry set
                followPosTableEntries.get(lastPosValue).followpos.addAll(((SyntaxNode) node.right).firstpos);
            }
        }
    }

    // Getter methods

    public SortedMap<Integer, FollowPosTableEntry> getFollowPosTableEntries() {
        return followPosTableEntries;
    }

    public void setFollowPosTableEntries(SortedMap<Integer, FollowPosTableEntry> followPosTableEntries) {
        this.followPosTableEntries = followPosTableEntries;
    }

    // Setter methods

    public DepthFirstIterator getDepthFirstIterator() {
        return depthFirstIterator;
    }

    public void setDepthFirstIterator(DepthFirstIterator depthFirstIterator) {
        this.depthFirstIterator = depthFirstIterator;
    }
}
