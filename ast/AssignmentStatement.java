package ast;

import parser.Symbol;
import parser.SymbolTable;
import parser.VAR_TYPE;

public class AssignmentStatement extends Stmt {
    private String variableName;
    private Exp exp;
    private SymbolTable symbolTable;

    public AssignmentStatement(String variableName, Exp exp, SymbolTable symbolTable) {
        this.variableName = variableName;
        this.exp = exp;
        this.symbolTable = symbolTable;
    }
    private void typeCheck() throws Exception {
        VAR_TYPE variableNameType = this.symbolTable.getSymbolFromVariableName(this.variableName).getType();
        VAR_TYPE expType = this.exp.getType();
        if (variableNameType != expType) {
            throw new Exception("Unexpected assignment, Variable " + this.variableName + " with type " + variableNameType + " assigned value of type " + expType);
        }
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol symbolFromVariableName = this.symbolTable.getSymbolFromVariableName(this.variableName);
        Symbol symbolFromExpressionEvaluation = this.exp.Evaluate(context);
        switch (symbolFromVariableName.getType()) {
            case NUMERIC:
                this.symbolTable.setVariable(this.variableName, symbolFromExpressionEvaluation.getNumericValue());
                break;
            case STRING:
                this.symbolTable.setVariable(this.variableName, symbolFromExpressionEvaluation.getStringValue());
                break;
            case BOOLEAN:
                this.symbolTable.setVariable(this.variableName, symbolFromExpressionEvaluation.getBooleanValue());
                break;
        }
        return true;
    }
}
