package parser;

public class Symbol {
    private VAR_TYPE type;
    private String value;

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
}
