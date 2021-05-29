package parser;

import ast.BlockedScopeStatements;
import ast.Stmt;

import java.util.List;
import java.util.Map;

public class Symbol {
    private VAR_TYPE type;
    private String value;

    private VAR_TYPE returnType;
    private Map<String, VAR_TYPE> formalArguments;
    private BlockedScopeStatements blockedScopeStatements;

    public Symbol(boolean val) {
        this.type = VAR_TYPE.BOOLEAN;
        this.value = String.valueOf(val);
    }

    public Symbol(double val) {
        this.type = VAR_TYPE.NUMERIC;
        this.value = String.valueOf(val);
    }

    public Symbol(String val) {
        this.type = VAR_TYPE.STRING;
        this.value = val;
    }

    public Symbol(VAR_TYPE type) {
        this.type = type;
    }

    public Symbol(VAR_TYPE type, VAR_TYPE returnType, Map<String, VAR_TYPE> formalArguments) throws Exception {
        if (type != VAR_TYPE.FUNCTION) {
            throw new Exception("Expected type to be FUNCTION, got " + type);
        }
        this.type = type;
        this.returnType = returnType;
        this.formalArguments = formalArguments;
    }

    public void setValue(boolean val) throws Exception {
        if (this.type != VAR_TYPE.BOOLEAN) {
            throw new Exception("Cannot assign boolean to the variable of type " + this.type);
        }
        this.value = String.valueOf(val);
    }

    public void setValue(double val) throws Exception {
        if (this.type != VAR_TYPE.NUMERIC) {
            throw new Exception("Cannot assign double to the variable of type " + this.type);
        }
        this.value = String.valueOf(val);
    }

    public void setValue(BlockedScopeStatements blockedScopeStatements) throws Exception {
        if (this.type != VAR_TYPE.FUNCTION) {
            throw new Exception("Cannot assign stmtList to the variable of type " + this.type);
        }
        this.blockedScopeStatements = blockedScopeStatements;
    }

    public void setValue(String val) throws Exception {
        if (this.type != VAR_TYPE.STRING) {
            throw new Exception("Cannot assign String to the variable of type " + this.type);
        }
        this.value = val;
    }

    public boolean getBooleanValue() throws Exception {
        if (this.value == null) {
            throw new Exception("Variable not initialized");
        }
        return Boolean.valueOf(this.value);
    }

    public double getNumericValue() throws Exception {
        if (this.value == null) {
            throw new Exception("Variable not initialized");
        }
        return Double.valueOf(this.value);
    }

    public String getStringValue() throws Exception {
        if (this.value == null) {
            throw new Exception("Variable not initialized");
        }
        return this.value;
    }

    public VAR_TYPE getType() {
        return type;
    }

    public VAR_TYPE getReturnType() {
        return returnType;
    }

    public Map<String, VAR_TYPE> getFormalArguments() {
        return formalArguments;
    }

    public BlockedScopeStatements getBlockedScopeStatements() {
        return this.blockedScopeStatements;
    }

    public void removeValue() {
        this.value = null;
    }
}
