package ast.binaryOperations;

import ast.*;

public class BinaryMinus extends Exp {
    private Exp leftExp;
    private Exp rightExp;

    public BinaryMinus(COMPILATION_CONTEXT context, Exp leftExp, Exp rightExp) throws Exception {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.typeCheck(context);
    }

    private void typeCheck(COMPILATION_CONTEXT context) throws Exception {
        VAR_TYPE leftSymbolType = this.leftExp.getType(context);
        VAR_TYPE rightSymbolType = this.rightExp.getType(context);
        if (leftSymbolType == rightSymbolType && leftSymbolType == VAR_TYPE.NUMERIC) {
            return;
        } else {
            throw new Exception("Type miss match for binary minus, got " + leftSymbolType + " and  " + rightSymbolType);
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
        VAR_TYPE rightSymbolType = rightSymbol.getType();
        return new Symbol(leftSymbol.getNumericValue() - rightSymbol.getNumericValue());
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) {
        return VAR_TYPE.NUMERIC;
    }
}
