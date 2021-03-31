package ast;

import parser.Symbol;

public class StringConstant extends Exp{
    private Symbol symbol;

    public StringConstant(String value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }
}
