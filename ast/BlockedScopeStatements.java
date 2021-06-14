package ast;

import java.util.List;

public class BlockedScopeStatements {
    private List<Stmt> stmtList;

    public BlockedScopeStatements() {

    }

    public void setStmtList(List<Stmt> statements) {
        this.stmtList = statements;
    }

    public List<Stmt> getStmtList() {
        return stmtList;
    }
}
