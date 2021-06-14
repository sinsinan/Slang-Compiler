package ast;

import ast.binaryOperations.BinaryDiv;
import ast.binaryOperations.BinaryMinus;
import ast.binaryOperations.BinaryMult;
import ast.binaryOperations.BinaryPlus;
import ast.operators.BinaryOperator;

public class BinaryExp extends Exp {
    private Exp exp;

    public BinaryExp(COMPILATION_CONTEXT context,Exp exp1, Exp exp2, BinaryOperator op) throws Exception {
        switch (op) {
            case PLUS:
                this.exp = new BinaryPlus(context, exp1, exp2);
                break;
            case MINUS:
                this.exp = new BinaryMinus(context, exp1, exp2);
                break;
            case MUL:
                this.exp = new BinaryMult(context, exp1, exp2);
                break;
            case DIV:
                this.exp = new BinaryDiv(context, exp1, exp2);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + op);
        }

    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return this.exp.Evaluate(context);
    }

    @Override
    public VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception {
        return this.exp.getType(context);
    }
}
