package ast;

import parser.ScopeInfo;

import java.util.List;

public class BlockedScopeStatements {
    private String scopeName;
    private List<Stmt> stmtList;
    private ScopeInfo scopeInfo;

    public BlockedScopeStatements(ScopeInfo scopeInfo) {
        this.scopeName = String.valueOf(scopeInfo.getChildSize());
        scopeInfo.addChild(this.scopeName);
        this.scopeInfo = scopeInfo.getChildByName(this.scopeName);
    }

    public ScopeInfo getScopeInfo() {
        return scopeInfo;
    }

    public ScopeInfo getParentScopeInfo() {
        return scopeInfo.getParent();
    }

    public void setStmtList(List<Stmt> statements) {
        this.stmtList = statements;
    }

    public List<Stmt> getStmtList() {
        return stmtList;
    }
}
