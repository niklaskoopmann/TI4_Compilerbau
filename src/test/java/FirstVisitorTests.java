import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;
import Visitor.FirstVisitor;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Student_3
 */

public class FirstVisitorTests {

    @Test
    public void testNullableOperandNode() {
        FirstVisitor tester = new FirstVisitor();
        OperandNode node = new OperandNode("a");
        OperandNode node2 = new OperandNode("epsilon");


        assertFalse(tester.isNullable(node));
        assertTrue(tester.isNullable(node2));
    }

    @Test
    public void testBinOperandNode_1() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);

        assertFalse(tester.isNullable(node));
    }

    @Test
    public void testBinOperandNode_2() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);
        un.nullable = tester.isNullable(un);
        a2.nullable = tester.isNullable(a2);

        assertFalse(tester.isNullable(node2));
    }

    @Test
    public void testUnaryNode() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);

        assertTrue(tester.isNullable(un));
    }

    @Test
    public void testSetFirstAndLastPos() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        a2.nullable = tester.isNullable(a2);

        Set<Integer> set = new HashSet<>();
        a.firstpos.addAll(tester.setFirstAndLastPos(a));
        b.firstpos.addAll(tester.setFirstAndLastPos(b));

        set.add(3);

        assertEquals(set, tester.setFirstAndLastPos(a2));
    }

    @Test
    public void testSetFirstPosBinOp_Or() {
        FirstVisitor tester = new FirstVisitor();
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        a.firstpos.addAll(tester.setFirstAndLastPos(a));
        b.firstpos.addAll(tester.setFirstAndLastPos(b));

        assertEquals(set, tester.setFirstPos(node));
    }

    @Test
    public void testSetFirstPosBinOp_Concat() {
        FirstVisitor tester = new FirstVisitor();
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);
        un.nullable = tester.isNullable(un);
        a2.nullable = tester.isNullable(a2);
        node2.nullable = tester.isNullable(node2);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);

        a.firstpos.addAll(tester.setFirstAndLastPos(a));
        b.firstpos.addAll(tester.setFirstAndLastPos(b));
        node.firstpos.addAll(tester.setFirstPos(node));
        un.firstpos.addAll(tester.setFirstPos(un));
        a2.firstpos.addAll(tester.setFirstAndLastPos(a2));
        node2.firstpos.addAll(tester.setFirstPos(node2));

        assertEquals(set, tester.setFirstPos(node2));
    }

    @Test
    public void testSetLastPosBinOp_Or() {
        FirstVisitor tester = new FirstVisitor();
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> positions;
        positions = tester.setFirstAndLastPos(a);
        a.firstpos.addAll(positions);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.firstpos.addAll(positions);
        b.lastpos.addAll(positions);

        assertEquals(set, tester.setLastPos(node));
    }

    @Test
    public void testSetLastPosBinOp_Concat() {
        FirstVisitor tester = new FirstVisitor();
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);
        un.nullable = tester.isNullable(un);
        a2.nullable = tester.isNullable(a2);
        node2.nullable = tester.isNullable(node2);

        Set<Integer> set = new HashSet<>();
        set.add(3);

        Set<Integer> positions;
        positions = tester.setFirstAndLastPos(a);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.lastpos.addAll(positions);

        positions = tester.setFirstPos(node);
        node.lastpos.addAll(positions);

        positions = tester.setFirstPos(un);
        un.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(a2);
        a2.lastpos.addAll(positions);

        positions = tester.setFirstPos(node2);
        node2.lastpos.addAll(positions);

        assertEquals(set, tester.setLastPos(node2));
    }

    @Test
    public void testSetFirstPosUnaryOp() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);
        un.nullable = tester.isNullable(un);


        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> positions;

        positions = tester.setFirstAndLastPos(a);
        a.firstpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.firstpos.addAll(positions);

        positions = tester.setFirstPos(node);
        node.firstpos.addAll(positions);

        positions = tester.setFirstPos(un);
        un.firstpos.addAll(positions);

        assertEquals(set, tester.setFirstPos(un));
    }

    @Test
    public void testSetLastPosUnaryOp() {
        FirstVisitor tester = new FirstVisitor();

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        a.nullable = tester.isNullable(a);
        b.nullable = tester.isNullable(b);
        node.nullable = tester.isNullable(node);
        un.nullable = tester.isNullable(un);


        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> positions;

        positions = tester.setFirstAndLastPos(a);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.lastpos.addAll(positions);

        positions = tester.setLastPos(node);
        node.lastpos.addAll(positions);

        positions = tester.setLastPos(un);
        un.lastpos.addAll(positions);

        assertEquals(set, tester.setLastPos(un));
    }
}
