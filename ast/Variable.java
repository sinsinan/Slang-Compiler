package ast;

import parser.Symbol;
import parser.SymbolTable;
import parser.VAR_TYPE;

public class Variable extends Exp{
    private String name;
    private SymbolTable symbolTable;

    public Variable(String name, SymbolTable symbolTable) {
        this.name = name;
        this.symbolTable = symbolTable;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return this.symbolTable.getSymbolFromVariableName(this.name);
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        return this.symbolTable.getSymbolFromVariableName(this.name).getType();
    }
}
