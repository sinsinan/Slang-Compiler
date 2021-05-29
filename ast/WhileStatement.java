package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public class WhileStatement extends Stmt {
    private ExpAndBlockedScopeStatements expAndBlockedScopeStatements;

    public WhileStatement(ExpAndBlockedScopeStatements expAndBlockedScopeStatements) {
        this.expAndBlockedScopeStatements = expAndBlockedScopeStatements;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        OuterLoop:
        while (this.isWhileComplete(context)) {
            for (Stmt stmt : this.expAndBlockedScopeStatements.getBlockedScopeStatements().getStmtList()) {
                Terminate t = stmt.Evaluate(context);
                if (t == null) {
                    continue;
                } else if (BreakStatement.class.equals(t.getTerminatedStmtClass())) {
                    break OuterLoop;
                } else if (ContinueStatement.class.equals(t.getTerminatedStmtClass())) {
                    break;
                } else {
                    return t;
                }
            }
        }
        return null;
    }

    private boolean isWhileComplete(RUNTIME_CONTEXT context) throws Exception {
        Symbol val = this.expAndBlockedScopeStatements.getExp().Evaluate(context);
        if (val.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected Expression of while to be a boolean, got " + val.getType());
        }
        return val.getBooleanValue();
    }
}
