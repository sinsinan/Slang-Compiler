package ast;

import parser.Symbol;

public class PrintLineStatement extends Stmt {
    private Exp exp;

    public PrintLineStatement(Exp exp) {
        this.exp = exp;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol val = this.exp.Evaluate(context);

        System.out.println(val.getStringValue());
        return true;
    }
}
