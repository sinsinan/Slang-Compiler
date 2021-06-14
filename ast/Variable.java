package ast;

public class Variable extends Exp{
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return  context.getScopeInfo().getSymbolFromVariableName(this.name);
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return context.getScopeInfo().getSymbolFromVariableName(this.name).getType();
    }
}
