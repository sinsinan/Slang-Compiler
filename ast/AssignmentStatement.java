package ast;

import parser.Symbol;
import parser.SymbolTable;

public class AssignmentStatement extends Stmt {
    private String variableName;
    private Exp exp;
    private SymbolTable symbolTable;

    public AssignmentStatement(String variableName, Exp exp, SymbolTable symbolTable) {
        this.variableName = variableName;
        this.exp = exp;
        this.symbolTable = symbolTable;
    }

    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol symbolFromVariableName = this.symbolTable.getSymbolFromVariableName(this.variableName);
        Symbol symbolFromExpressionEvaluation = this.exp.Evaluate(context);
        if (symbolFromExpressionEvaluation.getType() != symbolFromVariableName.getType()) {
            throw new Exception("Unexpected assignment, Variable " + this.variableName + " with type " + symbolFromVariableName.getType() + " assigned value of type " + symbolFromExpressionEvaluation.getType());
        }
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
