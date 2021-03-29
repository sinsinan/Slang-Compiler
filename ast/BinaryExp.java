package ast;

import parser.Symbol;

public class BinaryExp extends Exp {
    private Exp _exp1, _exp2;
    private OPERATOR _op;

    public BinaryExp(Exp exp1, Exp exp2, OPERATOR op) {
        this._exp1 = exp1;
        this._exp2 = exp2;
        this._op = op;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        switch (this._op) {
            case PLUS:
                return (new BinaryPlus(this._exp1, this._exp2)).Evaluate(context);
            case MINUS:
                return (new BinaryMinus(this._exp1, this._exp2)).Evaluate(context);
            case MUL:
                return (new BinaryMult(this._exp1, this._exp2)).Evaluate(context);
            case DIV:
                return (new BinaryDiv(this._exp1, this._exp2)).Evaluate(context);
        }
        return null;
    }
}
