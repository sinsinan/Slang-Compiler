package ast;

public class BreakStatement extends Stmt {
    @Override
    public boolean Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return true;
    }
}