import ast.Exp;
import ast.Stmt;
import parser.RDParser;

import java.util.List;

public class runSlang {
    public static void main(String[] args) throws Exception {
        RDParser p = new RDParser("PRINT(-2*(3+3));PRINTLINE(3+3);PRINT(-2*(3+3));");
        List<Stmt> stmtList = p.getStmtList();
        for (Stmt stmt : stmtList) {
            stmt.Evaluate(null);
        }
    }
}
