package ast;

import parser.VAR_TYPE;

public abstract class Stmt {
    public abstract Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception;
}