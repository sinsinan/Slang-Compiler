package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public class BinaryMult extends Exp{
    private Exp leftExp;
    private Exp rightExp;

    public BinaryMult (Exp leftExp, Exp rightExp) {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedLeftExp = this.leftExp.Evaluate(context);
        Symbol evaluatedRightExp = this.rightExp.Evaluate(context);
        return typeCheckAndGetSymbol(evaluatedLeftExp, evaluatedRightExp);
    }

    private Symbol typeCheckAndGetSymbol(Symbol leftSymbol, Symbol rightSymbol) throws Exception {
        VAR_TYPE leftSymbolType = leftSymbol.getType();
        VAR_TYPE rightSymbolType = rightSymbol.getType();
        if (leftSymbolType == rightSymbolType && leftSymbolType == VAR_TYPE.NUMERIC) {
            return new Symbol(leftSymbol.getNumericValue() * rightSymbol.getNumericValue());
        } else {
            throw new Exception("Type miss match for binary multiplication, got "+leftSymbolType+" and  "+rightSymbol);
        }
    }
}
