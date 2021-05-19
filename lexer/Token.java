package lexer;

public enum Token {
    PLUS,
    MINUS,
    DIV,
    MUL,
    DOUBLE,
    OPAREN, //Open parenthesis
    CPAREN, //Close parenthesis
    EOP, //Program ended
    SEMI, //Indicates end of statement
    PRINT,
    PRINTLN, //Print line
    INVALID,
    VARIABLE_NAME, //Denotes a variable
    STRING,
    NUMERIC,
    BOOLEAN,
    QUOTED_STRING,
    TRUE,
    FALSE,
    ASSIGNMENT,
// for relational operator support
    EQ,
    NEQ,
    GT,
    GTE,
    LT,
    LTE,
// for logical operator support
    AND,
    OR,
    NOT,

    CONTINUE,
    BREAK,
    WHILE,

    OPEN_BRACE, // {
    CLOSE_BRACE, // }

    IF,
    ELIF,
    ELSE,
}
