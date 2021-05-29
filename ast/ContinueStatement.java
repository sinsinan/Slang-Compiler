package ast;

public class ContinueStatement extends Stmt {
    @Override
    public Terminate Evaluate(RUNTIME_CONTEXT context) throws Exception {
        return new Terminate(this.getClass(), null);
    }
}
