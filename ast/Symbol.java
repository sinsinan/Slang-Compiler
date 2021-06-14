package ast;

import java.util.Map;

public class Symbol {
    private VAR_TYPE type;
    private String stringValue;
    private Boolean booleanValue;
    private Double doubleValue;

    private VAR_TYPE functionReturnType;
    private Map<String, VAR_TYPE> functionFormalArguments;
    private BlockedScopeStatements functionBlockedScopeStatements;
    private RUNTIME_CONTEXT functionRuntimeContext;

    public Symbol(boolean val) {
        this.type = VAR_TYPE.BOOLEAN;
        this.booleanValue = val;
    }

    public Symbol(double val) {
        this.type = VAR_TYPE.NUMERIC;
        this.doubleValue = val;
    }

    public Symbol(String val) {
        this.type = VAR_TYPE.STRING;
        this.stringValue = val;
    }

    public Symbol(VAR_TYPE type) {
        this.type = type;
    }

    public Symbol(VAR_TYPE type, VAR_TYPE returnType, Map<String, VAR_TYPE> formalArguments) throws Exception {
        if (type != VAR_TYPE.FUNCTION) {
            throw new Exception("Expected type to be FUNCTION, got " + type);
        }
        this.type = type;
        this.functionReturnType = returnType;
        this.functionFormalArguments = formalArguments;
    }

    public void setValue(boolean val) throws Exception {
        if (this.type != VAR_TYPE.BOOLEAN) {
            throw new Exception("Cannot assign boolean to the variable of type " + this.type);
        }
        this.booleanValue = val;
    }

    public void setValue(double val) throws Exception {
        if (this.type != VAR_TYPE.NUMERIC) {
            throw new Exception("Cannot assign double to the variable of type " + this.type);
        }
        this.doubleValue = val;
    }

    public void setValue(BlockedScopeStatements blockedScopeStatements) throws Exception {
        if (this.type != VAR_TYPE.FUNCTION) {
            throw new Exception("Cannot assign stmtList to the variable of type " + this.type);
        }
        this.functionBlockedScopeStatements = blockedScopeStatements;
    }

    public void setValue(RUNTIME_CONTEXT functionRuntimeContext) throws Exception {
        if (this.type != VAR_TYPE.FUNCTION) {
            throw new Exception("Cannot assign stmtList to the variable of type " + this.type);
        }
        this.functionRuntimeContext = functionRuntimeContext;
    }

    public void setValue(String val) throws Exception {
        if (this.type != VAR_TYPE.STRING) {
            throw new Exception("Cannot assign String to the variable of type " + this.type);
        }
        this.stringValue = val;
    }

    public boolean getBooleanValue() throws Exception {
        if (this.booleanValue == null) {
            throw new Exception("Variable not initialized");
        }
        return this.booleanValue;
    }

    public double getNumericValue() throws Exception {
        if (this.doubleValue == null) {
            throw new Exception("Variable not initialized");
        }
        return this.doubleValue;
    }

    public String getStringValue() throws Exception {
        switch (this.type) {
            case NUMERIC:
                return this.doubleValue.toString();
            case STRING:
                return this.stringValue;
            case BOOLEAN:
                return this.getStringValue();
//            case FUNCTION: //TODO add proper
//                return this.functionReturnType;
            default:
                throw new Exception("String of variable not found");
        }
    }

    public VAR_TYPE getType() {
        return type;
    }

    public VAR_TYPE getReturnType() {
        return functionReturnType;
    }

    public Map<String, VAR_TYPE> getFormalArguments() {
        return functionFormalArguments;
    }

    public BlockedScopeStatements getBlockedScopeStatements() {
        return this.functionBlockedScopeStatements;
    }

    public RUNTIME_CONTEXT getFunctionRuntimeContext() {
        return functionRuntimeContext;
    }
}
