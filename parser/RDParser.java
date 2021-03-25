package parser;

import ast.*;
import lexer.Lexer;
import lexer.TOKEN;

import java.util.ArrayList;
import java.util.List;
//<stmtList> := <statement>+
//<statement> := <printStmt> | <printLineStmt> | <varDeclareStmt> | <varAssignmentStmt>
//<varDeclareStmt> := STRING <varName>; | NUMERIC <varName>; | BOOLEAN <varName>;
//<varAssignmentStmt> := varName = <Expr> ;
//<printStmt> := print(<Expr>);
//<printLineStmt>:= printLine(<Expr>);
//<Expr> ::= <Term> | <Term> { + | - } <Expr>
//<Term> ::= <Factor> | <Factor> {*|/} <Term>
//<Factor>::= <number> | ( <expr> ) | {+|-} <factor> | TRUE | FALSE | quotedString

public class RDParser extends Lexer {
    private TOKEN currentToken;
    SymbolTable symbolTable;

    public RDParser(String exp) {
        super(exp);
        this.symbolTable = new SymbolTable();
    }

    public List<Stmt> getStmtList() throws Exception {
        this.setNewCurrentToken();
        return StatementList();
    }

    private List<Stmt> StatementList() throws Exception {
        List<Stmt> stmtList = new ArrayList();
        while (this.currentToken != TOKEN.EOP) {
            stmtList.add(Statement());
        }
        return stmtList;
    }

    private Stmt Statement() throws Exception {
        switch (this.currentToken) {
            case PRINT:
                this.setNewCurrentToken();
                if (this.currentToken != TOKEN.OPAREN) {
                    throw new Exception("Expected Open Parentheses, got " + this.currentToken);
                }
                this.setNewCurrentToken();
                return this.PrintStmt();
            case PRINTLN:
                this.setNewCurrentToken();
                if (this.currentToken != TOKEN.OPAREN) {
                    throw new Exception("Expected Open Parentheses, got " + this.currentToken);
                }
                this.setNewCurrentToken();
                return this.PrintLineStmt();
            case STRING:
            case NUMERIC:
            case BOOLEAN:
                this.DeclareVariable();
            case VARIABLE_NAME:
                this.AssignToVariable();
            default:
                throw new Exception("Expected statement starters got " + this.currentToken);
        }
    }

    private Stmt PrintStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == TOKEN.CPAREN) {
            this.setNewCurrentToken();
            if (this.currentToken == TOKEN.SEMI) {
                this.setNewCurrentToken();
                return new PrintStatement(exp);
            }
            throw new Exception("Expected Semi colon, got " + this.currentToken);
        }
        throw new Exception("Expected Close Parentheses, got " + this.currentToken);
    }

    private Stmt PrintLineStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == TOKEN.CPAREN) {
            this.setNewCurrentToken();
            if (this.currentToken == TOKEN.SEMI) {
                this.setNewCurrentToken();
                return new PrintLineStatement(exp);
            }
            throw new Exception("Expected Semi colon, got " + this.currentToken);
        }
        throw new Exception("Expected Close Parentheses, got " + this.currentToken);
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
        Exp exp;
        switch (this.currentToken) {
            case DOUBLE:
                exp = new NumericConstant(this.getNumber());
                this.setNewCurrentToken();
                return exp;
            case OPAREN:
                this.setNewCurrentToken();
                exp = this.Expr();
                if (this.currentToken == TOKEN.CPAREN) {
                    this.setNewCurrentToken();
                    return exp;
                }
                throw new Exception("Close parenthesis not found");
            case MINUS:
            case PLUS:
                TOKEN lastToken = this.currentToken;
                this.setNewCurrentToken();
                exp = this.Factor();
                return new UnaryExp(exp, lastToken == TOKEN.PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
            default:
                throw new Exception("Unexpected end");
        }
    }

    private void setNewCurrentToken() throws Exception {
        this.currentToken = this.getToken();
    }

    private void DeclareVariable() throws Exception {
        VAR_TYPE varType;
        switch (this.currentToken) {
            case STRING:
                varType = VAR_TYPE.STRING;
                break;
            case BOOLEAN:
                varType = VAR_TYPE.BOOLEAN;
                break;
            case DOUBLE:
                varType = VAR_TYPE.NUMERIC;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.currentToken);
        }
        this.setNewCurrentToken();
        if (this.currentToken != TOKEN.VARIABLE_NAME) {
            throw new Exception("Expected Variable name got " + this.currentToken);
        }
        this.symbolTable.declareVariable(this.getVariableName(), varType);
        this.setNewCurrentToken();
        if (this.currentToken != TOKEN.CPAREN) {
            throw new Exception("Expected closed Parentheses, got " + this.currentToken);
        }
    }

    private void AssignToVariable() throws Exception {
        String currentVariableName = this.getVariableName();
        this.setNewCurrentToken();
        if (this.currentToken != TOKEN.ASSIGNMENT) {
            throw new Exception("Expected assignment, got " + this.currentToken);
        }
    }
}
