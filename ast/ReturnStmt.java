package ast;

public class ReturnStmt extends Stmt {
    private Exp exp;

    public ReturnStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return new Terminate(this.getClass(), this.exp.Evaluate(context));
    }

    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
       return this.exp.getType(context);
    }
}
