package parser;

import ast.*;
import lexer.Lexer;
import lexer.TOKEN;

import java.util.List;
//<stmtList> := <statement>+
//<statement> := <printStmt> | <printLineStmt>
//<printStmt> := print <Expr>;
//<printLineStmt>:= printLine <Expr>;
//<Expr> ::= <Term> | <Term> { + | - } <Expr>
//<Term> ::= <Factor> | <Factor> {*|/} <Term>
//<Factor>::= <number> | ( <expr> ) | {+|-} <factor>

public class RDParser extends Lexer {
    private TOKEN currentToken;

    public RDParser(String exp) {
        super(exp);
    }

    public List<Stmt> getStmtList() throws Exception {
        this.setNewCurrentToken();
        return StatementList();
    }

    private List<Stmt> StatementList() throws Exception {
        List<Stmt> stmtList = null;
        while (this.currentToken != TOKEN.EOP) {
            stmtList.add(Statement());
        }
        return stmtList;
    }

    private Stmt Statement() throws Exception {
        switch (this.currentToken) {
            case PRINT:
                this.setNewCurrentToken();
                return this.PrintStmt();
            case PRINTLN:
                this.setNewCurrentToken();
                return this.PrintLineStmt();
            default:
                throw new Exception("Expected PRINT or PRINTLN, got " + this.currentToken);
        }
    }

    private Stmt PrintStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == TOKEN.SEMI) {
            this.setNewCurrentToken();
            return new PrintStatement(exp);
        }
        throw new Exception("Expected Semi colon, got " + this.currentToken);
    }

    private Stmt PrintLineStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == TOKEN.SEMI) {
            this.setNewCurrentToken();
            return new PrintLineStatement(exp);
        }
        throw new Exception("Expected Semi colon, got " + this.currentToken);
    }

    private Exp Expr() throws Exception {
        Exp exp1 = this.Term();
        if (this.currentToken == TOKEN.PLUS || this.currentToken == TOKEN.MINUS) {
            TOKEN lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new BinaryExp(exp1, exp2, lastToken == TOKEN.PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
        }
        return exp1;
    }

    private Exp Term() throws Exception {
        Exp exp1 = this.Factor();
        if (this.currentToken == TOKEN.MUL || this.currentToken == TOKEN.DIV) {
            TOKEN lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new BinaryExp(exp1, exp2, lastToken == TOKEN.MUL ? OPERATOR.MUL : OPERATOR.DIV);
        }
        return exp1;
    }

    private Exp Factor() throws Exception {
        if (this.currentToken == TOKEN.DOUBLE) {
            Exp e1 = new NumericConstant(this.getNumber());
            this.setNewCurrentToken();
            return e1;
        } else if (this.currentToken == TOKEN.OPAREN) {
            this.setNewCurrentToken();
            Exp exp = this.Expr();
            if (this.currentToken == TOKEN.CPAREN) {
                this.setNewCurrentToken();
                return exp;
            }
            throw new Exception("Close parenthesis not found");
        } else if (this.currentToken == TOKEN.MINUS || this.currentToken == TOKEN.PLUS) {
            TOKEN lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp = this.Factor();
            return new UnaryExp(exp, lastToken == TOKEN.PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
        } else {
            throw new Exception("Unexpected end");
        }
    }

    private void setNewCurrentToken() throws Exception {
        this.currentToken = this.getToken();
    }
}
