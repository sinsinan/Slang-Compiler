package ast;

public class RUNTIME_CONTEXT {

    private ScopeInfo scopeInfo;

    public RUNTIME_CONTEXT() {
        this.scopeInfo = new ScopeInfo();
    }

    public RUNTIME_CONTEXT(ScopeInfo ScopeInfo) {
        this.scopeInfo = new ScopeInfo(ScopeInfo);
    }

    public ScopeInfo getScopeInfo() {
        return scopeInfo;
    }
}
