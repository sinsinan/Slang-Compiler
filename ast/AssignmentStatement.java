package ast;

import parser.Symbol;
import parser.ScopeInfo;
import parser.VAR_TYPE;

public class AssignmentStatement extends Stmt {
    private String variableName;
    private Exp exp;
    private ScopeInfo scopeInfo;

    public AssignmentStatement(String variableName, Exp exp, ScopeInfo scopeInfo) {
        this.variableName = variableName;
        this.exp = exp;
        this.scopeInfo = scopeInfo;
    }
    private void typeCheck() throws Exception {
        VAR_TYPE variableNameType = this.scopeInfo.getSymbolFromVariableName(this.variableName).getType();
        VAR_TYPE expType = this.exp.getType();
        if (variableNameType != expType) {
            throw new Exception("Unexpected assignment, Variable " + this.variableName + " with type " + variableNameType + " assigned value of type " + expType);
        }
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol symbolFromVariableName = this.scopeInfo.getSymbolFromVariableName(this.variableName);
        Symbol symbolFromExpressionEvaluation = this.exp.Evaluate(context);
        switch (symbolFromVariableName.getType()) {
            case NUMERIC:
                this.scopeInfo.setVariable(this.variableName, symbolFromExpressionEvaluation.getNumericValue());
                break;
            case STRING:
                this.scopeInfo.setVariable(this.variableName, symbolFromExpressionEvaluation.getStringValue());
                break;
            case BOOLEAN:
                this.scopeInfo.setVariable(this.variableName, symbolFromExpressionEvaluation.getBooleanValue());
                break;
        }
        return null;
    }
}
