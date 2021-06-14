package ast;

import ast.operators.LogicalOperator;

public class LogicalExp extends Exp {
    private Exp _exp1, _exp2;
    private LogicalOperator _op;

    public LogicalExp(COMPILATION_CONTEXT context, Exp exp1, Exp exp2, LogicalOperator op) throws Exception {
        this._exp1 = exp1;
        this._exp2 = exp2;
        this._op = op;
        this.typeCheck(context, this._exp1, this._exp2);
    }

    public LogicalExp(COMPILATION_CONTEXT context, Exp exp1, LogicalOperator op) throws Exception {
        this._exp1 = exp1;
        this._op = op;
        this.typeCheck(context, this._exp1);
    }

    private void typeCheck(COMPILATION_CONTEXT context, Exp leftExp, Exp rightExp) throws Exception {
        VAR_TYPE leftExpType = leftExp.getType(context);
        VAR_TYPE rightExpType = rightExp.getType(context);
        if (leftExpType != rightExpType && leftExpType != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected "+leftExpType+" and "+rightExpType+" to be BOOLEAN");
        }
    }

    private void typeCheck(COMPILATION_CONTEXT context, Exp leftExp) throws Exception {
        VAR_TYPE leftExpType = leftExp.getType(context);
        if (leftExpType != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected "+leftExpType+" to be BOOLEAN");
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {

        Symbol leftSymbol;
        Symbol rightSymbol;
        switch (this._op){
            case AND:
                leftSymbol = this._exp1.Evaluate(context);
                rightSymbol = this._exp2.Evaluate(context);
                return new Symbol(leftSymbol.getBooleanValue() && rightSymbol.getBooleanValue());
            case OR:
                leftSymbol = this._exp1.Evaluate(context);
                rightSymbol = this._exp2.Evaluate(context);
                return new Symbol(leftSymbol.getBooleanValue() || rightSymbol.getBooleanValue());
            case NOT:
                leftSymbol = this._exp1.Evaluate(context);
                return new Symbol(!leftSymbol.getBooleanValue());
        }
        return null;
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) {
        return VAR_TYPE.BOOLEAN;
    }
}
