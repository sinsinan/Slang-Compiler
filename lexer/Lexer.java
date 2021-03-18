package lexer;

public class Lexer {
    String exp;
    int length;
    int index;
    double number;
    KeyWordTable keyWordTable;

    public Lexer(String exp) {
        this.exp = exp;
        this.length = exp.length();
        this.index = 0;
        this.keyWordTable = new KeyWordTable();
    }

    public TOKEN getToken() throws Exception {
        if (this.index >= this.length) {
            return TOKEN.EOP;
        }
        TOKEN tok;
        switch (this.exp.charAt(this.index)) {
            case '\n':
            case '\t':
                this.index++;
                tok = this.getToken();
                break;
            case ';':
                this.index++;
                tok = TOKEN.SEMI;
                break;
            case '+':
                this.index++;
                tok = TOKEN.PLUS;
                break;
            case '-':
                this.index++;
                tok = TOKEN.MINUS;
                break;
            case '*':
                this.index++;
                tok = TOKEN.MUL;
                break;
            case '/':
                this.index++;
                tok = TOKEN.DIV;
                break;
            case '(':
                this.index++;
                tok = TOKEN.OPAREN;
                break;
            case ')':
                this.index++;
                tok = TOKEN.CPAREN;
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
            default:
                tok = this.getVariableToken();
                if (tok != TOKEN.INVALID) {
                    break;
                }
                tok = this.getKeyWordToken();
                if (tok != TOKEN.INVALID) {
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

    private TOKEN getDoubleFromString() {
        String numberStr = "";
        while (this.index < this.length && (this.exp.charAt(this.index) == '1' || this.exp.charAt(this.index) == '2' || this.exp.charAt(this.index) == '3' || this.exp.charAt(this.index) == '4' || this.exp.charAt(this.index) == '5' || this.exp.charAt(this.index) == '6' || this.exp.charAt(this.index) == '7' || this.exp.charAt(this.index) == '8' || this.exp.charAt(this.index) == '9')) {
            numberStr += this.exp.charAt(this.index);
            this.index++;
        }
        this.number = Double.parseDouble(numberStr);
        return TOKEN.DOUBLE;
    }

    private TOKEN getKeyWordToken() {
        String possibleKeyWord = "";
        int tempIndex = this.index;
        TOKEN tokenFromKeyWord = this.keyWordTable.getTokenFromKeyWord(possibleKeyWord);
        while (this.keyWordTable.getKeywordMatchCount(possibleKeyWord) > 0) {
            if (this.keyWordTable.getTokenFromKeyWord(possibleKeyWord) != TOKEN.INVALID) {
                tokenFromKeyWord = this.keyWordTable.getTokenFromKeyWord(possibleKeyWord);
                this.index = tempIndex;
            }
            possibleKeyWord += this.exp.charAt(tempIndex);
            tempIndex++;
        }
        return tokenFromKeyWord;
    }
}
