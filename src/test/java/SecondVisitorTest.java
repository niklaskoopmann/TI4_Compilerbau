import DFAGeneration.FollowPosTableEntry;
import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.DepthFirstIterator;
import Visitor.SecondVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Student_2
 */

class SecondVisitorTest {

    private SecondVisitor sv;
    private BinOpNode bon;
    private OperandNode on;
    private UnaryOpNode uon;

    @BeforeEach
    void setUp() {

        sv = new SecondVisitor();

        bon = new BinOpNode("|", new OperandNode("A"), new OperandNode("B"));
        on = new OperandNode("0");
        uon = new UnaryOpNode("?", new OperandNode("A"));
    }

    @Test
    void visitTreeNodes() {

        on = new OperandNode("A");

        int testONposition = (int) (Math.random() * Integer.MAX_VALUE) + 1;

        on.position = testONposition;

        sv.visitTreeNodes(on);

        assertEquals(1, sv.getFollowPosTableEntries().size());

        Assertions.assertEquals("A", sv.getFollowPosTableEntries().get(testONposition).symbol);
    }

    @Test
    void visitOperandNode() {

        for (int i = 48; i <= 122; i++) {

            if (i <= 57 || (i >= 65 && i <= 90) || i >= 97) {

                String currentSymbol = (char) i + "";
                on = new OperandNode(currentSymbol);
                on.position = 0;
                sv.visit(on);

                Assertions.assertEquals(sv.getFollowPosTableEntries().get(0).symbol, currentSymbol);
            }
        }

        for (int i = -10000; i < 10000; i++) {

            on = new OperandNode("A");
            on.position = i;
            sv.visit(on);

            Assertions.assertEquals(sv.getFollowPosTableEntries().get(i).position, i);
        }
    }

    @Test
    void visitUnaryOpNode() {

        uon = new UnaryOpNode("?", new OperandNode("A"));

        SortedMap<Integer, FollowPosTableEntry> followPosTableEntriesBefore = sv.getFollowPosTableEntries();

        sv.visit(uon);

        assertEquals(followPosTableEntriesBefore, sv.getFollowPosTableEntries());

        setUp();

        OperandNode testON1 = new OperandNode("A");
        OperandNode testON2 = new OperandNode("B");
        OperandNode testON3 = new OperandNode("C");


        int testON1Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON2Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON3Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        testON1.position = testON1Position;
        testON2.position = testON2Position;
        testON3.position = testON3Position;

        uon = new UnaryOpNode("+", testON1);

        uon.lastpos.add(testON1Position);
        uon.lastpos.add(testON2Position);
        uon.lastpos.add(testON3Position);

        OperandNode testON4 = new OperandNode("D");
        OperandNode testON5 = new OperandNode("E");
        OperandNode testON6 = new OperandNode("F");
        int testON4Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON5Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON6Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        testON4.position = testON4Position;
        testON5.position = testON5Position;
        testON6.position = testON6Position;
        uon.firstpos.add(testON4Position);
        uon.firstpos.add(testON5Position);
        uon.firstpos.add(testON6Position);

        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        sv.visit(uon);

        assertEquals(sv.getFollowPosTableEntries().size(), 3);

        Set<Integer> testFollowPosSet = new HashSet<Integer>();
        testFollowPosSet.add(testON4Position);
        testFollowPosSet.add(testON5Position);
        testFollowPosSet.add(testON6Position);
        for (FollowPosTableEntry entry : sv.getFollowPosTableEntries().values())
            assertEquals(testFollowPosSet, entry.followpos);

        setUp();
        uon = new UnaryOpNode("*", new OperandNode("A"));

        uon.lastpos.add(testON1Position);
        uon.lastpos.add(testON2Position);
        uon.lastpos.add(testON3Position);
        uon.firstpos.add(testON4Position);
        uon.firstpos.add(testON5Position);
        uon.firstpos.add(testON6Position);

        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        sv.visit(uon);

        assertEquals(sv.getFollowPosTableEntries().size(), 3);

        for (FollowPosTableEntry entry : sv.getFollowPosTableEntries().values()) {
            assertEquals(testFollowPosSet, entry.followpos);
        }
    }

    @Test
    void visitBinOpNode() {

        bon = new BinOpNode("|", new OperandNode("A"), new OperandNode("B"));

        SortedMap<Integer, FollowPosTableEntry> followPosTableEntriesBefore = sv.getFollowPosTableEntries();

        sv.visit(uon);

        assertEquals(followPosTableEntriesBefore, sv.getFollowPosTableEntries());

        setUp();

        OperandNode testON1 = new OperandNode("A");
        OperandNode testON2 = new OperandNode("B");
        OperandNode testON3 = new OperandNode("C");

        int testON1Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON2Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON3Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        testON1.position = testON1Position;
        testON2.position = testON2Position;
        testON3.position = testON3Position;

        OperandNode testLeftChild = new OperandNode("L");
        OperandNode testRightChild = new OperandNode("R");

        testLeftChild.lastpos.add(testON1Position);
        testLeftChild.lastpos.add(testON2Position);
        testLeftChild.lastpos.add(testON3Position);

        OperandNode testON4 = new OperandNode("D");
        OperandNode testON5 = new OperandNode("E");
        OperandNode testON6 = new OperandNode("F");
        int testON4Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON5Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        int testON6Position = (int) (Math.random() * Integer.MAX_VALUE) + 1;
        testON4.position = testON4Position;
        testON5.position = testON5Position;
        testON6.position = testON6Position;
        testRightChild.firstpos.add(testON4Position);
        testRightChild.firstpos.add(testON5Position);
        testRightChild.firstpos.add(testON6Position);

        bon = new BinOpNode("°", testLeftChild, testRightChild);

        sv.visit(testON1);
        sv.visit(testON2);
        sv.visit(testON3);

        sv.visit(bon);

        assertEquals(3, sv.getFollowPosTableEntries().size());

        Set<Integer> testFollowPosSet = new HashSet<Integer>();
        testFollowPosSet.add(testON4Position);
        testFollowPosSet.add(testON5Position);
        testFollowPosSet.add(testON6Position);

        for (FollowPosTableEntry entry : sv.getFollowPosTableEntries().values()) {
            assertEquals(testFollowPosSet, entry.followpos);
        }
    }

    @Test
    void getAndSetFollowPosTableEntries() {
        SortedMap<Integer, FollowPosTableEntry> testFollowPosTable = new TreeMap<>();

        for (int i = 0; i < (int) (Math.random() * 10000); i++) { // this takes less time...

            String randomTestString = "";

            for (int j = 0; j < (int) (Math.random() * 10000); j++)
                randomTestString += (char) ((int) (Math.random() * 94) + 32); // this takes less time...

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