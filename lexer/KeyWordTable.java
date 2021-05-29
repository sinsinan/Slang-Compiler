package lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyWordTable {
    private static Map<String, Token> keyWorkMap;
    private Set<String> keyWordSet;

    public KeyWordTable() {
        this.keyWorkMap = new HashMap<>();
        this.keyWorkMap.put("PRINT", Token.PRINT);
        this.keyWorkMap.put("PRINTLINE", Token.PRINTLN);
        this.keyWorkMap.put("STRING", Token.STRING);
        this.keyWorkMap.put("NUMERIC", Token.NUMERIC);
        this.keyWorkMap.put("BOOLEAN", Token.BOOLEAN);
        this.keyWorkMap.put("TRUE", Token.TRUE);
        this.keyWorkMap.put("FALSE", Token.FALSE);
        this.keyWorkMap.put("continue", Token.CONTINUE);
        this.keyWorkMap.put("break", Token.BREAK);
        this.keyWorkMap.put("while", Token.WHILE);
        this.keyWorkMap.put("if", Token.IF);
        this.keyWorkMap.put("elif", Token.ELIF);
        this.keyWorkMap.put("else", Token.ELSE);
        this.keyWorkMap.put("function", Token.FUNCTION);
        this.keyWorkMap.put("return", Token.RETURN);
        this.keyWordSet = this.keyWorkMap.keySet();
    }

    public Token getTokenFromKeyWord(String keyWord) {
        return this.keyWorkMap.getOrDefault(keyWord, Token.INVALID);
    }

    public int getKeywordMatchCount(String inputKeyWord) {
        int count = 0;
        for (String keyWord : this.keyWordSet) {
            if (keyWord.startsWith(inputKeyWord) || keyWord.equals(inputKeyWord)) {
                count++;
            }
        }
        return count;
    }
}
