package ast;

import parser.Symbol;
import parser.VAR_TYPE;

import javax.naming.Context;

public class UnaryMinus extends Exp{
    private Exp exp;
    public UnaryMinus(Exp exp) {
        this.exp = exp;
    }

    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedExp = this.exp.Evaluate(context);
        if (evaluatedExp.getType() == VAR_TYPE.NUMERIC) {
            evaluatedExp.setValue(-evaluatedExp.getNumericValue());
            return evaluatedExp;
        }
        throw new Exception("Unary minus only applicable to NUMERIC TYPE, got " + evaluatedExp.getType());
    }
}
