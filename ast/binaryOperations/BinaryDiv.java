package ast.binaryOperations;

import ast.Exp;
import ast.RUNTIME_CONTEXT;
import parser.Symbol;
import parser.VAR_TYPE;

public class BinaryDiv extends Exp {
    private Exp leftExp;
    private Exp rightExp;

    public BinaryDiv(Exp leftExp, Exp rightExp) throws Exception {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.typeCheck();
    }

    private void typeCheck() throws Exception {
        VAR_TYPE leftSymbolType = this.leftExp.getType();
        VAR_TYPE rightSymbolType = this.rightExp.getType();
        if (leftSymbolType == rightSymbolType && leftSymbolType == VAR_TYPE.NUMERIC) {
            return;
        } else {
            throw new Exception("Type miss match for binary division, got " + leftSymbolType + " and  " + rightSymbolType);
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedLeftExp = this.leftExp.Evaluate(context);
        Symbol evaluatedRightExp = this.rightExp.Evaluate(context);
        return typeCheckAndGetSymbol(evaluatedLeftExp, evaluatedRightExp);
    }

    private Symbol typeCheckAndGetSymbol(Symbol leftSymbol, Symbol rightSymbol) throws Exception {
        return new Symbol(leftSymbol.getNumericValue() / rightSymbol.getNumericValue());
    }

    @Override
    public VAR_TYPE getType() {
        return VAR_TYPE.NUMERIC;
    }
}
