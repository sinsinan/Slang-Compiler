package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatement extends Exp {
    private String functionName;
    private List<Exp> actualArgumentExpList;

    public FunctionCallStatement(COMPILATION_CONTEXT context, String functionName, List<Exp> actualArgumentExpList) throws Exception {
        this.functionName = functionName;
        this.actualArgumentExpList = actualArgumentExpList;
        this.typeCheck(context);
    }

    private void typeCheck(COMPILATION_CONTEXT context) throws Exception {
        Symbol symbol = context.getScopeInfo().getSymbolFromVariableName(this.functionName);
        List<VAR_TYPE> formalArgumentTypes = new ArrayList<>(symbol.getFormalArguments().values());
        if (formalArgumentTypes.size() != this.actualArgumentExpList.size()) {
            throw new Exception("Expected actual and formal arguments to have same number of arguments for function" + this.functionName);
        }
        for (int i = 0; i < formalArgumentTypes.size(); i++) {
            if (formalArgumentTypes.get(i) != actualArgumentExpList.get(i).getType(context)) {
                throw new Exception("Expected actual and formal arguments to have same type for function" + this.functionName + ", Formal: " + formalArgumentTypes.get(i) + ", Actual: " + actualArgumentExpList.get(i).getType(context));
            }
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol functionSymbol = context.getScopeInfo().getSymbolFromVariableName(this.functionName);
        List<String> formalArgumentNames = new ArrayList<>(functionSymbol.getFormalArguments().keySet());
        RUNTIME_CONTEXT functionCallContext = new RUNTIME_CONTEXT(functionSymbol.getFunctionRuntimeContext().getScopeInfo());
        for (int i = 0; i < this.actualArgumentExpList.size(); i++) {
            this.setFunctionArgument(functionCallContext, formalArgumentNames.get(i), this.actualArgumentExpList.get(i).Evaluate(context));
        }
        for (Stmt stmt : functionSymbol.getBlockedScopeStatements().getStmtList()) {
            Terminate t = stmt.Evaluate(functionCallContext);
            if (t == null) {
                continue;
            } else if (ReturnStmt.class.equals(t.getTerminatedStmtClass())) {
                // TODO DO we need this here
                if (t.getTerminatedSymbol().getType().equals(context.getScopeInfo().getSymbolFromVariableName(this.functionName).getReturnType())) {
                    return t.getTerminatedSymbol();
                }
            }
        }
        throw new Exception("FunctionCallStatement: No return found ");
    }

    private void setFunctionArgument(RUNTIME_CONTEXT context, String argumentName, Symbol evaluatedSymbol) throws Exception {
        context.getScopeInfo().declareVariable(argumentName, evaluatedSymbol.getType());
        switch (context.getScopeInfo().getSymbolFromVariableName(this.functionName).getReturnType()) {
            case STRING:
                context.getScopeInfo().getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getStringValue());
                break;
            case BOOLEAN:
                context.getScopeInfo().getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getBooleanValue());
                break;
            case NUMERIC:
                context.getScopeInfo().getSymbolFromVariableName(argumentName).setValue(evaluatedSymbol.getNumericValue());
                break;
            //TODO implement function
            default:
                throw new IllegalStateException("Unexpected value in setFunctionArgument: " + context.getScopeInfo().getSymbolFromVariableName(this.functionName).getReturnType());
        }
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return context.getScopeInfo().getSymbolFromVariableName(this.functionName).getReturnType();
    }
}
