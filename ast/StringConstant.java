package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public class StringConstant extends Exp{
    private Symbol symbol;

    public StringConstant(String value) {
        symbol = new Symbol(value);
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) {
        return this.symbol;
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        return VAR_TYPE.STRING;
    }
}
