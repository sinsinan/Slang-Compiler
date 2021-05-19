package parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private List<Map<String, Symbol>> tableArray;
//    private Map<String, Symbol> table;
    public SymbolTable() {
        this.tableArray = new LinkedList<Map<String, Symbol>>();
        this.addBlockScope();
    }

    public void addBlockScope() {
        tableArray.add(new HashMap<String, Symbol>());
    }

    public void declareVariable(String variableName, VAR_TYPE varType) {
        Symbol symbol = new Symbol(varType);
        this.tableArray.get(this.tableArray.size() - 1).put(variableName, symbol);
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
        int i = this.tableArray.size() - 1;
        while (i >=0  && symbol == null) {
            symbol = this.tableArray.get(i).get(variableName);
            i--;
        }
        if (symbol == null) {
            throw new Exception("Variable "+variableName+" not declared");
        }
        return symbol;
    }
}
