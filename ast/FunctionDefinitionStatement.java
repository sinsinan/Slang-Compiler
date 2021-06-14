package ast;

import java.util.*;

public class FunctionDefinitionStatement extends Stmt {

    private VAR_TYPE returnType;
    private LinkedHashMap<String, VAR_TYPE> formalArguments;
    private String functionName;
    private BlockedScopeStatements blockedScopeStatements;

    public FunctionDefinitionStatement(COMPILATION_CONTEXT context, COMPILATION_CONTEXT functionContext, VAR_TYPE returnType, String functionName, LinkedHashMap<String, VAR_TYPE> formalArguments) throws Exception {
        this.functionName = functionName;
        this.returnType = returnType;
        this.formalArguments = formalArguments;
        context.getScopeInfo().declareVariable(functionName, VAR_TYPE.FUNCTION, returnType, formalArguments);
        for (Map.Entry<String,VAR_TYPE> entry : this.formalArguments.entrySet()) {
            functionContext.getScopeInfo().declareVariable(entry.getKey(), entry.getValue());
        }
    }

    public void setBlockedScopeStatements(BlockedScopeStatements blockedScopeStatements) throws Exception {
        this.blockedScopeStatements = blockedScopeStatements;
//        this.typeCheck();
    }

//    private void typeCheck() throws Exception {
//        boolean isReturnFound = false;
//        for (Stmt stmt: this.blockedScopeStatements.getStmtList()) {
//            if (ReturnStmt.class.equals(stmt.getClass())) {
//                isReturnFound = true;
//                if (((ReturnStmt) stmt).getType() != returnType) {
//                    throw new Exception("Expected return to be of type "+returnType+" got " +((ReturnStmt) stmt).getType() + "for function: " + this.functionName);
//                }
//            } else if (WhileStatement.class.equals(stmt.getClass())) {
//                VAR_TYPE returnedType = stmt.getReturnedType();
//                if (returnedType != this.returnType) {
//                    throw new Exception("Expected return to be of type "+returnType+" got " +((ReturnStmt) stmt).getType() + "for function: " + this.functionName);
//                }
//            } else if (IfStatement.class.equals(stmt.getClass())) {
//                VAR_TYPE returnedType = stmt.getReturnedType();
//                if (returnedType != this.returnType) {
//                    throw new Exception("Expected return to be of type "+returnType+" got " +((ReturnStmt) stmt).getType() + "for function: " + this.functionName);
//                }
//            }
//        }
//        if (!isReturnFound) {
//            throw new Exception("Return not found for function" + this.functionName);
//        }
//    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        RUNTIME_CONTEXT functionRuntimeContext = new RUNTIME_CONTEXT(context.getScopeInfo());
        Symbol symbol = context.getScopeInfo().declareVariable(functionName, VAR_TYPE.FUNCTION, returnType, formalArguments);
        symbol.setValue(this.blockedScopeStatements);
        symbol.setValue(functionRuntimeContext);
        return null;
    }
}
