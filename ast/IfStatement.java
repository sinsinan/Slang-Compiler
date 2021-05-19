package ast;

import parser.Symbol;
import parser.VAR_TYPE;

import java.util.List;

public class IfStatement extends Stmt {
    private List<ExpAndStatements> ifElifArray;
    private List<Stmt> elseStatements = null;

    public IfStatement(List<ExpAndStatements> ifElifArray, List<Stmt> elseStatements) {
        this.ifElifArray = ifElifArray;
        this.elseStatements = elseStatements;
    }

    public IfStatement(List<ExpAndStatements> ifElifArray) {
        this.ifElifArray = ifElifArray;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        for (ExpAndStatements expAndStatements : this.ifElifArray) {
            Symbol symbol = expAndStatements.getExp().Evaluate(context);
            if (symbol.getType() != VAR_TYPE.BOOLEAN) {
                throw new Exception("Expected expression result to be boolean, got " + symbol.getType());
            }
            if (symbol.getBooleanValue()) {
                for(Stmt stmt: expAndStatements.getStmtList()) {
                    stmt.Evaluate(context);
                }
                return true;
            }
        }
        if (this.elseStatements != null) {
            for(Stmt stmt: this.elseStatements) {
                stmt.Evaluate(context);
            }
        }
        return true;
    }
}
