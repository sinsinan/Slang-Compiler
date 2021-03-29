package ast;

import parser.Symbol;

public class UnaryExp extends Exp{
    private Exp _exp;
    private OPERATOR _op;

    public UnaryExp(Exp exp, OPERATOR op) {
        this._exp = exp;
        this._op = op;
    }

    @Override
    public Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception {
        switch (this._op) {
            case PLUS:
                return (new UnaryPlus(this._exp)).Evaluate(context);
            case MINUS:
                return (new UnaryMinus(this._exp)).Evaluate(context);
        }
        throw new Exception("UnaryExp, Evaluate, expected operator to be any of (PLUS, MINUS), got "+this._op);
    }
}
