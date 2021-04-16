package parser;

import ast.*;
import ast.operators.BinaryOperator;
import lexer.Lexer;
import lexer.Token;

import java.util.ArrayList;
import java.util.List;
//<stmtList> := <statement>+
//<statement> := <printStmt> | <printLineStmt> | <varDeclareStmt> | <varAssignmentStmt>
//<varDeclareStmt> := STRING <varName>; | NUMERIC <varName>; | BOOLEAN <varName>;
//<varAssignmentStmt> := <varName> = <Expr> ;
//<printStmt> := print(<Expr>);
//<printLineStmt>:= printLine(<Expr>);
//<Expr> ::= <Term> | <Term> { + | - } <Expr>
//<Term> ::= <Factor> | <Factor> {*|/} <Term>
//<Factor>::= <number> | ( <expr> ) | {+|-} <factor> | TRUE | FALSE | quotedString

public class RDParser extends Lexer {
    private Token currentToken;
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
        Stmt currentStatement;
        while (this.currentToken != Token.EOP) {
            currentStatement = Statement();
            if (currentStatement != null) {
                stmtList.add(currentStatement);
            }
        }
        return stmtList;
    }

    private Stmt Statement() throws Exception {
        switch (this.currentToken) {
            case PRINT:
                this.setNewCurrentToken();
                if (this.currentToken != Token.OPAREN) {
                    throw new Exception("Expected Open Parentheses, got " + this.currentToken);
                }
                this.setNewCurrentToken();
                return this.PrintStmt();
            case PRINTLN:
                this.setNewCurrentToken();
                if (this.currentToken != Token.OPAREN) {
                    throw new Exception("Expected Open Parentheses, got " + this.currentToken);
                }
                this.setNewCurrentToken();
                return this.PrintLineStmt();
            case STRING:
            case NUMERIC:
            case BOOLEAN:
                return this.DeclareVariable();
            case VARIABLE_NAME:
                return this.AssignToVariable();
            default:
                throw new Exception("Expected statement starters got " + this.currentToken);
        }
    }

    private Stmt PrintStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == Token.CPAREN) {
            this.setNewCurrentToken();
            if (this.currentToken == Token.SEMI) {
                this.setNewCurrentToken();
                return new PrintStatement(exp);
            }
            throw new Exception("Expected Semi colon, got " + this.currentToken);
        }
        throw new Exception("Expected Close Parentheses, got " + this.currentToken);
    }

    private Stmt PrintLineStmt() throws Exception {
        Exp exp = this.Expr();
        if (this.currentToken == Token.CPAREN) {
            this.setNewCurrentToken();
            if (this.currentToken == Token.SEMI) {
                this.setNewCurrentToken();
                return new PrintLineStatement(exp);
            }
            throw new Exception("Expected Semi colon, got " + this.currentToken);
        }
        throw new Exception("Expected Close Parentheses, got " + this.currentToken);
    }

    private Exp Expr() throws Exception {
        Exp exp1 = this.Term();
        if (this.currentToken == Token.PLUS || this.currentToken == Token.MINUS) {
            Token lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new BinaryExp(exp1, exp2, lastToken == Token.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS);
        }
        return exp1;
    }

    private Exp Term() throws Exception {
        Exp exp1 = this.Factor();
        if (this.currentToken == Token.MUL || this.currentToken == Token.DIV) {
            Token lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new BinaryExp(exp1, exp2, lastToken == Token.MUL ? BinaryOperator.MUL : BinaryOperator.DIV);
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
                if (this.currentToken == Token.CPAREN) {
                    this.setNewCurrentToken();
                    return exp;
                }
                throw new Exception("Close parenthesis not found");
            case MINUS:
            case PLUS:
                Token lastToken = this.currentToken;
                this.setNewCurrentToken();
                exp = this.Factor();
                return new UnaryExp(exp, lastToken == Token.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS);
            case QUOTED_STRING:
                String quotedString = this.getQuotedString();
                this.setNewCurrentToken();
                return new StringConstant(quotedString);
            case TRUE:
            case FALSE:
                boolean boolValue = this.currentToken == Token.TRUE;
                this.setNewCurrentToken();
                return new BooleanConstant(boolValue);
            case VARIABLE_NAME:
                this.setNewCurrentToken();
                return new Variable(this.getVariableName(), this.symbolTable);
            default:
                throw new Exception("Unexpected end");
        }
    }

    private void setNewCurrentToken() throws Exception {
        this.currentToken = this.getToken();
    }

    private Stmt DeclareVariable() throws Exception {
        VAR_TYPE varType;
        switch (this.currentToken) {
            case STRING:
                varType = VAR_TYPE.STRING;
                break;
            case BOOLEAN:
                varType = VAR_TYPE.BOOLEAN;
                break;
            case NUMERIC:
                varType = VAR_TYPE.NUMERIC;
                break;
            default:
                throw new IllegalStateException("Expected STRING,BOOLEAN,DOUBLE value: " + this.currentToken);
        }
        this.setNewCurrentToken();
        if (this.currentToken != Token.VARIABLE_NAME) {
            throw new Exception("Expected Variable name got " + this.currentToken);
        }
        this.symbolTable.declareVariable(this.getVariableName(), varType);
        this.setNewCurrentToken();
        if (this.currentToken != Token.SEMI) {
            throw new Exception("Expected semi colon, got " + this.currentToken);
        }
        this.setNewCurrentToken();
        return null;
    }

    private Stmt AssignToVariable() throws Exception {
        String currentVariableName = this.getVariableName();
        this.setNewCurrentToken();
        if (this.currentToken != Token.ASSIGNMENT) {
            throw new Exception("Expected assignment, got " + this.currentToken);
        }
        this.setNewCurrentToken();
        Exp expressionToBeAssigned = Expr();
        if (this.currentToken != Token.SEMI) {
            throw new Exception("Expected semi colon, got " + this.currentToken);
        }
        this.setNewCurrentToken();
        return new AssignmentStatement(currentVariableName, expressionToBeAssigned, this.symbolTable);
    }
}
