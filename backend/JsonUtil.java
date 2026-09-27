package backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonUtil.java
 * -------------
 * A tiny, dependency-free helper for reading a value out of a simple flat
 * JSON string like {"key":"value", "key2":"value2"} and for escaping text
 * so it is safe to place inside a JSON string.
 *
 * For a bigger/production project you would use a real JSON library
 * (e.g. org.json or Gson), but for a beginner degree project this keeps
 * things dependency-free and easy to understand.
 *
 * Marathi: Hi helper class chhoti JSON strings vachण्यासाठी ani
 * banवण्यासाठी वापरतो, बाहेरची library न वापरता.
 */
public class JsonUtil {

    public static String getValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        Matcher matcher = Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static String escape(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", " ")
                    .replace("\r", " ");
    }
}
