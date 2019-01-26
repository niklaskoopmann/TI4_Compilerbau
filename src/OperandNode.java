public class OperandNode extends SyntaxNode implements Visitable {

    public int position;
    public String symbol;

    public OperandNode(String symbol) {
        position = -1;
        this.symbol = symbol;
    }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
