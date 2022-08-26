package util;

public class CaseConverter {

    public static String toSnakeCase(String string) {
        StringBuilder newString = new StringBuilder(Capitalisation.capitalise(string));
        for (int i = 0; i < newString.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                newString.setCharAt(i, Character.toLowerCase(newString.charAt(i)));
                newString.insert(i, "_");
                i--;
            }
        }
        return string;
    }

    public static String toCamelCase(String string) {
        return string.toUpperCase();
    }
}
