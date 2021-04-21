package ast.UnaryOperations;

import ast.Exp;
import ast.RUNTIME_CONTEXT;
import parser.Symbol;
import parser.VAR_TYPE;

import javax.naming.Context;

public class UnaryPlus extends Exp {
    private Exp exp;

    public UnaryPlus(Exp exp) throws Exception {
        this.exp = exp;
        this.typeCheck();
    }

    private void typeCheck() throws Exception {
        VAR_TYPE expType = this.exp.getType();
        if (expType != VAR_TYPE.NUMERIC) {
            throw new Exception("Unary minus only applicable to NUMERIC TYPE, got " + expType);
        }
    }

    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol evaluatedExp = this.exp.Evaluate(context);
        return evaluatedExp;
    }

    @Override
    public VAR_TYPE getType() {
        return VAR_TYPE.NUMERIC;
    }
}