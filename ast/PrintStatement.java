package ast;

public class PrintStatement extends Stmt{
    private Exp exp;

    public PrintStatement (Exp exp) {
        this.exp = exp;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) {
        double val = this.exp.Evaluate(context);
        System.out.print(val);
        return true;
    }
}
