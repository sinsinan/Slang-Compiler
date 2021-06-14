package ast;

import ast.operators.RelationalOperator;

public class RelationalExp extends Exp{
    Exp leftExp;
    Exp rightExp;
    RelationalOperator op;

    public RelationalExp(COMPILATION_CONTEXT context, Exp leftExp, Exp rightExp, RelationalOperator op) throws Exception {
        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.op = op;
        this.typeCheck(context);
    }
    private void typeCheck(COMPILATION_CONTEXT context) throws Exception {
        VAR_TYPE leftExpType = this.leftExp.getType(context);
        VAR_TYPE rightExpType = this.rightExp.getType(context);
        if (leftExpType == rightExpType) {
            if (leftExpType == VAR_TYPE.BOOLEAN){
                switch (this.op) {
                    case EQ:
                    case NEQ:
                        break;
                    default:
                        throw new IllegalStateException("Relational operators available for boolean are ==,<>. got "+ this.op);
                }
            }else if (leftExpType == VAR_TYPE.STRING) {
                switch (this.op) {
                    case EQ:
                    case NEQ:
                        break;
                    default:
                        throw new IllegalStateException("Relational operators available for string are ==,<>. got "+ this.op);
                }
            }
        }else{
            throw new Exception("Expected "+leftExpType+" and "+rightExpType+" to be same");
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        Symbol leftSymbol = this.leftExp.Evaluate(context);
        Symbol rightSymbol = this.rightExp.Evaluate(context);
        VAR_TYPE leftExpType = leftSymbol.getType();
        if (leftExpType == VAR_TYPE.BOOLEAN){
            switch (this.op) {
                case EQ:
                    return new Symbol(leftSymbol.getBooleanValue() == rightSymbol.getBooleanValue());
                case NEQ:
                    return new Symbol(leftSymbol.getBooleanValue() != rightSymbol.getBooleanValue());
            }
        }else if (leftExpType == VAR_TYPE.STRING) {
            switch (this.op) {
                case EQ:
                    return new Symbol(leftSymbol.getStringValue().equals(rightSymbol.getStringValue()));
                case NEQ:
                    return new Symbol(!leftSymbol.getStringValue().equals(rightSymbol.getBooleanValue()));
            }
        } else {
            switch (this.op) {
                case NEQ:
                    return new Symbol(leftSymbol.getNumericValue() != rightSymbol.getNumericValue());
                case EQ:
                    return new Symbol(leftSymbol.getNumericValue() == rightSymbol.getNumericValue());
                case GT:
                    return new Symbol(leftSymbol.getNumericValue() > rightSymbol.getNumericValue());
                case GTE:
                    return new Symbol(leftSymbol.getNumericValue() >= rightSymbol.getNumericValue());
                case LT:
                    return new Symbol(leftSymbol.getNumericValue() < rightSymbol.getNumericValue());
                case LTE:
                    return new Symbol(leftSymbol.getNumericValue() <= rightSymbol.getNumericValue());
            }
        }
        throw new Exception("Unexpected End");
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return VAR_TYPE.BOOLEAN;
    }
}
