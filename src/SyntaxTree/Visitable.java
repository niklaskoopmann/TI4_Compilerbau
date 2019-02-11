package SyntaxTree;

public interface Visitable {
    void accept(Visitor.Visitor visitor);
    String toString();
}
