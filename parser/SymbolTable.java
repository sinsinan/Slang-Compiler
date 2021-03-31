package parser;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Symbol> table;
    public SymbolTable() {
        this.table =  new HashMap<String, Symbol>();
    }

    public void declareVariable(String variableName, VAR_TYPE varType) {
        Symbol symbol = new Symbol(varType);
        this.table.put(variableName, symbol);
    }

    public void setVariable(String variableName, String val) throws Exception {
        Symbol symbol = this.table.get(variableName);
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        if (symbol.getType() != VAR_TYPE.STRING) {
            throw new Exception("Failed to assign STRING to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Double val) throws Exception {
        Symbol symbol = this.table.get(variableName);
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        if (symbol.getType() != VAR_TYPE.NUMERIC) {
            throw new Exception("Failed to assign NUMERIC to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public void setVariable(String variableName, Boolean val) throws Exception {
        Symbol symbol = this.table.get(variableName);
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        if (symbol.getType() != VAR_TYPE.BOOLEAN) {
            throw new Exception("Failed to assign BOOLEAN to Variable "+variableName+" of type "+symbol.getType());
        }
        symbol.setValue(val);
    }

    public Symbol getSymbolFromVariableName(String variableName) throws Exception {
        Symbol symbol = this.table.get(variableName);
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        return symbol;
    }
}
