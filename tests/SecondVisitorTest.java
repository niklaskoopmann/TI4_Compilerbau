import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.DepthFirstIterator;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * for whole test class:
 *
 * @author Niklas Koopmann (9742503)
 *
 */

class SecondVisitorTest {

    private SecondVisitor sv;
    private DepthFirstIterator dfi;
    private BinOpNode bon;
    private OperandNode on;
    private UnaryOpNode uon;

    @BeforeEach
    void setUp() {

        // re-init all attributes before each test
        sv = new SecondVisitor();
        dfi = new DepthFirstIterator();

        // nodes as well, just to be safe
        bon = new BinOpNode("|" ,new OperandNode("A"), new OperandNode("B"));
        on = new OperandNode("0");
        uon = new UnaryOpNode("?", new OperandNode("A"));
    }

    @Test
    void visitTreeNodes() {
    }

    @Test // OperandNode
    void visit() {

        for(int i = 48; i <= 122; i++){ // 0 ... z in ASCII

            // only for 0 ... 9 | A ... Z | a ... z
            if(i <= 57 || (i >= 65 && i <= 90) || i >= 97){

                String currentSymbol = (char)i + "";
                on = new OperandNode(currentSymbol); // re-init OperandNode
                sv.visit(on);

                // compare symbol in SecondVisitor's FollowPosTable with actual symbol in node
                assertEquals(sv.getFollowPosTableEntries().get(0).symbol, currentSymbol);
            }
        }

        for(int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++){

            on = new OperandNode("A"); // re-init OperandNode
            on.position = i;
            sv.visit(on);

            // compare position of node to current testing position
            assertEquals(sv.getFollowPosTableEntries().get(i).position, i);
        }
    }

    @Test // UnaryOpNode
    void visit1() {

        uon = new UnaryOpNode("?", new OperandNode("A")); // re-init UnaryOpNode

        // SecondVisitor's FollowPosTable before visiting the node
        SortedMap<Integer, FollowPosTableEntry> followPosTableEntriesBefore = sv.getFollowPosTableEntries();

        // should do nothing because operator is neither a Kleene operation nor a concatenation
        sv.visit(uon);

        // if nothing has changed, the FollowPosTables should be equal
        assertEquals(followPosTableEntriesBefore, sv.getFollowPosTableEntries());

        uon = new UnaryOpNode("+", new OperandNode("A")); // re-init UnaryOpNode


        uon = new UnaryOpNode("*", new OperandNode("A"));
    }

    @Test // BinOpNode
    void visit2() {
    }
}