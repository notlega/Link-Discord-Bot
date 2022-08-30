package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkFilter {

    public static boolean checkLinkValid(String link) {
        Pattern pattern = Pattern.compile("[(htp?s:/w.)a-zA-Z0-9@%_+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);
        return matcher.find();
    }

    public static boolean checkLinkTwitter(String link) {
        Pattern pattern = Pattern.compile("^(ht{2}p?s?:/{2})?(www.)?twitter.com/.*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);
        return matcher.find();
    }
}
