package lexer;

import java.util.HashMap;
import java.util.Map;

public class KeyWordTable {
    private static Map<String, TOKEN> keyWorkMap = new HashMap<String, TOKEN>();

    public KeyWordTable() {
        this.keyWorkMap.put("PRINT", TOKEN.PRINT);
        this.keyWorkMap.put("PRINTLINE", TOKEN.PRINTLN);
    }

    public TOKEN getTokenFromKeyWord(String keyWord) {
        return this.keyWorkMap.getOrDefault(keyWord, TOKEN.INVALID);
    }
}
