package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public class ReturnStmt extends Stmt {
    private Exp exp;

    public ReturnStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return new Terminate(this.getClass(), this.exp.Evaluate(context));
    }

    public VAR_TYPE getType() throws Exception {
       return this.exp.getType();
    }
}
