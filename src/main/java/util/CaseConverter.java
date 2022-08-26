package util;

import org.jetbrains.annotations.NotNull;

public class CaseConverter {

    /**
     * Converts a string to snakeCase.
     * @param string The string to convert.
     * @return The string in snakeCase.
     */
    public static @NotNull String snakeCase(String string) {
        StringBuilder newString = new StringBuilder(Capitalisation.decapitalise(string));

        for (int i = 0; i < newString.length(); i++) {
            if (Character.isUpperCase(newString.charAt(i))) {
                newString.setCharAt(i, Character.toLowerCase(newString.charAt(i)));
                newString.insert(i, "_");
                i--;
            }
        }
        return newString.toString();
    }

    /**
     * Converts a string to camelCase.
     * @param string The string to convert.
     * @return The string in camelCase.
     */
    public static @NotNull String camelCase(String string) {
        StringBuilder newString = new StringBuilder(Capitalisation.decapitalise(string));

        for (int i = 0; i < newString.length(); i++) {
            if (newString.charAt(i) == '_') {
                newString.setCharAt(i + 1, Character.toUpperCase(newString.charAt(i + 1)));
                newString.deleteCharAt(i);
                i--;
            }
        }
        return newString.toString();
    }

    /**
     * Converts a string to pascalCase.
     * @param string The string to convert.
     * @return The string in pascalCase.
     */
    public static @NotNull String pascalCase(String string) {
        return Capitalisation.capitalise(camelCase(string));
    }

    /**
     * Converts a string to kebabCase.
     * @param string The string to convert.
     * @return The string in kebabCase.
     */
    public static @NotNull String kebabCase(String string) {
        StringBuilder newString = new StringBuilder(Capitalisation.decapitalise(string));

        for (int i = 0; i < newString.length(); i++) {
            if (Character.isUpperCase(newString.charAt(i))) {
                newString.setCharAt(i, Character.toLowerCase(newString.charAt(i)));
                newString.insert(i, "-");
                i--;
            }
        }
        return newString.toString();
    }
}
