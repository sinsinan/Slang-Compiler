package ast;

import parser.Symbol;

public abstract class Exp {
    public abstract Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception;
}