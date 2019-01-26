package Visitor;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;

public interface Visitor {
    public void visit(OperandNode node);
    public void visit(BinOpNode node);
    public void visit(UnaryOpNode node);
}
