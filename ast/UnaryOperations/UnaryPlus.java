package ast.UnaryOperations;

import ast.Exp;
import ast.RUNTIME_CONTEXT;
import parser.Symbol;
import parser.VAR_TYPE;

import javax.naming.Context;

public class UnaryPlus extends Exp {
    private Exp exp;
    public UnaryPlus(Exp exp) {
        this.exp = exp;
    }

    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedExp = this.exp.Evaluate(context);
        if (evaluatedExp.getType() == VAR_TYPE.NUMERIC) {
            return evaluatedExp;
        }
        throw new Exception("Unary plus only applicable to NUMERIC TYPE, got " + evaluatedExp.getType());
    }
}
