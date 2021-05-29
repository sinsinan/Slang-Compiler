package ast;

import parser.Symbol;
import parser.ScopeInfo;
import parser.VAR_TYPE;

public class Variable extends Exp{
    private String name;
    private ScopeInfo scopeInfo;

    public Variable(String name, ScopeInfo scopeInfo) {
        this.name = name;
        this.scopeInfo = scopeInfo;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return this.scopeInfo.getSymbolFromVariableName(this.name);
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        return this.scopeInfo.getSymbolFromVariableName(this.name).getType();
    }
}
