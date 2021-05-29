package ast;

import parser.Symbol;
import parser.ScopeInfo;
import parser.VAR_TYPE;

import java.util.*;

public class FunctionDefinitionStatement extends Stmt {

    private VAR_TYPE returnType;
    private LinkedHashMap<String, VAR_TYPE> formalArguments;
    private ScopeInfo scopeInfo;
    private String functionName;
    private BlockedScopeStatements blockedScopeStatements;

    public FunctionDefinitionStatement(ScopeInfo scopeInfo, VAR_TYPE returnType, String functionName, LinkedHashMap<String, VAR_TYPE> formalArguments) throws Exception {
        this.scopeInfo = scopeInfo;
        this.functionName = functionName;
        this.returnType = returnType;
        this.formalArguments = formalArguments;
        this.scopeInfo.declareVariable(functionName, VAR_TYPE.FUNCTION, returnType, formalArguments);
        for (Map.Entry<String,VAR_TYPE> entry : this.formalArguments.entrySet()) {
            this.scopeInfo.declareVariable(entry.getKey(), entry.getValue());
        }
    }

    public void setBlockedScopeStatements(BlockedScopeStatements blockedScopeStatements) throws Exception {
        this.blockedScopeStatements = blockedScopeStatements;
        this.typeCheck();
    }

    private void typeCheck() throws Exception {
        boolean isReturnFound = false;
        for (Stmt stmt: this.blockedScopeStatements.getStmtList()) {
            if (ReturnStmt.class.equals(stmt.getClass())) {
                isReturnFound = true;
                if (((ReturnStmt) stmt).getType() != returnType) {
                    throw new Exception("Expected return to be of type "+returnType+" got " +((ReturnStmt) stmt).getType() + "for function: " + this.functionName);
                }
            } else if (WhileStatement.class.equals(stmt.getClass()) || IfStatement.class.equals(stmt.getClass())) {
                Terminate t = stmt.getReturn();
                if (t.)
            }
        }
        if (!isReturnFound) {
            throw new Exception("Return not found for function" + this.functionName)
        }
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol symbol = this.scopeInfo.getSymbolFromVariableName(this.functionName);
        if (symbol.getType() != VAR_TYPE.FUNCTION) {
            throw new Exception("Expected symbol to be FUNCTION got "+ symbol.getType());
        }
        symbol.setValue(this.blockedScopeStatements);
        return null;
    }
}
