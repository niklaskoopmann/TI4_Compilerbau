import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.FirstVisitor;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FirstVisitorTests {


    @Test
    public void testNullableOperandNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode node = new OperandNode("a");
        OperandNode node2 = new OperandNode("epsilon");

        // assert statements
        assertFalse(tester.isOperandNullable(node));
        assertTrue(tester.isOperandNullable(node2));
    }

    @Test
    public void testBinOperandNode_1() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);


        // assert statements
        assertFalse(tester.isOperandNullable(a));
        assertFalse(tester.isOperandNullable(b));
        assertFalse(tester.isBinOpNullable(node));
    }
    @Test
    public void testBinOperandNode_2() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        // assert statements
        assertFalse(tester.isOperandNullable(a));
        assertFalse(tester.isOperandNullable(b));
        assertFalse(tester.isBinOpNullable(node));
        assertTrue(tester.isUnaryNullable(un));
        assertFalse(tester.isOperandNullable(a2));
        // 0 and 1 equals 0 in AND logic
        assertFalse(tester.isBinOpNullable(node2));
    }

    @Test
    public void testUnaryNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        // assert statements
        assertFalse(tester.isOperandNullable(a));
        assertFalse(tester.isOperandNullable(b));
        assertFalse(tester.isBinOpNullable(node));
        assertTrue(tester.isUnaryNullable(un));
    }

    // todo: test fails
    @Test
    public void testSetFirstAndLastPos(){
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        // assert statements
        assertFalse(tester.isOperandNullable(a));
        assertFalse(tester.isOperandNullable(b));



        Set<Integer> set = new HashSet<>();


        set.add(3);
        assertEquals(set,tester.setFirstAndLastPos(a));
        assertEquals(set,tester.setFirstAndLastPos(b));
        assertEquals(set,tester.setFirstAndLastPos(a2));
    }
}
