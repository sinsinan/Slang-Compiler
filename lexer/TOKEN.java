package lexer;

public enum TOKEN {
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
    VARIABLE, //Denotes a variable
    STRING,
    NUMERIC,
    BOOLEAN,
}
