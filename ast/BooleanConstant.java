package ast;

public class BooleanConstant extends Exp{
    private Symbol symbol;

    public BooleanConstant(boolean value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return VAR_TYPE.BOOLEAN;
    }
}
