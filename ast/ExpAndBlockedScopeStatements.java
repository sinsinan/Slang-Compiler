package ast;

public class ExpAndBlockedScopeStatements{
    private Exp exp;
    private BlockedScopeStatements blockedScopeStatements;

    public ExpAndBlockedScopeStatements(Exp exp, BlockedScopeStatements blockedScopeStatements) {
        this.exp = exp;
        this.blockedScopeStatements = blockedScopeStatements;
    }


    public Exp getExp() {
        return this.exp;
    }


    public BlockedScopeStatements getBlockedScopeStatements() {
        return blockedScopeStatements;
    }
}
