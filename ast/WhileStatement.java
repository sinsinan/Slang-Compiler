package ast;

import parser.Symbol;
import parser.VAR_TYPE;

import java.util.ArrayList;
import java.util.List;

public class WhileStatement extends Stmt {
    private Exp exp;
    private List<Stmt> stmtList;

    public WhileStatement(Exp exp, List<Stmt> stmtList) {
        this.exp = exp;
        this.stmtList = stmtList;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        OuterLoop:
        while (this.isWhileComplete(context)) {
            for (Stmt stmt : this.stmtList) {
                if (stmt.getClass() == BreakStatement.class) {
                    break OuterLoop;
                }
                if (stmt.getClass() == ContinueStatement.class) {
                    break;
                }
                stmt.Evaluate(context);
            }
        }
        return true;
    }

    private boolean isWhileComplete(RUNTIME_CONTEXT context) throws Exception {
        Symbol val = this.exp.Evaluate(context);
        if (val.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected Expression of while to be a boolean, got " + val.getType());
        }
        return val.getBooleanValue();
    }
}
