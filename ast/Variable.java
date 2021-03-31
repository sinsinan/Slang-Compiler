package ast;

import parser.Symbol;
import parser.SymbolTable;

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
}
