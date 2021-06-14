package ast;

public abstract class Stmt {
    public abstract Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception;
}