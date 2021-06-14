package ast.UnaryOperations;

import ast.*;

public class UnaryPlus extends Exp {
    private Exp exp;

    public UnaryPlus(COMPILATION_CONTEXT context, Exp exp) throws Exception {
        this.exp = exp;
        this.typeCheck(context);
    }

    private void typeCheck(COMPILATION_CONTEXT context) throws Exception {
        VAR_TYPE expType = this.exp.getType(context);
        if (expType != VAR_TYPE.NUMERIC) {
            throw new Exception("Unary minus only applicable to NUMERIC TYPE, got " + expType);
        }
    }

    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedExp = this.exp.Evaluate(context);
        return evaluatedExp;
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) {
        return VAR_TYPE.NUMERIC;
    }
}
