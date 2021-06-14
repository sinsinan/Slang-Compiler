package ast;

public class ExpContextAndBlockedScopeStatements {
    private Exp exp;
    private BlockedScopeStatements blockedScopeStatements;
    private COMPILATION_CONTEXT compilationContext;

    public ExpContextAndBlockedScopeStatements(COMPILATION_CONTEXT compilationContext, Exp exp, BlockedScopeStatements blockedScopeStatements) {
        this.exp = exp;
        this.blockedScopeStatements = blockedScopeStatements;
        this.compilationContext = compilationContext;
    }


    public Exp getExp() {
        return this.exp;
    }


    public BlockedScopeStatements getBlockedScopeStatements() {
        return blockedScopeStatements;
    }

    public COMPILATION_CONTEXT getCompilationContext() {
        return compilationContext;
    }
}
