package ast;
public class NumericConstant extends Exp{
    private double _value;

    public NumericConstant(double value) {
        this._value = value;
    }

    @Override
    public double Evaluate(RUNTIME_CONTEXT context) {
        return this._value;
    }
}