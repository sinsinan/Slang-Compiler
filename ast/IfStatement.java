package ast;

import parser.Symbol;
import parser.VAR_TYPE;

import java.util.List;

public class IfStatement extends Stmt {
    private List<ExpAndBlockedScopeStatements> ifElifArray;

    public IfStatement(List<ExpAndBlockedScopeStatements> ifElifArray) {
        this.ifElifArray = ifElifArray;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        for (ExpAndBlockedScopeStatements expAndBlockedScopeStatements : this.ifElifArray) {
            Symbol symbol = expAndBlockedScopeStatements.getExp().Evaluate(context);
            if (symbol.getType() != VAR_TYPE.BOOLEAN) {
                throw new Exception("Expected expression result to be boolean, got " + symbol.getType());
            }
            if (symbol.getBooleanValue()) {
                for(Stmt stmt: expAndBlockedScopeStatements.getBlockedScopeStatements().getStmtList()) {
                    Terminate t = stmt.Evaluate(context);
                    if (t != null) {
                        return t;
                    }
                }
                return null;
            }
        }
        return null;
    }
}
