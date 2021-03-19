package lexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyWordTable {
    private static Map<String, TOKEN> keyWorkMap;
    private Set<String> keyWordSet;

    public KeyWordTable() {
        this.keyWorkMap = new HashMap<String, TOKEN>();
        this.keyWorkMap.put("PRINT", TOKEN.PRINT);
        this.keyWorkMap.put("PRINTLINE", TOKEN.PRINTLN);
        this.keyWorkMap.put("STRING", TOKEN.STRING);
        this.keyWorkMap.put("NUMERIC", TOKEN.NUMERIC);
        this.keyWorkMap.put("BOOLEAN", TOKEN.BOOLEAN);
        this.keyWordSet = this.keyWorkMap.keySet();
    }

    public TOKEN getTokenFromKeyWord(String keyWord) {
        return this.keyWorkMap.getOrDefault(keyWord, TOKEN.INVALID);
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
