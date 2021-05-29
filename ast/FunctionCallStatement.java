package ast;

import parser.Symbol;
import parser.ScopeInfo;
import parser.VAR_TYPE;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatement extends Exp {
    private String functionName;
    private ScopeInfo scopeInfo;
    private List<Exp> actualArgumentExpList;

    public FunctionCallStatement(String functionName, ScopeInfo scopeInfo, List<Exp> actualArgumentExpList) throws Exception {
        this.functionName = functionName;
        this.scopeInfo = scopeInfo;
        this.actualArgumentExpList = actualArgumentExpList;
        this.typeCheck();
    }

    private void typeCheck() throws Exception {
        Symbol symbol = this.scopeInfo.getSymbolFromVariableName(this.functionName);
        List<VAR_TYPE> formalArgumentTypes = new ArrayList<>(symbol.getFormalArguments().values());
        if (formalArgumentTypes.size() != this.actualArgumentExpList.size()) {
            throw new Exception("Expected actual and formal arguments to have same number of arguments for function" + this.functionName);
        }
        for (int i = 0; i < formalArgumentTypes.size(); i++) {
            if (formalArgumentTypes.get(i) != actualArgumentExpList.get(i).getType()) {
                throw new Exception("Expected actual and formal arguments to have same type for function" + this.functionName + ", Formal: " + formalArgumentTypes.get(i) + ", Actual: " + actualArgumentExpList.get(i).getType());
            }
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {

        Symbol functionSymbol = this.scopeInfo.getSymbolFromVariableName(this.functionName);
        List<String> formalArgumentNames = new ArrayList<>(functionSymbol.getFormalArguments().keySet());
        for (int i = 0; i < this.actualArgumentExpList.size(); i++) {
            this.setFunctionArgument(formalArgumentNames.get(i), this.actualArgumentExpList.get(i).Evaluate(context));
        }
        for (Stmt stmt : functionSymbol.getBlockedScopeStatements().getStmtList()) {
            Terminate t = stmt.Evaluate(context);
            if (t == null) {
                continue;
            } else if (ReturnStmt.class.equals(t.getTerminatedStmtClass())) {
                this.removeArgumentValues(formalArgumentNames);
                return t.getTerminatedSymbol();
            }
        }
        throw new Exception("FunctionCallStatement: No return found ");
    }

    private void setFunctionArgument(String argumentName, Symbol evaluatedSymbol) throws Exception {
        switch (this.scopeInfo.getSymbolFromVariableName(this.functionName).getReturnType()) {
            case STRING:
                this.scopeInfo.getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getStringValue());
                break;
            case BOOLEAN:
                this.scopeInfo.getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getBooleanValue());
                break;
            case NUMERIC:
                this.scopeInfo.getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getNumericValue());
                break;
            //TODO implement function
            default:
                throw new IllegalStateException("Unexpected value in return: " + this.scopeInfo.getSymbolFromVariableName(this.functionName).getReturnType());
        }
    }

    private void removeArgumentValues(List<String> formalArgumentNames) throws Exception {
        for (int i = 0; i < this.actualArgumentExpList.size(); i++) {
            this.scopeInfo.getSymbolFromVariableName(formalArgumentNames.get(i)).removeValue();
        }
    }

    @Override
    public VAR_TYPE getType() throws Exception {
        return this.scopeInfo.getSymbolFromVariableName(this.functionName).getReturnType();
    }
}
