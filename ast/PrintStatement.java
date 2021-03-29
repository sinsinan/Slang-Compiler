package ast;

import parser.Symbol;

public class PrintStatement extends Stmt{
    private Exp exp;

    public PrintStatement (Exp exp) {
        this.exp = exp;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol val = this.exp.Evaluate(context);
        System.out.print(val.getStringValue());
        return true;
    }
}
