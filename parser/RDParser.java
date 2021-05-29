package parser;

import ast.*;
import ast.operators.BinaryOperator;
import ast.operators.LogicalOperator;
import ast.operators.RelationalOperator;
import lexer.Lexer;
import lexer.Token;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
//<stmtList> := <statement>+
//<statement> := <printStmt> | <printLineStmt> | <varDeclareStmt> | <varAssignmentStmt> | <singleWordStatement> | <functionDefinitionStatement> | <returnStatement>
//<varDeclareStmt> := <parseType> <varName>;
// <parseType> := STRING | NUMERIC | BOOLEAN
//<varAssignmentStmt> := <varName> = <Expr> ;
//<printStmt> := print(<Expr>);
//<printLineStmt>:= printLine(<Expr>);
//<ifStmt> := if(<expr>) <blockScope> [elif(<expr>)<blockOfStatements>][else<blockOfStatements>}]
//<whileStmt> := while(<expr>)<blockOfStatements>
//<blockOfStatements> := {<stmts>}
//<singleWordStatement> := break; | continue;
//<functionDefinitionStatement> := function <parseType> <varName> (<parseFormalParameters> <blockOfStatements>
//<parseFormalParameters> := <parseType> <varName> , <parseFormalParameters>, <parseType> <varName> ), )
//<returnStatement> := return <varName>


//<expr> ::= <BExpr>  | <BExpr> LOGIC_OP <expr>
//<BExpr> ::= <BExpr> | <BExpr> REL_OP <expr>
//<RExpr> := <Term> | <Term> ADD_OP <RExpr>
//<Term> := <Factor> | <Factor> MUL_OP <Term>
//<Factor> := <number> | ( <expr> ) | {+|-|!} <factor> | TRUE | FALSE | quotedString | <functionCall> | <varName>
//<functionCall> := functionName ( <parseActualArguments>
//<parseActualArguments> :=  <Expr> , <parseActualArguments> | <Expr> <parseActualArguments> | );

/// <LOGIC_OP> := '&&' | ‘||’
/// <REL_OP> := '>' |' < '|' >=' |' <=' |' <>' |' =='
/// <MUL_OP> := '*' |' /'
/// <ADD_OP> := '+' |' -'

public class RDParser extends Lexer {
    private Token currentToken;
    ScopeInfo scopeInfo;

    public RDParser(String exp) {
        super(exp);
        this.scopeInfo = new ScopeInfo();
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
            case CONTINUE:
            case BREAK:
                return this.ParseSingleWordStatements();
            case WHILE:
                return this.WhileStmt();
            case IF:
                return this.IfStmt();
            case FUNCTION:
                return this.functionDefinition();
            case RETURN:
                return this.returnStatement();
            default:
                throw new Exception("Expected statement starters got " + this.currentToken);
        }
    }

    private Stmt returnStatement() throws Exception {
        Exp exp = this.Expr();
        return new ReturnStmt(exp);
    }

    private Stmt functionDefinition() throws Exception {
        VAR_TYPE returnType = this.parseType();
        this.setNewCurrentToken();
        if (this.currentToken == Token.VARIABLE_NAME) {
            String functionName = this.getVariableName();
            this.setNewCurrentToken();
            if (this.currentToken == Token.OPAREN) {
                LinkedHashMap<String, VAR_TYPE> formalArguments = this.parseFormalArguments();
                FunctionDefinitionStatement functionDefinitionStatement = new FunctionDefinitionStatement(this.scopeInfo, returnType, functionName, formalArguments);
                this.setNewCurrentToken();
                BlockedScopeStatements blockedScopeStatements = this.getBlockOfStatements();
                functionDefinitionStatement.setBlockedScopeStatements(blockedScopeStatements);
                return functionDefinitionStatement;
            }
            throw new Exception("Expected token to be OPEN PARENTHESIS got " + this.currentToken);
        }
        throw new Exception("Expected token to be VARIABLE_NAME got " + this.currentToken);
    }

    private VAR_TYPE parseType() throws Exception {
        this.setNewCurrentToken();
        switch (this.currentToken) {
            case STRING:
                return VAR_TYPE.STRING;
            case BOOLEAN:
                return VAR_TYPE.BOOLEAN;
            case NUMERIC:
                return VAR_TYPE.NUMERIC;

            default:
                throw new IllegalStateException("Expected token to be STRING, BOOLEAN, NUMERIC got " + this.currentToken);
        }
    }

    private LinkedHashMap<String, VAR_TYPE> parseFormalArguments() throws Exception {
        LinkedHashMap<String, VAR_TYPE> formalArguments = new LinkedHashMap<String, VAR_TYPE>();
        VAR_TYPE formalArgumentType;
        String formalArgumentName;
        this.setNewCurrentToken();
        while (this.currentToken == Token.CPAREN) {
            formalArgumentType = this.parseType();
            this.setNewCurrentToken();
            if (this.currentToken != Token.VARIABLE_NAME) {
                throw new Exception("Expected token to be VARIABLE_NAME got " + this.currentToken);
            }
            formalArgumentName = this.getVariableName();
            if (formalArguments.containsKey(formalArgumentName)) {
                throw new Exception("All argument names should be different, change name of argument " + formalArgumentName);
            }
            formalArguments.put(formalArgumentName, formalArgumentType);
            this.setNewCurrentToken();
            if (this.currentToken == Token.COMMA) {
                this.setNewCurrentToken();
            }
        }
        return formalArguments;
    }

    private Stmt IfStmt() throws Exception {
        List<ExpAndBlockedScopeStatements> expAndBlockedScopeStatementsArray = new ArrayList<ExpAndBlockedScopeStatements>();
        expAndBlockedScopeStatementsArray.add(this.parseExpressionAndStatements());
        while (this.currentToken == Token.ELIF) {
            expAndBlockedScopeStatementsArray.add(this.parseExpressionAndStatements());
        }
        if (this.currentToken == Token.ELSE) {
            this.setNewCurrentToken();
            expAndBlockedScopeStatementsArray.add(new ExpAndBlockedScopeStatements(new BooleanConstant(true), this.getBlockOfStatements()));
        }
        return new IfStatement(expAndBlockedScopeStatementsArray);
    }

    private Stmt WhileStmt() throws Exception {
        ExpAndBlockedScopeStatements expAndBlockedScopeStatements = this.parseExpressionAndStatements();
        return new WhileStatement(expAndBlockedScopeStatements);
    }

    private ExpAndBlockedScopeStatements parseExpressionAndStatements() throws Exception {
        this.setNewCurrentToken();
        if (this.currentToken == Token.OPAREN) {
            this.setNewCurrentToken();
            Exp exp = this.Expr();
            if (this.currentToken == Token.CPAREN) {
                this.setNewCurrentToken();
                BlockedScopeStatements blockedScopeStatements = this.getBlockOfStatements();
                ExpAndBlockedScopeStatements expAndBlockedScopeStatements = new ExpAndBlockedScopeStatements(exp, blockedScopeStatements);
                return expAndBlockedScopeStatements;
            }

            throw new Exception("Expected Close Parenthesis, got " + this.currentToken);
        }
        throw new Exception("Expected Open Parentheses, got " + this.currentToken);
    }

    private BlockedScopeStatements getBlockOfStatements() throws Exception {
        if (this.currentToken == Token.OPEN_BRACE) {
            this.setNewCurrentToken();
            BlockedScopeStatements blockedScopeStatements = new BlockedScopeStatements(this.scopeInfo);
            this.scopeInfo = blockedScopeStatements.getScopeInfo();
            List<Stmt> stmtList = new ArrayList();
            Stmt currentStatement;
            while (this.currentToken != Token.CLOSE_BRACE) {
                currentStatement = Statement();
                if (currentStatement != null) {
                    stmtList.add(currentStatement);
                }
            }
            this.setNewCurrentToken();
            blockedScopeStatements.setStmtList(stmtList);
            this.scopeInfo = blockedScopeStatements.getParentScopeInfo();
            return blockedScopeStatements;
        }
        throw new Exception("Expected OPEN_BRACE, got " + this.currentToken);
    }

    private Stmt ParseSingleWordStatements() throws Exception {
        Token singleWordToken = this.currentToken;
        this.setNewCurrentToken();
        if (this.currentToken == Token.SEMI) {
            this.setNewCurrentToken();
            switch (singleWordToken) {
                case CONTINUE:
                    return new ContinueStatement();
                case BREAK:
                    return new BreakStatement();
                default:
                    throw new Exception("Expected CONTINUE or BREAK, got " + singleWordToken);
            }
        }
        throw new Exception("Expected Semi colon, got " + this.currentToken);
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
        Exp exp1 = this.BExpr();
        if (this.currentToken == Token.AND || this.currentToken == Token.OR) {
            Token lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new LogicalExp(exp1, exp2, lastToken == Token.AND ? LogicalOperator.AND : LogicalOperator.OR);
        }
        return exp1;
    }

    private Exp BExpr() throws Exception {
        Exp exp1 = this.RExpr();
        if (this.currentToken == Token.GT || this.currentToken == Token.GTE || this.currentToken == Token.LT || this.currentToken == Token.LTE || this.currentToken == Token.EQ || this.currentToken == Token.NEQ) {
            Token lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.Expr();
            return new RelationalExp(exp1, exp2, this.getRelationalOperator(lastToken));
        }
        return exp1;
    }

    private RelationalOperator getRelationalOperator(Token lastToken) throws Exception {
        switch (lastToken) {
            case LTE:
                return RelationalOperator.LTE;
            case LT:
                return RelationalOperator.LT;
            case GTE:
                return RelationalOperator.GTE;
            case GT:
                return RelationalOperator.GT;
            case EQ:
                return RelationalOperator.EQ;
            case NEQ:
                return RelationalOperator.NEQ;
            default:
                throw new Exception("Invalid token at getRelationalOperator expected RelationalOperator got " + lastToken);
        }
    }

    private Exp RExpr() throws Exception {
        Exp exp1 = this.Term();
        if (this.currentToken == Token.PLUS || this.currentToken == Token.MINUS) {
            Token lastToken = this.currentToken;
            this.setNewCurrentToken();
            Exp exp2 = this.RExpr();
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
            case NOT:
                this.setNewCurrentToken();
                exp = this.Factor();
                return new LogicalExp(exp, LogicalOperator.NOT);
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
                if (this.scopeInfo.getSymbolFromVariableName(this.getVariableName()).getType() == VAR_TYPE.FUNCTION) {
                    return this.FunctionCall(this.getVariableName());
                }
                Variable variable = new Variable(this.getVariableName(), this.scopeInfo);
                this.setNewCurrentToken();
                return variable;
            default:
                throw new Exception("Unexpected end");
        }
    }

    private Exp FunctionCall(String functionName) throws Exception {
        this.setNewCurrentToken();
        if (this.currentToken != Token.OPAREN) {
            throw new Exception("Invalid token at ParseFunctionCall expected OPAREN got " + this.currentToken);
        }
        this.setNewCurrentToken();
        List<Exp> actualArgumentExpList=  new ArrayList<>();
        while (this.currentToken == Token.CPAREN) {
            actualArgumentExpList.add(Expr());
            this.setNewCurrentToken();
            if (this.currentToken == Token.COMMA) {
                this.setNewCurrentToken();
            } else {
                break;
            }
        }
        if (this.currentToken != Token.CPAREN) {
            throw new Exception("Invalid token at ParseFunctionCall expected CPAREN got " + this.currentToken);
        }
        return new FunctionCallStatement(functionName, this.scopeInfo, actualArgumentExpList);
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
//        this.scopeInfo.declareVariable(this.getVariableName(), varType);
        VariableDeclarationStmt variableDeclarationStmt = new VariableDeclarationStmt(this.scopeInfo, this.getVariableName(), varType);
        this.setNewCurrentToken();
        if (this.currentToken != Token.SEMI) {
            throw new Exception("Expected semi colon, got " + this.currentToken);
        }
        this.setNewCurrentToken();
        return variableDeclarationStmt;
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
        return new AssignmentStatement(currentVariableName, expressionToBeAssigned, this.scopeInfo);
    }
}
