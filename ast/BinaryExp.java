package ast;

public class BinaryExp extends Exp {
    private Exp _exp1, _exp2;
    private OPERATOR _op;

    public BinaryExp(Exp exp1, Exp exp2, OPERATOR op) {
        this._exp1 = exp1;
        this._exp2 = exp2;
        this._op = op;
    }

    @Override
    public double Evaluate(RUNTIME_CONTEXT context) {
        switch (this._op) {
            case PLUS:
                return this._exp1.Evaluate(context) + this._exp2.Evaluate(context);
            case MINUS:
                return this._exp1.Evaluate(context) - this._exp2.Evaluate(context);
            case MUL:
                return this._exp1.Evaluate(context) * this._exp2.Evaluate(context);
            case DIV:
                return this._exp1.Evaluate(context) / this._exp2.Evaluate(context);
        }
        return Double.NaN;
    }
}
