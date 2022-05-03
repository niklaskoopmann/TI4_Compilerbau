package SyntaxTree;

import Visitor.Visitor;

/**
 * @author Student_4
 */

public interface Visitable {

    void accept(Visitor visitor);

    String toString();

}
