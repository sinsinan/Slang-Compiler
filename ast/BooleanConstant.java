package ast;

import parser.Symbol;

public class BooleanConstant extends Exp{
    private Symbol symbol;

    public BooleanConstant(boolean value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }
}
