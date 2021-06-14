package ast;

import java.util.List;

public class IfStatement extends Stmt {
    private List<ExpContextAndBlockedScopeStatements> ifElifArray;

    public IfStatement(List<ExpContextAndBlockedScopeStatements> ifElifArray) {
        this.ifElifArray = ifElifArray;
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        for (ExpContextAndBlockedScopeStatements expContextAndBlockedScopeStatements : this.ifElifArray) {
            RUNTIME_CONTEXT blockScopedContext = new RUNTIME_CONTEXT(context.getScopeInfo());
            Symbol symbol = expContextAndBlockedScopeStatements.getExp().Evaluate(blockScopedContext);
            if (symbol.getType() != VAR_TYPE.BOOLEAN) {
                throw new Exception("Expected expression result to be boolean, got " + symbol.getType());
            }
            if (symbol.getBooleanValue()) {
                for (Stmt stmt : expContextAndBlockedScopeStatements.getBlockedScopeStatements().getStmtList()) {
                    Terminate t = stmt.Evaluate(blockScopedContext);
                    if (t != null) {
                        return t;
                    }
                }
                return null;
            }
        }
        return null;
    }

//    public VAR_TYPE getReturnStatementAndType(COMPILATION_CONTEXT context, VAR_TYPE returnType) throws Exception {
//        List<VAR_TYPE> allReturns = new ArrayList<>();
//        for (ExpAndBlockedScopeStatements expAndBlockedScopeStatements : this.ifElifArray) {
//            VAR_TYPE firstReturn = null;
//            for (Stmt stmt : expAndBlockedScopeStatements.getBlockedScopeStatements().getStmtList()) {
//                if (ReturnStmt.class.equals(stmt.getClass()) && firstReturn == null) {
//                    firstReturn = ((ReturnStmt) stmt).getType(context);
//                    if (firstReturn != returnType) {
//                        throw new Exception("Expected return of type " + returnType + ", got " + firstReturn);
//                    }
//                    allReturns.add(firstReturn);
//                }
//                if (WhileStatement.class.equals(stmt.getClass()) && firstReturn == null) {
//                    firstReturn = ((WhileStatement) stmt).getReturnStatementAndType(context, returnType);
//                    if (firstReturn != returnType) {
//                        throw new Exception("Expected return of type " + returnType + ", got " + firstReturn);
//                    }
//                    allReturns.add(firstReturn);
//                }
//
//            }
//            return null;
//        }
//    }
}
