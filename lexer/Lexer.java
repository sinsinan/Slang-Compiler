package lexer;

public class Lexer {
    String exp;
    int length;
    int index;
    double number;
    KeyWordTable keyWordTable;
    String variableName;
    String quotedString;

    public Lexer(String exp) {
        this.exp = exp;
        this.length = exp.length();
        this.index = 0;
        this.keyWordTable = new KeyWordTable();
    }

    public Token getToken() throws Exception {
        if (this.index >= this.length) {
            return Token.EOP;
        }
        Token tok;
        switch (this.exp.charAt(this.index)) {
            case '\n':
            case '\t':
            case ' ':
                this.index++;
                tok = this.getToken();
                break;
            case ';':
                this.index++;
                tok = Token.SEMI;
                break;
            case '+':
                this.index++;
                tok = Token.PLUS;
                break;
            case '-':
                this.index++;
                tok = Token.MINUS;
                break;
            case '*':
                this.index++;
                tok = Token.MUL;
                break;
            case '/':
                this.index++;
                tok = Token.DIV;
                break;
            case '(':
                this.index++;
                tok = Token.OPAREN;
                break;
            case ')':
                this.index++;
                tok = Token.CPAREN;
                break;
            case '{':
                this.index++;
                tok = Token.OPEN_BRACE;
                break;
            case '}':
                this.index++;
                tok = Token.CLOSE_BRACE;
                break;
            case '=':
                this.index++;
                tok = Token.ASSIGNMENT;
                if (this.index < this.length && this.exp.charAt(this.index) == '=') {
                    this.index++;
                    tok = Token.EQ;
                }
                break;
            case '<':
                this.index++;
                tok = Token.LT;
                if (this.index < this.length) {
                    switch (this.exp.charAt(this.index)) {
                        case '>':
                            this.index++;
                            tok = Token.NEQ;
                            break;
                        case '=':
                            this.index++;
                            tok = Token.LTE;
                            break;
                    }
                }
                break;
            case '>':
                this.index++;
                tok = Token.GT;
                if (this.index < this.length && this.exp.charAt(this.index) == '=') {
                    this.index++;
                    tok = Token.GTE;
                }
                break;
            case '&':
                if ((this.index + 1) < this.length && this.exp.charAt(this.index + 1) == '&') {
                    this.index += 2;
                    tok = Token.AND;
                    break;
                }
            case '|':
                if ((this.index + 1) < this.length && this.exp.charAt(this.index + 1) == '|') {
                    this.index += 2;
                    tok = Token.OR;
                    break;
                }
            case '!':
                this.index++;
                tok = Token.NOT;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                tok = this.getDoubleFromString();
                break;
            case '"':
                this.parseQuotedString();
                tok = Token.QUOTED_STRING;
                break;
            default:
                tok = this.getKeyWordToken();
                if (tok != Token.INVALID) {
                    break;
                }
                tok = this.getVariableToken();
                if (tok != Token.INVALID) {
                    break;
                }
                System.out.println(String.format("Invalid character %c found at index %d in expression %s", this.exp.charAt(this.index), this.index, this.exp));
                throw new Exception("Invalid character found");
        }
        return tok;
    }

    public double getNumber() {
        return this.number;
    }

    private Token getDoubleFromString() {
        String numberStr = "";
        while (this.index < this.length && (this.exp.charAt(this.index) == '0' || this.exp.charAt(this.index) == '1' || this.exp.charAt(this.index) == '2' || this.exp.charAt(this.index) == '3' || this.exp.charAt(this.index) == '4' || this.exp.charAt(this.index) == '5' || this.exp.charAt(this.index) == '6' || this.exp.charAt(this.index) == '7' || this.exp.charAt(this.index) == '8' || this.exp.charAt(this.index) == '9')) {
            numberStr += this.exp.charAt(this.index);
            this.index++;
        }
        this.number = Double.parseDouble(numberStr);
        return Token.DOUBLE;
    }

    private Token getKeyWordToken() {
        String possibleKeyWord = "";
        int tempIndex = this.index;
        Token tokenFromKeyWord = this.keyWordTable.getTokenFromKeyWord(possibleKeyWord);
        while (this.keyWordTable.getKeywordMatchCount(possibleKeyWord) > 0) {
            if (this.keyWordTable.getTokenFromKeyWord(possibleKeyWord) != Token.INVALID) {
                tokenFromKeyWord = this.keyWordTable.getTokenFromKeyWord(possibleKeyWord);
                this.index = tempIndex;
            }
            possibleKeyWord += this.exp.charAt(tempIndex);
            tempIndex++;
        }
        return tokenFromKeyWord;
    }

    private Token getVariableToken() {
        if (Character.isLetter(this.exp.charAt(this.index))) {
            this.variableName = "";
            while (this.index < this.length && (Character.isAlphabetic(this.exp.charAt(this.index)) || this.exp.charAt(this.index) == '_')) {
                this.variableName += this.exp.charAt(this.index);
                this.index++;
            }
            return Token.VARIABLE_NAME;

        } else {
            return Token.INVALID;
        }
    }

    private void parseQuotedString() throws Exception {
        this.quotedString = "";
        int quoteCount = 0;
        if (this.exp.charAt(this.index) == '"') {
            while ((this.index < this.length) && quoteCount < 2) {
                if (this.exp.charAt(this.index) == '"') {
                    quoteCount++;
                } else {
                    this.quotedString += this.exp.charAt(this.index);
                }
                this.index++;
            }
        }
        if (quoteCount != 2) {
            throw new Exception("Quoted string not ended");
        }
    }

    public String getVariableName() {
        return this.variableName;
    }

    public String getQuotedString() {
        return this.quotedString;
    }


}
