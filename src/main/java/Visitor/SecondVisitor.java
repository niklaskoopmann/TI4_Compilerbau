package Visitor;

import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.*;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Student_2
 */

public class SecondVisitor implements Visitor {

    private SortedMap<Integer, FollowPosTableEntry> followPosTableEntries;
    private DepthFirstIterator depthFirstIterator;

    /**
     * Initialize the Second Visitor
     */
    public SecondVisitor() {
        followPosTableEntries = new TreeMap<>();
        depthFirstIterator = new DepthFirstIterator();
    }

    /**
     * Traverses provided Node
     * @param root the node to be traversed
     */
    public void visitTreeNodes(Visitable root) {
        DepthFirstIterator.traverse(root, this);
    }

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(OperandNode node) {
        FollowPosTableEntry entry = new FollowPosTableEntry(node.position, node.symbol);
        entry.followpos.clear();
        followPosTableEntries.put(node.position, entry);
    }

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(UnaryOpNode node) {
        if (node.operator.equals("*") || node.operator.equals("+")) {
            for (int lastPosValue : node.lastpos) {
                followPosTableEntries.get(lastPosValue).followpos.addAll(node.firstpos);
            }
        }
    }

    /**
     * Visit a node
     * @param node the node to be visited
     */
    public void visit(BinOpNode node) {
        if (node.operator.equals("°") && !(node.left == null || node.right == null)) {
            for (int lastPosValue : ((SyntaxNode) node.left).lastpos) {
                followPosTableEntries.get(lastPosValue).followpos.addAll(((SyntaxNode) node.right).firstpos);
            }
        }
    }

    public SortedMap<Integer, FollowPosTableEntry> getFollowPosTableEntries() {
        return followPosTableEntries;
    }

    public void setFollowPosTableEntries(SortedMap<Integer, FollowPosTableEntry> followPosTableEntries) {
        this.followPosTableEntries = followPosTableEntries;
    }

    public DepthFirstIterator getDepthFirstIterator() {
        return depthFirstIterator;
    }

    public void setDepthFirstIterator(DepthFirstIterator depthFirstIterator) {
        this.depthFirstIterator = depthFirstIterator;
    }
}
