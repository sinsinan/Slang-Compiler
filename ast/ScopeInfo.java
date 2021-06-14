package ast;

import java.util.*;

public class ScopeInfo {
    private ScopeInfo parentScope;
    private LinkedHashMap<String, Symbol> keyValueStore;

    public ScopeInfo() {
        this.parentScope = null;
        this.keyValueStore = new LinkedHashMap<>();
    }

    public ScopeInfo(ScopeInfo parent) {
        this.parentScope = parent;
        this.keyValueStore = new LinkedHashMap<>();
    }

    public Symbol declareVariable(String variableName, VAR_TYPE varType) {
        Symbol symbol = new Symbol(varType);
        this.keyValueStore.put(variableName, symbol);
        return symbol;
    }

    public void setVariable(String variableName, String val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.STRING) {
            throw new Exception("Failed to assign STRING to Variable " + variableName + " of type " + symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Double val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.NUMERIC) {
            throw new Exception("Failed to assign NUMERIC to Variable " + variableName + " of type " + symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Boolean val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Failed to assign BOOLEAN to Variable " + variableName + " of type " + symbol.getType());
        }
        symbol.setValue(val);
    }

    public Symbol getSymbolFromVariableName(String variableName) throws Exception {
        Symbol symbol = null;
        ScopeInfo scopeInfo = this;
        while (scopeInfo != null && symbol == null) {
            symbol = scopeInfo.keyValueStore.get(variableName);
            scopeInfo = scopeInfo.getParentScope();
        }
        if (symbol == null) {
            throw new Exception("Variable " + variableName + " not declared");
        }
        return symbol;
    }

    public Symbol declareVariable(String variableName, VAR_TYPE varType, VAR_TYPE returnType, Map<String, VAR_TYPE> formalArguments) throws Exception {
        if (varType != VAR_TYPE.FUNCTION) {
            throw new Exception("Expected FUNCTION got " + varType);
        }
        Symbol symbol = new Symbol(varType, returnType, formalArguments);
        this.keyValueStore.put(variableName, symbol);
        return symbol;
    }

    public void setVariable(String variableName, BlockedScopeStatements blockedScopeStatements) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.FUNCTION) {
            throw new Exception("Failed to assign List<Stmt> to Variable " + variableName + " of type " + symbol.getType());
        }
        symbol.setValue(blockedScopeStatements);
    }

    public ScopeInfo getParentScope() {
        return this.parentScope;
    }
}
