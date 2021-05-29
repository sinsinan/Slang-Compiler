package ast;

public class BreakStatement extends Stmt {
    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return new Terminate(this.getClass(), null);
    }
}