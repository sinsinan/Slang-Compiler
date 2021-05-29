package ast;

import parser.ScopeInfo;
import parser.VAR_TYPE;

public class VariableDeclarationStmt extends Stmt{

    public VariableDeclarationStmt(ScopeInfo scopeInfo, String variableName, VAR_TYPE varType) {
        scopeInfo.declareVariable(variableName, varType);
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return null;
    }

}
