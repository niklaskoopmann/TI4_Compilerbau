import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.SyntaxNode;
import SyntaxTree.UnaryOpNode;
import Visitor.FirstVisitor;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FirstVisitorTests {


    @Test
    public void testNullableOperandNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode node = new OperandNode("a");
        OperandNode node2 = new OperandNode("epsilon");

        // assert statements
        assertEquals(false, tester.isOperandNullable(node));
        assertEquals(true, tester.isOperandNullable(node2));
    }

    @Test
    public void testBinOperandNode_1() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);


        // assert statements
        assertEquals(false, tester.isOperandNullable(a));
        assertEquals(false, tester.isOperandNullable(b));
        assertEquals(false, tester.isBinOpNullable(node));
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
        assertEquals(false, tester.isOperandNullable(a));
        assertEquals(false, tester.isOperandNullable(b));
        assertEquals(false, tester.isBinOpNullable(node));
        assertEquals(true, tester.isUnaryNullable(un));
        assertEquals(false, tester.isOperandNullable(a2));
        // 0 and 1 equals 0 in AND logic
        assertEquals(false, tester.isBinOpNullable(node2));
    }

    @Test
    public void testUnaryNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        // assert statements
        assertEquals(false, tester.isOperandNullable(a));
        assertEquals(false, tester.isOperandNullable(b));
        assertEquals(false, tester.isBinOpNullable(node));
        assertEquals(true, tester.isUnaryNullable(un));
    }

    // works
    @Test
    public void testSetFirstAndLastPos(){
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isOperandNullable(a2);


        Set<Integer> set = new HashSet<>();
        tester.setFirstAndLastPos(a);
        tester.setFirstAndLastPos(b);
        set.add(3);
        // assert statement
        assertEquals(set,tester.setFirstAndLastPos(a2));
    }


   // works
    @Test
    public void testSetFirstPosBinOp_Or(){
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        a.firstpos.addAll(tester.setFirstAndLastPos(a));
        b.firstpos.addAll(tester.setFirstAndLastPos(b));

        // assert statement
        assertEquals(set,tester.setFirstPos(node));
    }
    // works
    @Test
    public void testSetFirstPosBinOp_Concat(){
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);
        tester.isUnaryNullable(un);
        tester.isOperandNullable(a2);
        tester.isBinOpNullable(node2);

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

        // assert statement
        assertEquals(set,tester.setFirstPos(node2));
    }

    // works
    @Test
    public void testSetLastPosBinOp_Or(){
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        // first and last pos are equal for operands
        Set<Integer> positions = new HashSet<>();
        positions = tester.setFirstAndLastPos(a);
        a.firstpos.addAll(positions);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.firstpos.addAll(positions);
        b.lastpos.addAll(positions);


        // assert statement
        assertEquals(set,tester.setLastPos(node));
    }
    // works
    @Test
    public void testSetLastPosBinOp_Concat(){
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);
        OperandNode a2 = new OperandNode("a");
        BinOpNode node2 = new BinOpNode("°", un, a2);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);
        tester.isUnaryNullable(un);
        tester.isOperandNullable(a2);
        tester.isBinOpNullable(node2);

        Set<Integer> set = new HashSet<>();
        set.add(3);

        // first and last pos are equal for operands
        Set<Integer> positions = new HashSet<>();
        positions = tester.setFirstAndLastPos(a);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.lastpos.addAll(positions);

        positions = tester.setFirstPos(node);
        //node.firstpos.addAll(positions);
        node.lastpos.addAll(positions);

        positions = tester.setFirstPos(un);
        un.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(a2);
        a2.lastpos.addAll(positions);

        positions = tester.setFirstPos(node2);
        node2.lastpos.addAll(positions);

        // assert statement
        assertEquals(set,tester.setLastPos(node2));
    }

    // works
    @Test
    public void testSetFirstPosUnaryOp(){
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);
        tester.isUnaryNullable(un);


        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> positions = new HashSet<>();

        positions = tester.setFirstAndLastPos(a);
        a.firstpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.firstpos.addAll(positions);

        positions = tester.setFirstPos(node);
        node.firstpos.addAll(positions);

        positions = tester.setFirstPos(un);
        un.firstpos.addAll(positions);

        // assert statement
        assertEquals(set,tester.setFirstPos(un));
    }
    // works
    @Test
    public void testSetLastPosUnaryOp(){
        FirstVisitor tester = new FirstVisitor(); // tested class

        OperandNode a = new OperandNode("a");
        OperandNode b = new OperandNode("b");
        BinOpNode node = new BinOpNode("|", a, b);
        UnaryOpNode un = new UnaryOpNode("*", node);

        tester.isOperandNullable(a);
        tester.isOperandNullable(b);
        tester.isBinOpNullable(node);
        tester.isUnaryNullable(un);


        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> positions = new HashSet<>();

        positions = tester.setFirstAndLastPos(a);
        a.lastpos.addAll(positions);

        positions = tester.setFirstAndLastPos(b);
        b.lastpos.addAll(positions);

        positions = tester.setLastPos(node);
        node.lastpos.addAll(positions);

        positions = tester.setLastPos(un);
        un.lastpos.addAll(positions);

        // assert statement
        assertEquals(set,tester.setLastPos(un));
    }
}
