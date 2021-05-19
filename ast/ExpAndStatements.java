package ast;

import java.util.List;

public class ExpAndStatements{
    private Exp exp;
    private List<Stmt> stmtList;

    public ExpAndStatements(Exp exp, List<Stmt> stmtList) {
        this.exp = exp;
        this.stmtList = stmtList;
    }

    public Exp getExp() {
        return this.exp;
    }

    public List<Stmt> getStmtList() {
        return this.stmtList;
    }
}
