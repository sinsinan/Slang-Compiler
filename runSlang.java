import ast.Exp;
import ast.Stmt;
import parser.RDParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class runSlang {
    public static void main(String[] args) throws Exception {
        try
        {
            String content = Files.readString(Path.of("./HelloWorld.sin"));
            RDParser p = new RDParser(content);
            List<Stmt> stmtList = p.getStmtList();
            for (Stmt stmt : stmtList) {
                stmt.Evaluate(null);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
