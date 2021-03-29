package ast;

import parser.Symbol;

public class NumericConstant extends Exp{
    private Symbol symbol;

    public NumericConstant(double value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }
}