package ast;

import ast.UnaryOperations.UnaryMinus;
import ast.UnaryOperations.UnaryPlus;
import ast.operators.BinaryOperator;

public class UnaryExp extends Exp {
    private Exp exp;

    public UnaryExp(COMPILATION_CONTEXT context, Exp exp, BinaryOperator op) throws Exception {
        switch (op) {
            case PLUS:
                this.exp = new UnaryPlus(context, exp);
                break;
            case MINUS:
                this.exp = new UnaryMinus(context, exp);
                break;
            default:
                throw new IllegalStateException("UnaryExp, Evaluate, expected operator to be any of (PLUS, MINUS), got " + op);
        }
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return this.exp.Evaluate(context);
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return exp.getType(context);
    }
}
