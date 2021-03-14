package ast;

public class UnaryExp extends Exp{
    private Exp _exp;
    private OPERATOR _op;

    public UnaryExp(Exp exp, OPERATOR op) {
        this._exp = exp;
        this._op = op;
    }

    @Override
    public double Evaluate(RUNTIME_CONTEXT context) {
        switch (this._op) {
            case PLUS:
                return this._exp.Evaluate(context);
            case MINUS:
                return -this._exp.Evaluate(context);
        }
        return Double.NaN;
    }
}
