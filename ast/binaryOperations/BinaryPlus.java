package ast.binaryOperations;

import ast.*;

public class BinaryPlus extends Exp {
    private Exp leftExp;
    private Exp rightExp;

    public BinaryPlus(COMPILATION_CONTEXT context, Exp leftExp, Exp rightExp) throws Exception {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.typeCheck(context);
    }

    private void typeCheck(COMPILATION_CONTEXT context) throws Exception {
        VAR_TYPE leftSymbolType = this.leftExp.getType(context);
        VAR_TYPE rightSymbolType = this.rightExp.getType(context);
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
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        if (this.leftExp.getType(context) == this.rightExp.getType(context) && this.rightExp.getType(context) == VAR_TYPE.NUMERIC) {
            return VAR_TYPE.NUMERIC;
        } else if (this.leftExp.getType(context) == this.rightExp.getType(context) && this.rightExp.getType(context) == VAR_TYPE.STRING) {
            return VAR_TYPE.STRING;
        }
        throw new Exception("Type miss match for binary plus, got " + this.leftExp.getType(context) + " and  " + this.rightExp.getType(context));
    }
}
