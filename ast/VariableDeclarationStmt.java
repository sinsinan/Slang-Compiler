package ast;

public class VariableDeclarationStmt extends Stmt{

    private String variableName;
    private VAR_TYPE varType;

    public VariableDeclarationStmt(COMPILATION_CONTEXT compilationContext, String variableName, VAR_TYPE varType) {
        this.variableName = variableName;
        this.varType = varType;
        compilationContext.getScopeInfo().declareVariable(variableName, varType);
    }

    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        context.getScopeInfo().declareVariable(this.variableName, this.varType);
        return null;
    }

}
