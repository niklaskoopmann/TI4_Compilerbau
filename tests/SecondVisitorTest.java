import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.DepthFirstIterator;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.SortedMap;
import java.util.TreeMap;

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

        setUp(); // reset

        // create lastpos-nodes for UnaryOpNode
        OperandNode testON1 = new OperandNode("A");
        OperandNode testON2 = new OperandNode("B");
        OperandNode testON3 = new OperandNode("C");

        // set lastpos entry for test-OperandNode manually

        // set node's position
        int testON1Position = (int)(Math.random() * Integer.MAX_VALUE);
        int testON2Position = (int)(Math.random() * Integer.MAX_VALUE);
        int testON3Position = (int)(Math.random() * Integer.MAX_VALUE);
        testON1.position = testON1Position;
        testON2.position = testON2Position;
        testON3.position = testON3Position;

        // create FollowPosTable and FollowPosTableEntry for testOperandNode
        SortedMap<Integer, FollowPosTableEntry> testFPTE1 = new TreeMap<Integer, FollowPosTableEntry>();
        FollowPosTableEntry testEntry1 = new FollowPosTableEntry(testON1.position, testON1.symbol);

        uon = new UnaryOpNode("+", testON1); // re-init UnaryOpNode

        // add lastPos nodes' positions to uon's entry
        uon.lastpos.add(testON1Position);
        uon.lastpos.add(testON2Position);
        uon.lastpos.add(testON3Position);

        // add some firstpos values to the UnaryOpNode
        OperandNode testON4 = new OperandNode("D");
        OperandNode testON5 = new OperandNode("E");
        OperandNode testON6 = new OperandNode("F");
        int testON4Position = (int)(Math.random() * Integer.MAX_VALUE);
        int testON5Position = (int)(Math.random() * Integer.MAX_VALUE);
        int testON6Position = (int)(Math.random() * Integer.MAX_VALUE);
        testON4.position = testON4Position;
        testON5.position = testON5Position;
        testON6.position = testON6Position;
        uon.firstpos.add(testON4Position);
        uon.firstpos.add(testON5Position);
        uon.firstpos.add(testON6Position);

        // visit the operand nodes in the lastpos set first (to generate FollowPosTableEntries)
        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        sv.visit(uon); // visit node

        //
        assertEquals(sv.getFollowPosTableEntries().size(), 3);

        setUp(); // reset
        uon = new UnaryOpNode("*", new OperandNode("A"));
    }

    @Test // BinOpNode
    void visit2() {
    }
}