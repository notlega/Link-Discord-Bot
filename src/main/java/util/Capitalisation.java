package util;

public class Capitalisation {

    public static String capitalise(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String decapitalise(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
}
