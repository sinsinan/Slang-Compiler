package ast;

public class COMPILATION_CONTEXT {

    private COMPILATION_CONTEXT parentContext;
    private ScopeInfo scopeInfo;
//    private boolean isInsideLoop;

    public COMPILATION_CONTEXT() {
        this.scopeInfo = new ScopeInfo();
    }

    public COMPILATION_CONTEXT(COMPILATION_CONTEXT parentContext) {
        this.scopeInfo = new ScopeInfo(parentContext.scopeInfo);
        this.parentContext = parentContext;
    }

    public ScopeInfo getScopeInfo() {
        return scopeInfo;
    }

    public COMPILATION_CONTEXT getParentContext() {
        return parentContext;
    }

//    public void setInsideLoop(boolean insideLoop) {
//        isInsideLoop = insideLoop;
//    }
//
//    public boolean isInsideLoop() {
//        return isInsideLoop;
//    }
}
