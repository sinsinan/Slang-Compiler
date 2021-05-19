package ast;

public class ContinueStatement extends Stmt {
    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return true;
    }
}
