package ast;

import ast.operators.LogicalOperator;
import parser.Symbol;
import parser.VAR_TYPE;

public class LogicalExp extends Exp {
    private Exp _exp1, _exp2;
    private LogicalOperator _op;

    public LogicalExp(Exp exp1, Exp exp2, LogicalOperator op) throws Exception {
        this._exp1 = exp1;
        this._exp2 = exp2;
        this._op = op;
        this.typeCheck(this._exp1, this._exp2);
    }

    public LogicalExp(Exp exp1, LogicalOperator op) throws Exception {
        this._exp1 = exp1;
        this._op = op;
        this.typeCheck(this._exp1);
    }

    private void typeCheck(Exp leftExp, Exp rightExp) throws Exception {
        VAR_TYPE leftExpType = leftExp.getType();
        VAR_TYPE rightExpType = rightExp.getType();
        if (leftExpType != rightExpType && leftExpType != VAR_TYPE.BOOLEAN) {
            throw new Exception("Expected "+leftExpType+" and "+rightExpType+" to be BOOLEAN");
        }
    }

    private void typeCheck(Exp leftExp) throws Exception {
        VAR_TYPE leftExpType = leftExp.getType();
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
                rightSymbol = this._exp1.Evaluate(context);
                return new Symbol(leftSymbol.getBooleanValue() && rightSymbol.getBooleanValue());
            case OR:
                leftSymbol = this._exp1.Evaluate(context);
                rightSymbol = this._exp1.Evaluate(context);
                return new Symbol(leftSymbol.getBooleanValue() || rightSymbol.getBooleanValue());
            case NOT:
                leftSymbol = this._exp1.Evaluate(context);
                return new Symbol(!leftSymbol.getBooleanValue());
        }
        return null;
    }

    @Override
    public VAR_TYPE getType() {
        return VAR_TYPE.BOOLEAN;
    }
}
