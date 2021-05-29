package parser;

import ast.BlockedScopeStatements;
import ast.FunctionDefinitionStatement;
import ast.Stmt;
import ast.WhileStatement;

import java.util.*;

public class ScopeInfo {
    private ScopeInfo parent;
    private LinkedHashMap<String, ScopeInfo> child;
    private LinkedHashMap<String, Symbol> keyValueStore;
    private WhileStatement insideWhile = null;
    private FunctionDefinitionStatement insideFunction = null;
    private Stmt lastExecutedStmt = null;

    public ScopeInfo() {
        this.parent = null;
        this.child = new LinkedHashMap<>();
        this.keyValueStore = new LinkedHashMap<>();
    }

    public ScopeInfo(ScopeInfo parent) {
        this.parent = parent;
        this.child = new LinkedHashMap<>();
        this.keyValueStore = new LinkedHashMap<>();
    }

    public ScopeInfo(ScopeInfo parent, WhileStatement insideWhile) {
        this.parent = parent;
        this.child = new LinkedHashMap<>();
        this.keyValueStore = new LinkedHashMap<>();
        this.insideWhile = insideWhile;
    }

    public ScopeInfo(ScopeInfo parent, FunctionDefinitionStatement functionDefinitionStatement) {
        this.parent = parent;
        this.child = new LinkedHashMap<>();
        this.keyValueStore = new LinkedHashMap<>();
        this.insideFunction = functionDefinitionStatement;
    }

    public ScopeInfo(ScopeInfo parent, WhileStatement insideWhile, FunctionDefinitionStatement functionDefinitionStatement) {
        this.parent = parent;
        this.child = new LinkedHashMap<>();
        this.keyValueStore = new LinkedHashMap<>();
        this.insideWhile = insideWhile;
        this.insideFunction = functionDefinitionStatement;
    }

    public void addChild(String childName) {
        this.child.put(childName, new ScopeInfo(this));
    }

    public int getChildSize() {
        return this.child.size();
    }

    public ScopeInfo getChildByName(String childName) {
        return this.child.get(childName);
    }

    public void declareVariable(String variableName, VAR_TYPE varType) {
        Symbol symbol = new Symbol(varType);
        this.keyValueStore.put(variableName, symbol);
    }

    public void setVariable(String variableName, String val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.STRING) {
            throw new Exception("Failed to assign STRING to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Double val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.NUMERIC) {
            throw new Exception("Failed to assign NUMERIC to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Boolean val) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Failed to assign BOOLEAN to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public Symbol getSymbolFromVariableName(String variableName) throws Exception {
        Symbol symbol = null;
        ScopeInfo scopeInfo = this;
        while (scopeInfo != null && symbol == null) {
            symbol = scopeInfo.keyValueStore.get(variableName);
            scopeInfo = scopeInfo.getParent();
        }
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        return symbol;
    }

    public void declareVariable(String variableName, VAR_TYPE varType, VAR_TYPE returnType, Map<String, VAR_TYPE> formalArguments) throws Exception {
        if (varType != VAR_TYPE.FUNCTION) {
            throw new Exception("Expected FUNCTION got "+varType);
        }
        Symbol symbol = new Symbol(varType, returnType, formalArguments);
        this.keyValueStore.put(variableName, symbol);
    }

    public void setVariable(String variableName, BlockedScopeStatements blockedScopeStatements) throws Exception {
        Symbol symbol = this.getSymbolFromVariableName(variableName);
        if (symbol.getType() != VAR_TYPE.FUNCTION) {
            throw new Exception("Failed to assign List<Stmt> to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(blockedScopeStatements);
    }

    public ScopeInfo getParent() {
        return this.parent;
    }

    public Stmt getLastExecutedStmt() {
        return lastExecutedStmt;
    }

    public void setLastExecutedStmt(Stmt lastExecutedStmt) {
        this.lastExecutedStmt = lastExecutedStmt;
    }
}
