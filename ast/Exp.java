package ast;

import parser.Symbol;
import parser.VAR_TYPE;

public abstract class Exp {
    public abstract Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception;
    public abstract VAR_TYPE getType() throws Exception;
}