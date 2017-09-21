package weaver.interfaces.shaw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * \n 回车(\u000a)
 * \t 水平制表符(\u0009)
 * \s 空格(\u0008)
 * \r 换行(\u000d)
 */

public class ReplaceBlankUtil {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").replaceAll("&nbsp;","").replaceAll("<br>","");
        }
        return dest;
    }

    public static void main(String[] args) {
        System.out.println(ReplaceBlankUtil.replaceBlank(" just " +
                " do it! &nbsp;; <br> ha haha ; "));
    }
}
