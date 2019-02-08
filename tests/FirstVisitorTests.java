import SyntaxTree.OperandNode;
import Visitor.FirstVisitor;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.assertEquals;

public class FirstVisitorTests {


    @Test
    public void testNullableOperandNode() {
        FirstVisitor tester = new FirstVisitor(); // tested class
        OperandNode node = new OperandNode("a");

        // assert statements
        assertEquals(false, tester.setOperandNullable(node));
    }
}
