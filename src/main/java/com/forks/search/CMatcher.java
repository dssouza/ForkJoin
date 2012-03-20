/**
 *
 */
package main.java.com.forks.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Raman_Pliashkou
 */
public class CMatcher {
    public static boolean match(String str) {
        Pattern pattern = Pattern.compile("([^\\s]+(\\.(?i)(txt|xml))$)");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
