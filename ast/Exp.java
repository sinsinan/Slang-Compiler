package ast;

public abstract class Exp {
    public abstract Symbol Evaluate(RUNTIME_CONTEXT context) throws Exception;
    public abstract VAR_TYPE getType(COMPILATION_CONTEXT context) throws Exception;
}