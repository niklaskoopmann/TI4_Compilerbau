import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import SyntaxTree.Visitable;
import Visitor.FirstVisitor;
import org.junit.Test;


import java.util.*;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertEquals;

public class FirstVisitorTests {


    @Test
    public void testNullableOperandNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode node = new OperandNode("a");
        OperandNode node2 = new OperandNode("epsilon");

        // assert statements
        assertEquals(false, tester.setOperandNullable(node));
        assertEquals(true, tester.setOperandNullable(node2));
    }

    @Test
    public void testBinOperandNode_1() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);


        // assert statements
        assertEquals(false, tester.setOperandNullable(a));
        assertEquals(false, tester.setOperandNullable(b));
        assertEquals(false, tester.setBinOpNullable(node));
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
        assertEquals(false, tester.setOperandNullable(a));
        assertEquals(false, tester.setOperandNullable(b));
        assertEquals(false, tester.setBinOpNullable(node));
        assertEquals(true, tester.setUnaryNullable(un));
        assertEquals(false, tester.setOperandNullable(a2));
        // 0 and 1 equals 0 in AND logic
        assertEquals(false, tester.setBinOpNullable(node2));
    }

    @Test
    public void testUnaryNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        // assert statements
        assertEquals(false, tester.setOperandNullable(a));
        assertEquals(false, tester.setOperandNullable(b));
        assertEquals(false, tester.setBinOpNullable(node));
        assertEquals(true, tester.setUnaryNullable(un));
    }
}
