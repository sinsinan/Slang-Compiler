package ast;
public abstract class Stmt {
    public abstract boolean Evaluate(RUNTIME_CONTEXT context) throws Exception;
}