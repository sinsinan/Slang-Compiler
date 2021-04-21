package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public class NumericConstant extends Exp{
    private Symbol symbol;

    public NumericConstant(double value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        return VAR_TYPE.NUMERIC;
    }
}