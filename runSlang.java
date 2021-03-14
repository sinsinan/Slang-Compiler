import ast.Exp;
import parser.RDParser;

public class runSlang {
    public static void main(String[] args) throws Exception {
        RDParser p = new RDParser("-2*(3+3)");
        Exp e = p.CallExpr();
        System.out.println(e.Evaluate(null));
    }
}
