import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.DepthFirstIterator;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
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
    private BinOpNode bon;
    private OperandNode on;
    private UnaryOpNode uon;

    @BeforeEach
    void setUp() {

        // re-init all attributes before each test
        sv = new SecondVisitor();

        // nodes as well, just to be safe
        bon = new BinOpNode("|" ,new OperandNode("A"), new OperandNode("B"));
        on = new OperandNode("0");
        uon = new UnaryOpNode("?", new OperandNode("A"));
    }

    @Test
    void visitTreeNodes() {

        // nothing to really test here, just calling DepthFirstIterator (assuming that works flawlessly)

        // for full test coverage, just a basic test:

        on = new OperandNode("A");

        int testONposition = (int)(Math.random() * Integer.MAX_VALUE) + 1;

        on.position = testONposition;

        sv.visitTreeNodes(on);

        assertEquals(1, sv.getFollowPosTableEntries().size());

        assertEquals("A", sv.getFollowPosTableEntries().get(testONposition).symbol);
    }

    @Test
    void visitOperandNode() {

        for(int i = 48; i <= 122; i++){ // 0 ... z in ASCII

            // only for 0 ... 9 | A ... Z | a ... z
            if(i <= 57 || (i >= 65 && i <= 90) || i >= 97){

                String currentSymbol = (char)i + "";
                on = new OperandNode(currentSymbol); // re-init OperandNode
                on.position = 0;
                sv.visit(on);

                // compare symbol in SecondVisitor's FollowPosTable with actual symbol in node
                assertEquals(sv.getFollowPosTableEntries().get(0).symbol, currentSymbol);
            }
        }

        //for(int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++){ // this takes some time...
        for(int i = -100; i < 100; i++){ // this takes less time...

            on = new OperandNode("A"); // re-init OperandNode
            on.position = i;
            sv.visit(on);

            // compare position of node to current testing position
            assertEquals(sv.getFollowPosTableEntries().get(i).position, i);
        }
    }

    @Test
    void visitUnaryOpNode() {

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

        // set lastpos entry for test-OperandNodes manually

        // set nodes' positions
        int testON1Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON2Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON3Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        testON1.position = testON1Position;
        testON2.position = testON2Position;
        testON3.position = testON3Position;

        uon = new UnaryOpNode("+", testON1); // re-init UnaryOpNode

        // add lastPos nodes' positions to uon's entry
        uon.lastpos.add(testON1Position);
        uon.lastpos.add(testON2Position);
        uon.lastpos.add(testON3Position);

        // add some firstpos values to the UnaryOpNode
        OperandNode testON4 = new OperandNode("D");
        OperandNode testON5 = new OperandNode("E");
        OperandNode testON6 = new OperandNode("F");
        int testON4Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON5Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON6Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
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

        // uon now has:
        // lastpos = {testON1, testON2, testON3}
        // firstpos = {testON4, testON5, testON6}

        sv.visit(uon); // visit node

        // FollowPosTable should have 3 entries
        assertEquals(sv.getFollowPosTableEntries().size(), 3);

        // for each node in lastpos, a FollowPosTableEntry should be created
        Set<Integer> testFollowPosSet = new HashSet<Integer>();
        testFollowPosSet.add(testON4Position);
        testFollowPosSet.add(testON5Position);
        testFollowPosSet.add(testON6Position);
        for(FollowPosTableEntry entry : sv.getFollowPosTableEntries().values()) assertEquals(testFollowPosSet, entry.followpos);

        setUp(); // reset
        uon = new UnaryOpNode("*", new OperandNode("A")); // reset node with new operation

        // The visitor should do the exact same for Kleene plus and Kleene star. Therefore:

        // add lastPos nodes' positions to uon's entry
        uon.lastpos.add(testON1Position);
        uon.lastpos.add(testON2Position);
        uon.lastpos.add(testON3Position);
        uon.firstpos.add(testON4Position);
        uon.firstpos.add(testON5Position);
        uon.firstpos.add(testON6Position);

        // visit the operand nodes in the lastpos set first (to generate FollowPosTableEntries)
        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        sv.visit(uon); // visit node

        // FollowPosTable should have 3 entries
        assertEquals(sv.getFollowPosTableEntries().size(), 3);

        // followPosSet stays the same:
        for(FollowPosTableEntry entry : sv.getFollowPosTableEntries().values()) assertEquals(testFollowPosSet, entry.followpos);
    }

    @Test
    void visitBinOpNode() {

        // For all Binary Operation Nodes except concatenation (i. e. alternative), the Second Visitor should do nothing:

        // set up BinOpNode
        bon = new BinOpNode("|", new OperandNode("A"), new OperandNode("B"));

        // SecondVisitor's FollowPosTable before visiting the node
        SortedMap<Integer, FollowPosTableEntry> followPosTableEntriesBefore = sv.getFollowPosTableEntries();

        // should do nothing because operator is neither a Kleene operation nor a concatenation
        sv.visit(uon);

        // if nothing has changed, the FollowPosTables should be equal
        assertEquals(followPosTableEntriesBefore, sv.getFollowPosTableEntries());

        setUp(); // reset

        // For concatenation: Basically the same procedure as for the Kleene plus and star:

        // create lastpos-nodes for the BinaryOpNode's left child
        OperandNode testON1 = new OperandNode("A");
        OperandNode testON2 = new OperandNode("B");
        OperandNode testON3 = new OperandNode("C");

        // set node's position
        int testON1Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON2Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON3Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        testON1.position = testON1Position;
        testON2.position = testON2Position;
        testON3.position = testON3Position;

        // create left and right child for Binary Operation Node
        OperandNode testLeftChild = new OperandNode("L");
        OperandNode testRightChild = new OperandNode("R");

        // add lastPos nodes' positions to bon's left child's entry
        testLeftChild.lastpos.add(testON1Position);
        testLeftChild.lastpos.add(testON2Position);
        testLeftChild.lastpos.add(testON3Position);

        // add some firstpos values to the BinOpNode's right child
        OperandNode testON4 = new OperandNode("D");
        OperandNode testON5 = new OperandNode("E");
        OperandNode testON6 = new OperandNode("F");
        int testON4Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON5Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        int testON6Position = (int)(Math.random() * Integer.MAX_VALUE) + 1;
        testON4.position = testON4Position;
        testON5.position = testON5Position;
        testON6.position = testON6Position;
        testRightChild.firstpos.add(testON4Position);
        testRightChild.firstpos.add(testON5Position);
        testRightChild.firstpos.add(testON6Position);

        // initialize bon with left and right child
        bon = new BinOpNode("°", testLeftChild, testRightChild);

        // visit the operand nodes in the lastpos set first (to generate FollowPosTableEntries)
        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        // realistic scenario: visitor visits left child, then right child, then bon
        //sv.visit(testLeftChild);
        //sv.visit(testRightChild);

        // visit bon
        sv.visit(bon);

        // FollowPosTable should have 3 entries (for each element in the left child's lastpos-set)
        assertEquals(3, sv.getFollowPosTableEntries().size());

        // Also, the FollowPos entries should each contain testON4 through testON6
        Set<Integer> testFollowPosSet = new HashSet<Integer>();
        testFollowPosSet.add(testON4Position);
        testFollowPosSet.add(testON5Position);
        testFollowPosSet.add(testON6Position);

        // compare result of visit with expected set
        for(FollowPosTableEntry entry : sv.getFollowPosTableEntries().values()) assertEquals(testFollowPosSet, entry.followpos);
    }

    // for full test coverage:

    @Test
    void getAndSetFollowPosTableEntries() {
        SortedMap<Integer, FollowPosTableEntry> testFollowPosTable = new TreeMap<>();

        // insert random number of entries
        //for(int i = 0; i < (int)(Math.random() * Integer.MAX_VALUE); i++){ // this takes some time...
        for(int i = 0; i < (int)(Math.random() * 1000); i++){ // this takes less time...

            String randomTestString = "";

            //for(int j = 0; j < (int)(Math.random() * Integer.MAX_VALUE); j++) randomTestString += (char)((int)(Math.random() * 94) + 32); // this takes some time...
            for(int j = 0; j < (int)(Math.random() * 1000); j++) randomTestString += (char)((int)(Math.random() * 94) + 32); // this takes less time...

            FollowPosTableEntry testFPTE = new FollowPosTableEntry(i, randomTestString);

            testFollowPosTable.put(i, testFPTE);
        }

        sv.setFollowPosTableEntries(testFollowPosTable);

        assertEquals(testFollowPosTable, sv.getFollowPosTableEntries());
    }

    @Test
    void getAndSetDepthFirstIterator() {

        DepthFirstIterator testDFI = new DepthFirstIterator();

        sv.setDepthFirstIterator(testDFI);

        assertEquals(testDFI, sv.getDepthFirstIterator());
    }
}