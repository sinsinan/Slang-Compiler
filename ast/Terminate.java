package ast;

import parser.Symbol;

public class Terminate {
    private Class terminatedStmtClass;
    private Symbol terminatedSymbol;

    public Terminate(Class terminatedStmtClass, Symbol terminatedSymbol) {
        this.terminatedStmtClass = terminatedStmtClass;
        this.terminatedSymbol = terminatedSymbol;
    }

    public Class getTerminatedStmtClass() {
        return terminatedStmtClass;
    }

    public Symbol getTerminatedSymbol() {
        return terminatedSymbol;
    }
}


