package ast;

public class WhileStatement extends Stmt {
    private ExpContextAndBlockedScopeStatements expContextAndBlockedScopeStatements;

    public WhileStatement(ExpContextAndBlockedScopeStatements expContextAndBlockedScopeStatements) {
        this.expContextAndBlockedScopeStatements = expContextAndBlockedScopeStatements;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        RUNTIME_CONTEXT whileRuntimeContext = new RUNTIME_CONTEXT(context.getScopeInfo());
        OuterLoop:
        while (this.isWhileComplete(whileRuntimeContext)) {
            for (Stmt stmt : this.expContextAndBlockedScopeStatements.getBlockedScopeStatements().getStmtList()) {
                Terminate t = stmt.Evaluate(whileRuntimeContext);
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
        Symbol val = this.expContextAndBlockedScopeStatements.getExp().Evaluate(context);
        if (val.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected Expression of while to be a boolean, got " + val.getType());
        }
        return val.getBooleanValue();
    }

//    public VAR_TYPE getReturnStatementAndType(COMPILATION_CONTEXT context, VAR_TYPE returnType) throws Exception {
//    }
}
