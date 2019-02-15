package Visitor;

import SyntaxTree.BinOpNode;
import SyntaxTree.OperandNode;
import SyntaxTree.UnaryOpNode;

public interface Visitor {
    void visit(OperandNode node);

    void visit(BinOpNode node);

    void visit(UnaryOpNode node);
}
