package ast.binaryOperations;

import ast.Exp;
import ast.RUNTIME_CONTEXT;
import parser.Symbol;
import parser.VAR_TYPE;

public class BinaryPlus extends Exp {
    private Exp leftExp;
    private Exp rightExp;

    public BinaryPlus(Exp leftExp, Exp rightExp) throws Exception {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.typeCheck();
    }

    private void typeCheck() throws Exception {
        VAR_TYPE leftSymbolType = this.leftExp.getType();
        VAR_TYPE rightSymbolType = this.rightExp.getType();
        if (leftSymbolType == rightSymbolType && (leftSymbolType == VAR_TYPE.NUMERIC || leftSymbolType == VAR_TYPE.STRING)) {
            return;
        } else {
            throw new Exception("Type miss match for binary plus, got " + leftSymbolType + " and  " + rightSymbolType);
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedLeftExp = this.leftExp.Evaluate(context);
        Symbol evaluatedRightExp = this.rightExp.Evaluate(context);
        return typeCheckAndGetSymbol(evaluatedLeftExp, evaluatedRightExp);
    }

    private Symbol typeCheckAndGetSymbol(Symbol leftSymbol, Symbol rightSymbol) throws Exception {
        VAR_TYPE leftSymbolType = leftSymbol.getType();
        if (leftSymbolType == VAR_TYPE.NUMERIC) {
            return new Symbol(leftSymbol.getNumericValue() + rightSymbol.getNumericValue());
        } else {
            return new Symbol(leftSymbol.getStringValue() + rightSymbol.getStringValue());
        }
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        if (this.leftExp.getType() == this.rightExp.getType() && this.rightExp.getType() == VAR_TYPE.NUMERIC) {
            return VAR_TYPE.NUMERIC;
        } else if (this.leftExp.getType() == this.rightExp.getType() && this.rightExp.getType() == VAR_TYPE.STRING) {
            return VAR_TYPE.STRING;
        }
        throw new Exception("Type miss match for binary plus, got " + this.leftExp.getType() + " and  " + this.rightExp.getType());
    }
}
