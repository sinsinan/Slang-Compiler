package ast;

public class PrintLineStatement extends Stmt {
    private Exp exp;

    public PrintLineStatement(Exp exp) {
        this.exp = exp;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) {
        double val = this.exp.Evaluate(context);
        System.out.println(val);
        return true;
    }
}
