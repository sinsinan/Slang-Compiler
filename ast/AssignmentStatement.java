package ast;

public class AssignmentStatement extends Stmt {
    private String variableName;
    private Exp exp;

    public AssignmentStatement(COMPILATION_CONTEXT compilationContext, String variableName, Exp exp) throws Exception {
        this.variableName = variableName;
        this.exp = exp;
        this.typeCheck(compilationContext);
    }
    private void typeCheck(COMPILATION_CONTEXT compilationContext) throws Exception {
        VAR_TYPE variableNameType = compilationContext.getScopeInfo().getSymbolFromVariableName(this.variableName).getType();
        VAR_TYPE expType = this.exp.getType(compilationContext);
        if (variableNameType != expType) {
            throw new Exception("Unexpected assignment, Variable " + this.variableName + " with type " + variableNameType + " assigned value of type " + expType);
        }
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol symbolFromVariableName = context.getScopeInfo().getSymbolFromVariableName(this.variableName);
        Symbol symbolFromExpressionEvaluation = this.exp.Evaluate(context);
        switch (symbolFromVariableName.getType()) {
            case NUMERIC:
                context.getScopeInfo().setVariable(this.variableName, symbolFromExpressionEvaluation.getNumericValue());
                break;
            case STRING:
                context.getScopeInfo().setVariable(this.variableName, symbolFromExpressionEvaluation.getStringValue());
                break;
            case BOOLEAN:
                context.getScopeInfo().setVariable(this.variableName, symbolFromExpressionEvaluation.getBooleanValue());
                break;
        }
        return null;
    }
}
