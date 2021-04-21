package ast;

import ast.binaryOperations.BinaryDiv;
import ast.binaryOperations.BinaryMinus;
import ast.binaryOperations.BinaryMult;
import ast.binaryOperations.BinaryPlus;
import ast.operators.BinaryOperator;
import parser.Symbol;
import parser.VAR_TYPE;

public class BinaryExp extends Exp {
    private Exp exp;

    public BinaryExp(Exp exp1, Exp exp2, BinaryOperator op) throws Exception {
        switch (op) {
            case PLUS:
                this.exp = new BinaryPlus(exp1, exp2);
                break;
            case MINUS:
                this.exp = new BinaryMinus(exp1, exp2);
                break;
            case MUL:
                this.exp = new BinaryMult(exp1, exp2);
                break;
            case DIV:
                this.exp = new BinaryDiv(exp1, exp2);
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
    public VAR_TYPE getType() throws Exception {
        return this.exp.getType();
    }
}
